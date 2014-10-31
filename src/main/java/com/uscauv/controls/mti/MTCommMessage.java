package com.uscauv.controls.mti;

/**
 * Created by vmagro on 10/30/14.
 */
public class MTCommMessage {

    private final byte preamble = (byte) 0xFA;
    private final byte busId = (byte) 0xFF;

    private byte messageId;
    private byte length;

    private byte[] extLength;

    private byte[] data;

    //TODO: implement construction process to fill in all of these fields

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
