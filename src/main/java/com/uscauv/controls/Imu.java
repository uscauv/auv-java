package com.uscauv.controls;

import com.uscauv.Subsystem;
import com.uscauv.navigation.Pose;
import com.uscauv.navigation.Velocity;

/**
 * Read data from the IMU and provide a position and orientation to the rest of the robot.
 * <p>
 * The IMU driver will run in its own thread and periodically post PoseEvent's to the main EventBus via Seabee#post
 * for consumption in other parts of the program.
 */
public class Imu extends Subsystem implements Runnable {

    private static Imu instance;
    String filePath;
    bytes[] lastMessage;

    public static synchronized Imu getInstance() {
        if (instance == null) {
            instance = new Imu();
        }
        return instance;
    }

    private Imu() {
        FileReader usbFile = new FileReader(filePath); //when connected, opens /dev/seabee /dev/serial and /dev/ttyUSB0

    }

    private start(){

    }

    private run(){
        byte[] messageBytes = parseMessage(fileReader);       //probably should add functionality to MTCommMessage to make this easy (getTimeStamp or something)
        boolean newMessage = checkIfNewMessage(messageBytes);
        if(newMessage){
            lastMessage = messageBytes;
            //post on EventBus and w.e else needs to updated.
        }
 	    sleep(50); //default message frequency is once every 100 ms (100Hz)
    }

    public Pose getLatestPose() {
        //TODO: implement this
        return null;
    }

    public Velocity getLatestVelocity() {
        return getLatestPose().getVelocity();
    }

    //TODO: implement start/stop for this?

    @Override
    public String getName() {
        return "IMU";
	
    private byte[] parseMessage(FileReader fileReader){

    }

    private boolean checkIfNewMessage(byte[] messageBytes){

    }
}
