public class SerialMsgHeader {

	// Setup Constants 

	public static final int BAUD_RATE = 9600;
	// Message Constants 

	public static final byte MSG_SIZE = 1;
	public static final byte MSG_SENT = -86;
	public static final byte STOP = 0;
	public static final byte MOVE_FORWARD = 1;
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
	public static final byte REPORTING_VELOCITY = 110;
	public static final byte REPORTING_ANGULAR_VELOCITY = 111;
	public static final byte REPORTING_L_ANGULAR_VELOCITY = 112;
	public static final byte REPORTING_R_ANGULAR_VELOCITY = 113;
	public static final byte REPORTING_R_CONTROL = 114;
	public static final byte REPORTING_L_CONTROL = 115;
	public static final byte REPORTING_R_C_DEST = 116;
	public static final byte REPORTING_L_C_DEST = 117;
	public static final byte REPORTING_STATE = 122;
	public static final byte REPORTING_P = 123;
	public static final byte REPORTING_I = 124;
	public static final byte REPORTING_D = 125;
	public static final byte REPORTING_BATTERY = 127;
	public static final String[] MSG_KEYS = { "REPORTING_R_C_DEST", "GET_ANGULAR_VELOCITY", "GET_P", "MOVE_BACKWARD_ANALOG", "REPORTING_L_CONTROL", "TURN_RIGHT_ANALOG", "GET_I", "GET_D", "SET_ANGULAR_VELOCITY", "REPORTING_D", "REPORTING_VELOCITY", "REPORTING_BATTERY", "SET_P", "REPORTING_I", "REPORTING_STATE", "GET_LEFT_CONTROL", "REPORTING_P", "SET_I", "TURN_LEFT_ANALOG", "MOVE_FORWARD", "SET_D", "REPORTING_L_C_DEST", "STOP", "REPORTING_ANGULAR_VELOCITY", "GET_RIGHT_CONTROL", "SET_LINEAR_VELOCITY", "GET_STATE", "GET_LINEAR_VELOCITY", "REPORTING_R_ANGULAR_VELOCITY", "MSG_SENT", "REPORTING_R_CONTROL", "GET_BATTERY", "REPORTING_L_ANGULAR_VELOCITY", "MSG_SIZE" };
	public static final byte[] MSG_VALS = { REPORTING_R_C_DEST, GET_ANGULAR_VELOCITY, GET_P, MOVE_BACKWARD_ANALOG, REPORTING_L_CONTROL, TURN_RIGHT_ANALOG, GET_I, GET_D, SET_ANGULAR_VELOCITY, REPORTING_D, REPORTING_VELOCITY, REPORTING_BATTERY, SET_P, REPORTING_I, REPORTING_STATE, GET_LEFT_CONTROL, REPORTING_P, SET_I, TURN_LEFT_ANALOG, MOVE_FORWARD, SET_D, REPORTING_L_C_DEST, STOP, REPORTING_ANGULAR_VELOCITY, GET_RIGHT_CONTROL, SET_LINEAR_VELOCITY, GET_STATE, GET_LINEAR_VELOCITY, REPORTING_R_ANGULAR_VELOCITY, MSG_SENT, REPORTING_R_CONTROL, GET_BATTERY, REPORTING_L_ANGULAR_VELOCITY, MSG_SIZE };

}