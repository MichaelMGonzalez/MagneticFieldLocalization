#ifndef SERIAL_MSG_HEADER
#define SERIAL_MSG_HEADER

// Setup Constants 

#define BAUD_RATE 9600
// Message Constants 

#define MSG_SIZE 1
#define MSG_SENT -86
#define STOP 0
#define MOVE_FORWARD_ANALOG 1
#define MOVE_BACKWARD_ANALOG 2
#define TURN_LEFT_ANALOG 3
#define TURN_RIGHT_ANALOG 4
#define MOVE_FORWARD_VELOCITY 5
#define MOVE_BACKWARD_VELOCITY 6
#define TURN_LEFT_VELOCITY 7
#define TURN_RIGHT_VELOCITY 8
#define SET_P 8
#define SET_I 9
#define SET_D 10
#define GET_P 11
#define GET_I 12
#define GET_D 13
#define GET_VELOCITY 14
#define GET_BATTERY 15
#define GET_STATE 16
#define REPORTING_STATE 122
#define REPORTING_P 123
#define REPORTING_I 124
#define REPORTING_D 125
#define REPORTING_VELOCITY 126
#define REPORTING_BATTERY 127

#endif
