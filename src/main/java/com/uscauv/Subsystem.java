package com.uscauv;

/**
 * Interface that all Subsystems must implement in order for the main Seabee driver to automatically manage their states.
 */
public abstract class Subsystem {

    private boolean isEnabled = false;

    public abstract String getName();

    public void start() {
        isEnabled = true;
    }

    public void stop() {
        isEnabled = false;
    }

    public void init() {

    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
