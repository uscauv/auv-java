package com.uscauv.vision;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.ArrayList;

/**
 * Common processes to apply to images to aid in analyzing camera images.
 */
public class VisionUtil {

    public static class HsvThreshold {
        public HsvThreshold(int hueMin, int hueMax, int satMin, int satMax, int valMin, int valMax) {
            this.hueMin = hueMin;
            this.hueMax = hueMax;
            this.satMin = satMin;
            this.satMax = satMax;
            this.valMin = valMin;
            this.valMax = valMax;
        }

        public int hueMin, hueMax;
        public int satMin, satMax;
        public int valMin, valMax;
    }

    /**
     * Don't allow instantiation of a VisionUtil object because all methods are static.
     */
    private VisionUtil() {
    }

    /**
     * Threshold an HSV image given separate min/max values for each channel.
     *
     * @param hsv       hsv image
     * @param threshold threshold ranges
     * @return the result of the threshold (each binary channel AND'ed together)
     */
    public static Mat threshold(Mat hsv, HsvThreshold threshold) {
        //split image into the corresponding HSV channels
        ArrayList<Mat> split = new ArrayList<>(3);
        Core.split(hsv, split);

        Mat hue = split.get(0);
        Mat sat = split.get(1);
        Mat val = split.get(2);

        Mat hueBin = Mat.zeros(hue.size(), CvType.CV_8UC1);
        Mat satBin = Mat.zeros(val.size(), CvType.CV_8UC1);
        Mat valBin = Mat.zeros(val.size(), CvType.CV_8UC1);

        //threshold each channel separately
        Core.inRange(hue, new Scalar(threshold.hueMin), new Scalar(threshold.hueMax), hueBin);
        Core.inRange(sat, new Scalar(threshold.satMin), new Scalar(threshold.satMax), satBin);
        Core.inRange(val, new Scalar(threshold.valMin), new Scalar(threshold.valMax), valBin);

        //combine all of the thresholded channels into a single Mat
        Mat bin = hueBin.clone();
        Core.bitwise_and(satBin, bin, bin);
        Core.bitwise_and(valBin, bin, bin);

        return bin;
    }
}
