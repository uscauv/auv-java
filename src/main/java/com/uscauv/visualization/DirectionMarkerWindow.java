package com.uscauv.visualization;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Seabee;
import com.uscauv.events.visualization.DirectionMarkerImageOutputEvent;
import com.uscauv.vision.VisionUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vmagro on 8/30/14.
 */
public class DirectionMarkerWindow extends JFrame {

    private JLabel label = new JLabel();

    public DirectionMarkerWindow() {
        super("Direction Marker");

        Seabee.getInstance().register(this);

        label.setPreferredSize(new Dimension(640, 480));
        setContentPane(label);

        pack();

        setVisible(true);
    }

    @Subscribe
    public void onImage(DirectionMarkerImageOutputEvent event) {
        label.setIcon(new ImageIcon(VisionUtil.getBufferedImage(event.getImage())));
    }

}
