package com.ashmp.magneticfieldlocalizer;

public class SerialMsgHeader {

	// Setup Constants 

	public static final int BAUD_RATE = 38400;
	// Message Constants 

	public static final byte MSG_SIZE = 1;
	public static final byte MSG_SENT = -86;
	public static final byte STOP = 0;
	public static final byte MOVE_FORWARD_ANALOG = 1;
	public static final byte MOVE_BACKWARD_ANALOG = 2;
	public static final byte TURN_LEFT_ANALOG = 3;
	public static final byte TURN_RIGHT_ANALOG = 4;
	public static final byte SET_LINEAR_VELOCITY = 5;
	public static final byte SET_ANGULAR_VELOCITY = 6;
	public static final byte SET_P = 8;
	public static final byte SET_I = 9;
	public static final byte SET_D = 10;
	public static final byte GET_P = 11;
	public static final byte GET_I = 12;
	public static final byte GET_D = 13;
	public static final byte GET_RIGHT_CONTROL = 14;
	public static final byte GET_LEFT_CONTROL = 15;
	public static final byte GET_LINEAR_VELOCITY = 20;
	public static final byte GET_ANGULAR_VELOCITY = 21;
	public static final byte GET_BATTERY = 30;
	public static final byte GET_STATE = 31;
	public static final byte REPORTING_STATE = 122;
	public static final byte REPORTING_P = 123;
	public static final byte REPORTING_I = 124;
	public static final byte REPORTING_D = 125;
	public static final byte REPORTING_VELOCITY = 126;
	public static final byte REPORTING_BATTERY = 127;
	public static final String[] MSG_KEYS = { "GET_ANGULAR_VELOCITY", "GET_P", "MOVE_BACKWARD_ANALOG", "TURN_RIGHT_ANALOG", "GET_I", "GET_D", "SET_ANGULAR_VELOCITY", "REPORTING_D", "REPORTING_VELOCITY", "REPORTING_BATTERY", "SET_P", "REPORTING_I", "REPORTING_STATE", "GET_LEFT_CONTROL", "REPORTING_P", "SET_I", "TURN_LEFT_ANALOG", "SET_D", "STOP", "GET_RIGHT_CONTROL", "SET_LINEAR_VELOCITY", "GET_STATE", "GET_LINEAR_VELOCITY", "MSG_SENT", "GET_BATTERY", "MSG_SIZE", "MOVE_FORWARD_ANALOG" };
	public static final byte[] MSG_VALS = { GET_ANGULAR_VELOCITY, GET_P, MOVE_BACKWARD_ANALOG, TURN_RIGHT_ANALOG, GET_I, GET_D, SET_ANGULAR_VELOCITY, REPORTING_D, REPORTING_VELOCITY, REPORTING_BATTERY, SET_P, REPORTING_I, REPORTING_STATE, GET_LEFT_CONTROL, REPORTING_P, SET_I, TURN_LEFT_ANALOG, SET_D, STOP, GET_RIGHT_CONTROL, SET_LINEAR_VELOCITY, GET_STATE, GET_LINEAR_VELOCITY, MSG_SENT, GET_BATTERY, MSG_SIZE, MOVE_FORWARD_ANALOG };

}