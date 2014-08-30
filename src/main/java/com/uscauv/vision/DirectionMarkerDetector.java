package com.uscauv.vision;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Seabee;
import com.uscauv.events.image.BottomCameraImageEvent;
import com.uscauv.events.visualization.DirectionMarkerImageOutputEvent;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * This class detects direction markers and will post an event to the Seabee {@link com.google.common.eventbus.EventBus}
 * when it detects a direction marker. These are the orange markers on the bottom of the pool which point to the next
 * objective.
 */
public class DirectionMarkerDetector {

    @Subscribe
    public void onImage(BottomCameraImageEvent event) {
        System.out.println("DirectionMarkerDetector got image");

        //clone the image so any modifications we make don't mess up other code trying to use this image
        Mat img = event.getImage().clone();

        //threshold based on color
        //TODO: fix these magic numbers
        Mat bin = VisionUtil.threshold(img, new VisionUtil.HsvThreshold(20, 175, 0, 255, 0, 255));

        //find convex hulls in the color-thresholded image and fill them in to make the AND result nicer
        List<MatOfPoint> colorContours = new ArrayList<>();
        Imgproc.findContours(bin, colorContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        List<MatOfPoint> colorConvexHulls = VisionUtil.convexHulls(colorContours);
        Imgproc.drawContours(bin, colorConvexHulls, -1, new Scalar(255), -1);
        //we fill the convex hulls in in case part of the marker is obstructed, which will cause the obstructed part
        // to be missing in the result obtained by AND'ing the canny result and the color result

        Mat canny = VisionUtil.canny(img, 50);

        //find contours in the image after first processing it with Canny edge detection
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(canny, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //find the convex hulls of those edges in case part of the marker is blocked or eroded and is not a nice rectangle
        List<MatOfPoint> hulls = VisionUtil.convexHulls(contours);

        //remove the detected convex hulls if their score is below a certain threshold
        List<MatOfPoint> contoursToBeRemoved = new ArrayList<>();
        for (MatOfPoint hull : hulls) {
            double score = scoreContour(hull);
            if (score < 1 || Double.isNaN(score)) {
                contoursToBeRemoved.add(hull);
            } else {
                System.out.println("Contour with score " + score);
                Core.putText(img, "S: " + score, hull.toArray()[0], Core.FONT_HERSHEY_PLAIN, 1, new Scalar(0, 255, 0));

                RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(hull.toArray()));
                Point p = rect.center;
                p.x += 50;
                p.y -= 20;
                Core.putText(img, VisionUtil.angle(rect) + "deg", p, Core.FONT_HERSHEY_PLAIN, 1, new Scalar(0, 0, 255));
            }
        }
        hulls.removeAll(contoursToBeRemoved);

        Mat hullImg = Mat.zeros(bin.size(), bin.type());

        //draw the contours back onto the original image for visualization purposes
        Imgproc.drawContours(hullImg, hulls, -1, new Scalar(255), -1);

        Core.bitwise_and(bin, hullImg, bin);

        Seabee.getInstance().post(new DirectionMarkerImageOutputEvent(bin));
    }

    private double scoreContour(MatOfPoint contour) {
        RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));

        double shorterSide = Math.min(rect.size.width, rect.size.height);
        double longerSide = Math.max(rect.size.width, rect.size.height);

        //the marker is 6 inches by 4 feet so the ratio of long : short side is 8
        double ratioScore = 1 / Math.abs((longerSide / shorterSide) - 8);

        return ratioScore;
    }

}
