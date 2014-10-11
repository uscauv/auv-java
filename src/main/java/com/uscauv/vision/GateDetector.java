package com.uscauv.vision;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Seabee;
import com.uscauv.events.image.ForwardCameraImageEvent;
import com.uscauv.events.visualization.GateDetectionImageOutputEvent;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class detects the qualification gate and will post an event to the Seabee {@link
 * com.google.common.eventbus.EventBus} when it detects the gate.
 */
public class GateDetector {

    private static GateDetector instance;

    public static GateDetector getInstance() {
        if (instance == null)
            instance = new GateDetector();
        return instance;
    }

    private GateDetector() {

    }

    @Subscribe
    public void onImage(ForwardCameraImageEvent event) {
        long start = System.currentTimeMillis();
        System.out.println("GateDetector got image");

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

        Collections.sort(hulls, (a, b) -> scoreContour(a) < scoreContour(b) ? 1 : -1);

        //give up if we didn't detect at least two candidates
        if (hulls.size() < 2) {
            return;
        }

        //get the two highest scoring candidates
        RotatedRect right = Imgproc.minAreaRect(new MatOfPoint2f(hulls.get(1).toArray()));
        RotatedRect left = Imgproc.minAreaRect(new MatOfPoint2f(hulls.get(0).toArray()));

        //we got the right and left mixed up, swap them
        if (right.center.x < left.center.x) {
            RotatedRect tmp = right;
            right = left;
            left = tmp;
        }

        double confidence = scorePair(left, right);
        System.out.println("Confidence this is a gate: " + confidence);
        if (confidence < 80) {
            Seabee.getInstance().post(new GateDetectionImageOutputEvent(img));
            return;
        }

        //convert the RotatedRects into other forms for use in other methods later
        Point[] rightPoints = new Point[4];
        Point[] leftPoints = new Point[4];
        right.points(rightPoints);
        left.points(leftPoints);
        MatOfPoint rightPointsMat = new MatOfPoint();
        MatOfPoint leftPointsMat = new MatOfPoint();
        rightPointsMat.fromArray(rightPoints);
        leftPointsMat.fromArray(leftPoints);

        //draw the right side as green and left as red
        Imgproc.drawContours(img, Arrays.asList(rightPointsMat), -1, new Scalar(0, 255, 0), -1);
        Imgproc.drawContours(img, Arrays.asList(leftPointsMat), -1, new Scalar(0, 0, 255), -1);

        Seabee.getInstance().post(new GateDetectionImageOutputEvent(img));
        long end = System.currentTimeMillis();

        System.out.println("Took " + (end - start) + "ms to complete detection");
    }

    private double scoreContour(MatOfPoint contour) {
        RotatedRect rect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));

        double shorterSide = Math.min(rect.size.width, rect.size.height);
        double longerSide = Math.max(rect.size.width, rect.size.height);

        //the orange tape marker is 3 inches by 4 feet so the ratio of long : short side is 16
        double ratioScore = 1 / Math.abs((longerSide / shorterSide) - 16);

        double score = ratioScore + Imgproc.contourArea(contour);

        if (Imgproc.contourArea(contour) < 500) {
            return 0;
        }

        return score;
    }

    private double scorePair(RotatedRect left, RotatedRect right) {
        //calculate the space in pixels that should be between each side
        //the actual dimension is 10 feet, so the space should be 2.5x the height (longer dimension)
        double desiredSpaceBetween = Math.max(left.size.height, left.size.width) * 2.5;

        //score based on the distance between the two sides
        double spaceScore = Math.abs(Math.abs(left.center.x - right.center.x) - desiredSpaceBetween);
        spaceScore = 100 - .5 * Math.abs(spaceScore);

        //score based on parallel-ity of the two sides
        double parallelScore = Math.abs(VisionUtil.angle(left) - VisionUtil.angle(right));
        parallelScore = 100 - Math.abs(parallelScore);

        //score based on the similarity in sizes
        double samenessScore = Math.abs(Math.max(left.size.height, left.size.width) - Math.max(right.size.height, right.size.width));
        samenessScore = 100 - Math.abs(samenessScore);

        //score based on being located in the same spot on the Y-axis
        double sameYScore = Math.abs(left.center.y - right.center.y);
        sameYScore = 100 - Math.abs(sameYScore);

        //calculate the total score by averaging all the other scores
        return (spaceScore + parallelScore + sameYScore + samenessScore) / 4;
    }

}
