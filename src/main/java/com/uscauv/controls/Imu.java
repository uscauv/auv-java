package com.uscauv.controls;

import com.uscauv.Subsystem;
import com.uscauv.navigation.Pose;
import com.uscauv.navigation.Velocity;

/**
 * Read data from the IMU and provide a position and orientation to the rest of the robot.
 */
public class Imu extends Subsystem {

    private static Imu instance;

    public static Imu getInstance() {
        if (instance == null) {
            instance = new Imu();
        }
        return instance;
    }

    private Imu() {

    }

    public Pose getLatestPose() {
        //TODO: implement this
        return null;
    }

    public Velocity getLatestVelocity() {
        return getLatestPose().getVelocity();
    }

    //TODO: implement start/stop for this?

    @Override
    public String getName() {
        return "IMU";
    }
}
