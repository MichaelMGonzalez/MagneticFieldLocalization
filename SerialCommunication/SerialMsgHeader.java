public class SerialMsgHeader {

	// Setup Constants 

	public static final int BAUD_RATE = 115200;
	// Message Constants 

	public static final byte MSG_SIZE = 1;
	public static final byte STOP = 0;
	public static final byte MOVE_FORWARD_ANALOG = 1;
	public static final byte MOVE_BACKWARD_ANALOG = 2;
	public static final byte TURN_LEFT_ANALOG = 3;
	public static final byte TURN_RIGHT_ANALOG = 4;
	public static final byte MOVE_FORWARD_VELOCITY = 5;
	public static final byte MOVE_BACKWARD_VELOCITY = 6;
	public static final byte TURN_LEFT_VELOCITY = 7;
	public static final byte TURN_RIGHT_VELOCITY = 8;
	public static final byte SET_P = 8;
	public static final byte SET_I = 9;
	public static final byte SET_D = 10;
	public static final byte GET_P = 11;
	public static final byte GET_I = 12;
	public static final byte GET_D = 13;
	public static final byte GET_VELOCITY = 14;
	public static final byte GET_BATTERY = 15;
	public static final byte GET_STATE = 16;
	public static final byte REPORTING_STATE = 122;
	public static final byte REPORTING_P = 123;
	public static final byte REPORTING_I = 124;
	public static final byte REPORTING_D = 125;
	public static final byte REPORTING_VELOCITY = 126;
	public static final byte REPORTING_BATTERY = 127;
	public static final String[] MSG_KEYS = { "GET_P", "MOVE_BACKWARD_ANALOG", "TURN_RIGHT_ANALOG", "GET_I", "GET_D", "REPORTING_D", "REPORTING_VELOCITY", "REPORTING_BATTERY", "SET_P", "TURN_LEFT_VELOCITY", "REPORTING_STATE", "REPORTING_P", "SET_I", "TURN_LEFT_ANALOG", "TURN_RIGHT_VELOCITY", "GET_VELOCITY", "SET_D", "MOVE_FORWARD_VELOCITY", "STOP", "MOVE_BACKWARD_VELOCITY", "GET_STATE", "GET_BATTERY", "REPORTING_I", "MSG_SIZE", "MOVE_FORWARD_ANALOG" };
	public static final byte[] MSG_VALS = { GET_P, MOVE_BACKWARD_ANALOG, TURN_RIGHT_ANALOG, GET_I, GET_D, REPORTING_D, REPORTING_VELOCITY, REPORTING_BATTERY, SET_P, TURN_LEFT_VELOCITY, REPORTING_STATE, REPORTING_P, SET_I, TURN_LEFT_ANALOG, TURN_RIGHT_VELOCITY, GET_VELOCITY, SET_D, MOVE_FORWARD_VELOCITY, STOP, MOVE_BACKWARD_VELOCITY, GET_STATE, GET_BATTERY, REPORTING_I, MSG_SIZE, MOVE_FORWARD_ANALOG };

}