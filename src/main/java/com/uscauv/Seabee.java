package com.uscauv;

import com.google.common.eventbus.EventBus;

/**
 * Seabee is the main class for our robot. It holds on to a common {@link EventBus} that allows components to
 * publish/subscribe to one another without being aware of one another. This class also provides methods to get sensor
 * values and drive motors.
 */
public class Seabee {

    private static Seabee instance;

    private EventBus bus = new EventBus();

    /**
     * Get the singleton instance of Seabee
     *
     * @return
     */
    public static synchronized Seabee getInstance() {
        if (instance == null)
            instance = new Seabee();
        return instance;
    }

    /**
     * Don't let anyone instantiate Seabee, so there can only be one copy
     */
    private Seabee() {

    }

    /**
     * Register the given object with the {@link EventBus}. This only has to be done with subscribers.
     *
     * @param object the subscriber to register
     */
    public void register(Object object) {
        bus.register(object);
    }

    /**
     * Post an event to the {@link EventBus}. The event will be published to all methods with an @Subscribe annotation
     * that take the same parameter type and have been registered beforehand.
     *
     * @param event the event to publish
     */
    public void post(Object event) {
        bus.post(event);
    }

}
