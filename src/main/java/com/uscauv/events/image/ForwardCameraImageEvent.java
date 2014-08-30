package com.uscauv.events.image;

import org.opencv.core.Mat;

/**
 * Event for when an image is available from the downward-facing camera.
 */
public class ForwardCameraImageEvent {

    private Mat image;

    public ForwardCameraImageEvent(Mat mat) {
        this.image = mat;
    }

    public Mat getImage() {
        return image;
    }

}
