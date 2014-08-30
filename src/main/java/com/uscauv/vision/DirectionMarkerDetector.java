package com.uscauv.vision;

import com.google.common.eventbus.Subscribe;
import com.uscauv.events.image.BottomCameraImageEvent;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
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

        Mat img = event.getImage();

        Mat canny = VisionUtil.canny(img, 50);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(canny, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> hulls = VisionUtil.convexHulls(contours);

        List<MatOfPoint> contoursToBeRemoved = new ArrayList<>();
        for (MatOfPoint hull : hulls) {
            double score = scoreContour(hull);
            if (score < 1 || Double.isNaN(score)) {
                contoursToBeRemoved.add(hull);
            } else {
                System.out.println("Contour with score " + score);
                Core.putText(img, "S: " + score, hull.toArray()[0], Core.FONT_HERSHEY_PLAIN, 1, new Scalar(0, 255, 0));
            }
        }
        hulls.removeAll(contoursToBeRemoved);

        Imgproc.drawContours(img, hulls, -1, new Scalar(0, 255, 0), -1);

        Highgui.imwrite("canny.png", img);
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
