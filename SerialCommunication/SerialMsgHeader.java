public class SerialMsgHeader {

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
	public static final byte REPORTING_STATE = 250;
	public static final byte REPORTING_P = 251;
	public static final byte REPORTING_I = 252;
	public static final byte REPORTING_D = 253;
	public static final byte REPORTING_VELOCITY = 254;
	public static final byte REPORTING_BATTERY = 255;

}