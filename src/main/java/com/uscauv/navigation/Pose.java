package com.uscauv.navigation;

import javafx.geometry.Point3D;

/**
 * Representation of the sub's position, orientation and velocities.
 */
public class Pose {

    /**
     * X = lateral position in pool (axis perpendicular to starting orientation)
     * Y = forward/backward position in pool (axis parallel to starting orientation)
     * Z = depth (0 = surface, higher = deeper)
     */
    private Point3D position;

    /**
     * @see com.uscauv.navigation.Pose.Orientation
     */
    private Orientation orientation;

    /**
     * @see com.uscauv.navigation.Pose.Velocity
     */
    private Velocity velocity;

    public Pose(Point3D position, Orientation orientation, Velocity velocity) {
        this.position = position;
        this.orientation = orientation;
        this.velocity = velocity;
    }

    /**
     * X = lateral position in pool (axis perpendicular to starting orientation)
     * Y = forward/backward position in pool (axis parallel to starting orientation)
     * Z = depth (0 = surface, higher = deeper)
     */
    public Point3D getPosition() {
        return position;
    }

    /**
     * X = lateral position in pool (axis perpendicular to starting orientation)
     * Y = forward/backward position in pool (axis parallel to starting orientation)
     * Z = depth (0 = surface, higher = deeper)
     */
    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }


    /**
     * Representation of the sub's orientation, independent of the current position.
     * <p>
     * <img src="data:image/gif;base64,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" />
     */
    public static class Orientation {

        /**
         * Side-to-side tilt along sub's major axis.
         * Positive is leaning to right, negative to left.
         */
        private double roll;

        /**
         * Front-to-back tilt along sub's major axis.
         * Negative means nose is tilting downards.
         */
        private double pitch;

        /**
         * Rotation in the xy-plane (along the z/depth axis).
         * Positive is turning to the right, negative is turning to the left.
         */
        private double yaw;

        public Orientation(double roll, double pitch, double yaw) {
            this.roll = roll;
            this.pitch = pitch;
            this.yaw = yaw;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }
    }

}
