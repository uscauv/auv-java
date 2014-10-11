package com.uscauv.navigation;

import com.uscauv.Subsystem;

/**
 * This class is responsible for taking a target Pose and outputting the required motions
 * to achieve that target destination given continuous updates of the current position.
 */
public class PathPlanner implements Subsystem {

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

}
