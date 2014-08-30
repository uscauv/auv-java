package com.uscauv.events.visualization;

import org.opencv.core.Mat;

/**
 * Event for when the {@link com.uscauv.vision.DirectionMarkerDetector} has an image to display to the user.
 */
public class GateDetectionImageOutputEvent {

    private Mat image;

    public GateDetectionImageOutputEvent(Mat image) {
        this.image = image;
    }

    public Mat getImage() {
        return image;
    }

}
