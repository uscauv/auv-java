package com.uscauv;

import com.uscauv.events.image.BottomCameraImageEvent;
import com.uscauv.events.image.ForwardCameraImageEvent;
import com.uscauv.visualization.Gui;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.util.Scanner;

/**
 * Created by vmagro on 8/29/14.
 */
public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Gui.init();

        Scanner scanner = new Scanner(System.in);

        Mat bottomSrc = Highgui.imread("vision-samples/path_marker_generated.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
        Mat forwardSrc = Highgui.imread("vision-samples/gate_vertical_generated.jpg", Highgui.CV_LOAD_IMAGE_COLOR);

        BottomCameraImageEvent bottomEvent = new BottomCameraImageEvent(bottomSrc);
        ForwardCameraImageEvent forwardEvent = new ForwardCameraImageEvent(forwardSrc);

        Seabee.getInstance().post(bottomEvent);
        Seabee.getInstance().post(forwardEvent);

        while (scanner.hasNextLine()) {
            bottomSrc = Highgui.imread("vision-samples/path_marker_generated.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
            bottomEvent = new BottomCameraImageEvent(bottomSrc);

            forwardSrc = Highgui.imread("vision-samples/gate_vertical_generated.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
            forwardEvent = new ForwardCameraImageEvent(forwardSrc);

            Seabee.getInstance().post(bottomEvent);
            Seabee.getInstance().post(forwardEvent);

            scanner.nextLine();
        }
    }

}
