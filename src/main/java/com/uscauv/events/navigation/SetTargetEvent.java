package com.uscauv.events.navigation;

import com.uscauv.navigation.Pose;

/**
 * Created by vmagro on 10/10/14.
 */
public class SetTargetEvent {

    private Pose pose;

    public SetTargetEvent(Pose pose) {
        this.pose = pose;
    }

    public Pose getTargetPose() {
        return pose;
    }
}
