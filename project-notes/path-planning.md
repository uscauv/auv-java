We will mimic the ROS navigation stack style of controlling the robot along a certain path.

The path planner needs to:
* Take a given target point
* Given incremental updates of the robot's current position/direction, compute the current forward velocity (speed), and turning velocity to follow the computed path
* For now we can start with just 2 dimensions, but in the future we will need to add the depth dimension
* On a lower level, the core motor control driver will self-correct to match the desired velocity provided by the path planner

Basic API definition:
* We use Guava's [EventBus](https://code.google.com/p/guava-libraries/wiki/EventBusExplained) so it might be helpful to read up on that
* Define some new events for use with EventBus in the com.uscauv.events package (make a new subpackage for navigation events called ```navigation```)
* There needs to be an event to set the target point
* There also needs to be an event for a higher-level part of the program to provide the path planner with the current position/direction
* Another event that will be sent out by the path planner to notify the motor driver what the desired velocities (forward and turn) are
* (Events are just normal Java classes so just make sure that they contain all of the required data and have descriptive names)