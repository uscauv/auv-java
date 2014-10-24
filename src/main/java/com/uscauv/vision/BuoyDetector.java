package com.uscauv.vision;

import com.uscauv.events.image.ForwardCameraImageEvent;
import org.opencv.core.Mat;

/**
 * Analyzes forward images to find buoy.
 * Algorithm described as follows:
 * TODO: document algorithm
 */
public class BuoyDetector {

    private static BuoyDetector instance;

    public static synchronized BuoyDetector getInstance() {
        if (instance == null) {
            instance = new BuoyDetector();
        }
        return instance;
    }

    private BuoyDetector() {

    }

    public void onForwardImage(ForwardCameraImageEvent event) {
        Mat img = event.getImage();
    }

}
