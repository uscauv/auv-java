package com.uscauv.controls;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Subsystem;
import com.uscauv.events.controls.DesiredVelocityEvent;
import com.uscauv.navigation.Velocity;

/**
 * Control the thrusters. This will take the actual velocity and the commanded velocity to change the motor outputs to
 * achieve the desired velocity.
 */
public class Thrusters extends Subsystem {

    private static Thrusters instance;

    public static synchronized Thrusters getInstance() {
        if (instance == null) {
            instance = new Thrusters();
        }
        return instance;
    }

    private Thrusters() {

    }

    @Override
    public String getName() {
        return "Thrusters";
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void init() {

    }

    @Subscribe
    public void onDesiredVelocity(DesiredVelocityEvent event) {
        setDesiredVelocity(event.getVelocity());
    }

    private void setDesiredVelocity(Velocity velocity) {
        //TODO: implement this
    }
}
