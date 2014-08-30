package com.uscauv.vision;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Seabee;
import com.uscauv.events.image.BottomCameraImageEvent;
import org.opencv.core.Mat;

/**
 * This class detects direction markers and will post an event to the Seabee {@link com.google.common.eventbus.EventBus}
 * when it detects a direction marker. These are the orange markers on the bottom of the pool which point to the next
 * objective.
 */
public class DirectionMarkerDetector {

    /**
     * Singleton instance of DirectionMarkerDetector
     */
    private static DirectionMarkerDetector instance;

    static {
        instance = new DirectionMarkerDetector();
        Seabee.getInstance().register(instance);
    }

    /**
     * Don't allow anyone to instantiate DirectionMarkerDetector so we only have one copy of it.
     */
    private DirectionMarkerDetector() {
    }

    @Subscribe
    public void onImage(BottomCameraImageEvent event) {
        Mat img = event.getImage();
    }

}
