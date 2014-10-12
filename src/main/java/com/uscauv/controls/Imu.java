package com.uscauv.controls;

import com.uscauv.Subsystem;
import com.uscauv.navigation.Pose;
import com.uscauv.navigation.Velocity;

/**
 * Read data from the IMU and provide a position and orientation to the rest of the robot.
 * <p>
 * The IMU driver will run in its own thread and periodically post PoseEvent's to the main EventBus via Seabee#post
 * for consumption in other parts of the program.
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
