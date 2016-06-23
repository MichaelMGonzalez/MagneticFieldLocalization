#ifndef ARDUINO_HLSM
#define ARDUINO_HLSM

#define timeEllapsed (millis() - transitionedAt)

#include<Arduino.h>
class SimpleRobot_HLSM {
  public:
    #define IDLE 0
    #define WAITFORSECONDPRESS 1
    #define TESTMOTORS 2
    #define TESTPID 3
    long transitionedAt = 0;
    uint8_t state = IDLE;
    uint8_t prevState;
    void run( ) {
        prevState = state;
        // The following switch statement handles the HLSM's state action logic
        switch(state) {
            case IDLE:
                ExecuteActionIdle();
                break;
            case WAITFORSECONDPRESS:
                ExecuteActionWaitForSecondPress();
                break;
            case TESTMOTORS:
                ExecuteActionTestMotors();
                break;
            case TESTPID:
                ExecuteActionTestPID();
                break;
        }
        // The following switch statement handles the HLSM's state transition logic
        switch(state) {
            case IDLE:
                if ( buttonPress() ) {
                    state = WAITFORSECONDPRESS;
                }
                break;
            case WAITFORSECONDPRESS:
                if ( buttonPress() ) {
                    state = TESTMOTORS;
                }
                if ( wait() ) {
                    state = TESTPID;
                }
                break;
            case TESTMOTORS:
                if ( buttonPress() ) {
                    state = IDLE;
                }
                break;
            case TESTPID:
                if ( buttonPress() ) {
                    state = IDLE;
                }
                break;
        }
        if ( prevState!=state ) {
            transitionedAt = millis();
        }
    }
    void ExecuteActionIdle();
    void ExecuteActionWaitForSecondPress();
    void ExecuteActionTestMotors();
    void ExecuteActionTestPID();
    bool buttonPress();
    bool wait();
};

#endif