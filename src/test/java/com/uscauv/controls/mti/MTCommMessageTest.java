package com.uscauv.controls.mti;

import org.junit.Test;
import 

import static org.junit.Assert.assertEquals;

public class MTCommMessageTest {

	@Test
	public void testMessageData(){
		/**
		*Tests see message functions in MTCommMessage to make sure they're output is equavilent the desired binary outputs
		*/
		MTCommMessage commMessage = new MTCommMessage();
		testGoToConfigMessage(commMessage);
	}

	//not finished
	public void testGoToConfigMessage(MTCommMessage commMessage){
		commMessage.setGoToMeasurementMessage();
		byte [] messageBytes = commMessage.getBytes();
		for(byte messageByte : messageBytes){

		}
	}

	public void testGoToMeasurementMessage(MTCommMessage commMessage){

	}

	//etc...
}