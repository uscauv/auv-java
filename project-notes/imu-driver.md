The path planner needs to:
* Read data from the IMU over serial/usb
* Output it using EventBus to the rest of the program
* Integrate the measured accelerations to get velocity and position

Basic API definition:
* We use Guava's [EventBus](https://code.google.com/p/guava-libraries/wiki/EventBusExplained) so it might be helpful to read up on that
* Define some new events for use with EventBus in the com.uscauv.events package this can go in the same (make a new subpackage for sensor events called ```sensor```)
* The driver should instantiate a singleton instance of itself inside a ```static{}``` block (or some other mechanism) and begin publishing data when the program is started up
* (Events are just normal Java classes so just make sure that they contain all of the required data and have descriptive names)
* In addition to broadcasting Events there should be methods to get the last observed value for a more imperative style of programming where that could be appropriate in other areas.
