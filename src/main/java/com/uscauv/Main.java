package com.uscauv;

import com.uscauv.events.image.BottomCameraImageEvent;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 * Created by vmagro on 8/29/14.
 */
public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat src = Highgui.imread("vision-samples/path_marker_generated.jpg", Highgui.CV_LOAD_IMAGE_COLOR);

        BottomCameraImageEvent event = new BottomCameraImageEvent(src);
        Seabee.getInstance().post(event);
    }

}
