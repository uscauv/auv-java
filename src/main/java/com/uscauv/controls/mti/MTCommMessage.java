package com.uscauv.controls.mti;

/**
 * Created by vmagro on 10/30/14.
 */
public class MTCommMessage {

    private final byte preamble = (byte) 0xFA;
    private final byte busId = (byte) 0xFF;

    private byte messageId;
    private byte length; //num of bytes in the "data" segment.

    private byte[] extLength; //bytes in addition to the data segment (only used if "data" is 254 bytes long)

    private byte[] data;

    //NOTE: there are a TON of different types of messages. I've only got a handful listed here.

    public void setGoToConfigMessage(){
        /**
        *Sets message variables to a Go To Config state message
        */
        this.messageId = (byte)0x30; //make sure this truncates correctly
        this.data = new byte[]{}; // data is n/a for this message. dunno if this is what we want
        this.length = 0;
        this.extLength = null;
    }

    public void setGoToMeasurementMessage(){
        /**
        *Sets message variables to a Go To Measurement state message
        */
        this.messageId = (byte)0x10;
        this.data = new byte[]{}; // n/a
        this.length = 0;
        this.extLength = null;
    }

    public void setResetMessage(){
        /**
        *Sets message variables to a device Reset message
        */
        this.messageId = (byte)0x40;
        this.data = new byte[]{};
        this.length = 0;
        this.extLength = null;
    }

    public void setReqConfigurationMessage(){
        /**
        *Sets message variables to request a current Configuration message
        */
        this.messageId = (byte)0x0c;
        this.data = new byte[]{};
        this.length = 0;
        this.extLength = null;
    }

    public void setReqPeriodMessage(){
        /**
        *Sets message variables to request the current sample period (default is 10ms)
        */
        this.messageId = (byte)0x04;
        this.data = new byte[]{};
        this.length = 0;
        this.extLength = null;
    }

    public void setSetPeriodMessage(byte[] period){
        /**
        *Sets message variables to set a new sample period (def: 10ms [1152 (0x0480)], min: 1.95ms [225 (0x00E1)], max: 10.0ms [1152 (0x0480)])
        */
        this.messageId = (byte)0x04;
        if(period.length == 2){
            this.data = period;
        }
        else{
            System.out.println("Invalid array size passed into setSetPeriodMessage");
        }
        this.length = 2;
        this.extLength = null;
    }

    public void setReqOutputModeMessage(){
        /**
        *Sets message variables to get the current output mode
        */
        this.messageId = (byte)0xd0;
        this.data = new byte[]{};
        this.length = 0;
        this.extLength = null;
    }

    //NOTE: This and some others (notably set OutputSettings) need gotoConfig state sent to MTFirst.
    public void setSetOutputModeMessage(){ //baudrate will set frequency of outputs
        /**
        *Sets message variables to set the current output mode
        */
        this.messageId = (byte)0xd0;
        this.data = new byte[]{(byte)24, (byte)62}; //00011000 00011110
        this.length - 2;
        this.extLength = null;
        //need to implement this when we decide what sensor measurements we actually want
    }

    public void setReqOutputSettingsMessage(){
        /**
        *Sets message variables to get the current output settings
        */
        this.messageId = (byte)0xd2;
        this.data = new byte[]{};
        this.length = 0;
        this.extLength = null;
    }

    //NOTE: bits 14-18 I'm not sure about.
    public void setSetOutputSettingsMessage(){ //baudrate will set frequency of outputs
        /**
        *Sets message variables to set the current output settings
        */
        this.messageId = (byte)0xd2;
        this.data = new byte[]{(byte) 0, (byte) 7, (byte) 192, (byte) 96+26}; // 00000000 / 00000 XX X/XX 00 00 00 / 0 111 10 10
        this.length = 4;
        this.extLength = null;
    }

    /**
     * Construct this message into a byte array in the format that the IMU expects
     *
     * @return message bytes
     */
    public byte[] getBytes() {
        //4 bytes for preamble, busId, messageId, length
        int totalLength = 4;
        if (extLength != null) {
            totalLength += extLength.length;
        }
        totalLength += data.length;
        totalLength += 1; //1 byte at the end for the checksum

        byte[] bytes = new byte[totalLength];

        //fill in the known values for the message
        bytes[0] = preamble;
        bytes[1] = busId;
        bytes[2] = messageId;
        bytes[3] = length;

        int currentOffset = 4;

        //add extLength to message if present
        if (extLength != null) {
            System.arraycopy(extLength, 0, bytes, currentOffset, extLength.length);
            currentOffset += extLength.length;
        }

        System.arraycopy(data, 0, bytes, currentOffset, data.length);

        //calculate the checksum

        //we can sum up all the bytes (excluding the preamble) in one byte because we only care about the last byte,
        // and the overflow in addition will end up with this value being the "last byte" in the sum
        byte lastSumByte = 0;
        for (int i = 1; i < bytes.length; i++) {
            lastSumByte += bytes[i];
        }

        //checksum is the value needed to make the last byte of the message sum = 0
        byte checksum = (byte) (256 - lastSumByte);

        //last message byte is the checksum
        bytes[bytes.length - 1] = checksum;

        return bytes;
    }

}
