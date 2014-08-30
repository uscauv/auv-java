package com.uscauv.events.image;

import org.opencv.core.Mat;

/**
 * Event for when an image is available from the downward-facing camera.
 */
public class BottomCameraImageEvent {

    private Mat image;

    public BottomCameraImageEvent(Mat mat) {
        this.image = mat;
    }

    public Mat getImage() {
        return image;
    }

}
