package com.uscauv.events.controls;

import com.uscauv.navigation.Velocity;

/**
 * Created by vmagro on 10/12/14.
 */
public class DesiredVelocityEvent {

    private Velocity velocity;

    public DesiredVelocityEvent(Velocity velocity) {
        this.velocity = velocity;
    }

    public Velocity getVelocity() {
        return velocity;
    }
}
