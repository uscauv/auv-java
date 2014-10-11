package com.uscauv;

/**
 * Interface that all Subsystems must implement in order for the main Seabee driver to automatically manage their states.
 */
public interface Subsystem {

    public String getName();

    public default void start() {

    }

    public default void stop() {

    }

    public default void init() {

    }

}
