package com.uscauv.navigation;

import com.google.common.eventbus.Subscribe;
import com.uscauv.Subsystem;
import com.uscauv.events.navigation.PoseEvent;
import com.uscauv.events.navigation.SetTargetEvent;

/**
 * This class is responsible for taking a target Pose and outputting the required motions
 * to achieve that target destination given continuous updates of the current position.
 */
public class PathPlanner extends Subsystem {

    private static PathPlanner instance = null;

    private PathPlanner() {

    }

    public static PathPlanner getInstance() {
        if (instance == null)
            instance = new PathPlanner();
        return instance;
    }


    @Override
    public String getName() {
        return "Path Planner";
    }

    @Override
    public void init() {
        //TODO: implement this later
    }

    @Override
    public void start() {
        //TODO: implement this later
    }

    @Override
    public void stop() {
        //TODO: implement this later
    }

    @Subscribe
    public void onPoseUpdated(PoseEvent event) {
        //delegate to the other method so we don't have duplicated code
        updateCurrentPose(event.getPose());
    }

    @Subscribe
    public void onPoseTargetSet(SetTargetEvent event) {
        //delegate to the other method so we don't have duplicated code
        setPoseTarget(event.getTargetPose());
    }

    //these 2 methods exist to support a more imperative/tightly coupled approach in certain situations
    //where that might make more sense than using EventBus

    public void updateCurrentPose(Pose pose) {
        //TODO: implement this later
    }

    public void setPoseTarget(Pose target) {
        //TODO: implement this later
    }

}
