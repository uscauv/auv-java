package com.uscauv.events.navigation;

import com.uscauv.navigation.Pose;

/**
 * Created by vmagro on 10/10/14.
 */
public class PoseEvent {

    private Pose pose;

    public PoseEvent(Pose pose) {
        this.pose = pose;
    }

    public Pose getPose() {
        return pose;
    }
}
