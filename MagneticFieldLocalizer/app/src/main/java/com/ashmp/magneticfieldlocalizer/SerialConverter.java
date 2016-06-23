package com.ashmp.magneticfieldlocalizer;

/**
 * Created by Ashmp on 6/6/2016.
 */
public class SerialConverter {

    //SerialConverter() {}

    static public byte[] writeFloat(Byte msgtype, float f) {

        byte[] bytes = new byte[6];
        bytes[0] = msgtype;

        int bits = Float.floatToIntBits(f);
        bytes[1] = (byte)(bits & 0xff);
        bytes[2] = (byte)((bits >> 8) & 0xff);
        bytes[3] = (byte)((bits >> 16) & 0xff);
        bytes[4] = (byte)((bits >> 24) & 0xff);

        bytes[5] = SerialMsgHeader.MSG_SENT;

        return bytes;
    }

    static public byte[] writeInt(Byte msgtype, int i) {

        byte[] bytes = new byte[4];
        bytes[0] = msgtype;

        bytes[1] = (byte)(i);
        bytes[2] = (byte)(i >> 8);

        bytes[3] = SerialMsgHeader.MSG_SENT;

        return bytes;
    }

    static public byte[] writeCommand(Byte msgtype) {
        byte[] bytes = new byte[2];

        bytes[0] = msgtype;
        bytes[1] = SerialMsgHeader.MSG_SENT;

        return bytes;
    }

}
