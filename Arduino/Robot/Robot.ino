/** ############################# Robot Name ############################## **\
|** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~***********~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **|
|** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~/           \~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **|
|** ============================{  MagneticFL }============================ **|
|** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\           /~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **|
|** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~***********~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ **|
\** #############################[[[[[^_^]]]]]############################# **/



/** ======================================================================= **\
|** ------------------------------ Libraries ------------------------------ **|
\** ======================================================================= **/

// Gadgetron Libraries
#include "MomentaryButton.h"
#include "PinChangeInt.h"
#include "DistanceSensor.h"
#include "Motor.h"
#include "Servo.h"
#include "ServoMotor.h"
#include "GadgetManager.h"
#include "LED.h"

// 190 Libraries
#include "robot.h"
#include "SerialMsgHeader.h"
#include <DifferentialDrive.h>
#include <PIDController.h>

/** ======================================================================= **\
|** ---------------------------- Pin Constants ---------------------------- **|
\** ======================================================================= **/

#define MOTOR1_STBY 4
#define MOTOR1_PWMA 3
#define MOTOR1_AIN1 6
#define MOTOR1_AIN2 8
#define MOTOR1_PWMB 5
#define MOTOR1_BIN1 9
#define MOTOR1_BIN2 10
#define DISTANCESENSOR1_A A0
#define DISTANCESENSOR2_A A1
#define DISTANCESENSOR3_A A2
#define LED1_CONTROL 11
#define LED2_CONTROL 12
#define MOMENTARYBUTTON1_SENSE 13

/** ======================================================================= **\
|** ------------------------- Object Declarations ------------------------- **|
\** ======================================================================= **/

Motor motor1(MOTOR1_STBY, MOTOR1_PWMA, MOTOR1_AIN1, MOTOR1_AIN2, MOTOR1_PWMB, MOTOR1_BIN1, MOTOR1_BIN2);
DistanceSensor distancesensor1(DISTANCESENSOR1_A);
DistanceSensor distancesensor2(DISTANCESENSOR2_A);
DistanceSensor distancesensor3(DISTANCESENSOR3_A);
LED led1(LED1_CONTROL);
LED led2(LED2_CONTROL);
MomentaryButton momentarybutton1(MOMENTARYBUTTON1_SENSE);
DifferentialDrive ddController = DifferentialDrive(WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);

/** ======================================================================= **\
|** --------------------------- Setup Function ---------------------------- **|
|** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **|
|** ............................. Description ............................. **|
|** The setup() function runs --ONCE-- when the Arduino boots up. As the    **|
|** name implies, it's useful to add code that 'sets up' your Gadget to     **|
|** run correctly.                                                          **|
|** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **|
\** ======================================================================= **/

void setup() {
   motor1.setup();
   distancesensor1.setup();
   distancesensor2.setup();
   distancesensor3.setup();
   led1.setup();
   led2.setup();
   momentarybutton1.setup();
   Serial.begin(BAUD_RATE);
}

/** ======================================================================= **\
|** ---------------------------- Loop Function ---------------------------- **|
|** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **|
|** ............................. Description ............................. **|
|** The loop() function runs continuously after the setup() function        **|
|** finishes and while the Arduino is running. In other words, this         **|
|** function is called repeatly over and over again when it reaches the     **|
|** end of the function. This function is where the majority of your        **|
|** program's logic should go.                                              **|
|** %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% **|
\** ======================================================================= **/

void loop() {
}
