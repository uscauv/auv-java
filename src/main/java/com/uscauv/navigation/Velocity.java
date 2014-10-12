package com.uscauv.navigation;

/**
 * Representation of the sub's current velocity.
 * See other classes in this file for documentation on the various axes and sign conventions used.
 */
public class Velocity {
    private double x;
    private double y;
    private double z;

    public Velocity(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Turning velocity.
     * Right is positive, left is negative.
     *
     * @return
     */
    public double getTurn() {
        return x;
    }

    /**
     * Forward velocity.
     * Forward is positive, reverse is negative.
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Up/down velocity.
     * Down is positive, up is negative.
     *
     * @return
     */
    public double getZ() {
        return z;
    }

}