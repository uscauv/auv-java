package com.uscauv;

import com.google.common.eventbus.EventBus;
import com.uscauv.controls.Imu;
import com.uscauv.controls.Thrusters;
import com.uscauv.navigation.PathPlanner;
import com.uscauv.vision.DirectionMarkerDetector;
import com.uscauv.vision.GateDetector;

import java.util.HashMap;

/**
 * Seabee is the main class for our robot. It holds on to a common {@link EventBus} that allows components to
 * publish/subscribe to one another without being aware of one another. This class also provides methods to get sensor
 * values and drive motors.
 */
public class Seabee {

    private static Seabee instance;

    private EventBus bus = new EventBus();

    private HashMap<String, Subsystem> subsystems = new HashMap<>();

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
        register(DirectionMarkerDetector.getInstance());
        register(GateDetector.getInstance());

        registerSubsystem(PathPlanner.getInstance());
        registerSubsystem(Imu.getInstance());
        registerSubsystem(Thrusters.getInstance());
    }

    /**
     * Register the given object with the {@link EventBus}. This only has to be done with subscribers.
     *
     * @param object the subscriber to register
     */
    public void register(Object object) {
        System.out.println("Registered subscriber of type <" + object.getClass().getName() + ">");
        bus.register(object);
    }

    /**
     * Post an event to the {@link EventBus}. The event will be published to all methods with an @Subscribe annotation
     * that take the same parameter type and have been registered beforehand.
     *
     * @param event the event to publish
     */
    public void post(Object event) {
        System.out.println("Posting event of type <" + event.getClass().getName() + ">");
        bus.post(event);
    }

    /**
     * Register a subsystem with Seabee.
     * This must be done for all subsystems so Seabee can manage all of the subsystem states with a unified interface.
     * This method will also register the Subsystem with the EventBus if it has any methods with the @Subscribe annotation.
     *
     * @param subsystem
     */
    public void registerSubsystem(Subsystem subsystem) {
        register((Object) subsystem);

        subsystems.put(subsystem.getName(), subsystem);
    }

}
