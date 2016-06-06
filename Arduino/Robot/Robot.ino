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
#include <MomentaryButton.h>
#include <PinChangeInt.h>
#include <Motor.h>
#include <Servo.h>
#include <ServoMotor.h>
#include <GadgetManager.h>
#include <LED.h>

// 190 Libraries
#include "robot.h"
#include "SerialMsgHeader.h"
#include <DifferentialDrive.h>
#include <PIDController.h>
#include <IRWheelEncoder.h>

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
#define LED1_CONTROL 11
#define LED2_CONTROL 12
#define MOMENTARYBUTTON1_SENSE 13

#define DISTANCESENSOR1_A A0
#define DISTANCESENSOR2_A A1
#define DISTANCESENSOR3_A A2
#define ENCODER_LEFT A3
#define ENCODER_RIGHT A4

/** ======================================================================= **\
|** ------------------------- Object Declarations ------------------------- **|
\** ======================================================================= **/

Motor motor(MOTOR1_STBY, MOTOR1_PWMA, MOTOR1_AIN1, MOTOR1_AIN2, MOTOR1_PWMB, MOTOR1_BIN1, MOTOR1_BIN2);
LED led1(LED1_CONTROL);
LED led2(LED2_CONTROL);
MomentaryButton button(MOMENTARYBUTTON1_SENSE);
DifferentialDrive ddController = DifferentialDrive(WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);
IRWheelEncoder left_encoder = IRWheelEncoder(ENCODER_LEFT);
IRWheelEncoder right_encoder = IRWheelEncoder(ENCODER_RIGHT);
PIDController pidController = PIDController( 1, 0, 1);

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
   motor.setup();
   led1.setup();
   led2.setup();
   button.setup();
   left_encoder.setup();
   left_encoder.set_thresholds(934, 915);
   right_encoder.setup();
   right_encoder.set_thresholds(1017, 992);
   Serial.begin(38400);
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
  set_power( 255, 0 );
  //test_sensors(true);
  long t = micros();
  test_odom(t);
}

void test_odom(long t) {
  float l_a = left_encoder.update( t );
  float r_a = right_encoder.update( t);
  Serial.print( "L Angular Velocity: " );
  Serial.print( l_a * WHEEL_RADIUS);
  Serial.print( "\t R Angular Velocity: " );
  Serial.println( r_a * WHEEL_RADIUS);
}

void test_sensors(bool csv) {
  int val_1 = analogRead(ENCODER_LEFT);
  int val_2 = analogRead(ENCODER_RIGHT);
  String before_left  = csv ? ""   : "Left Sensor: ";
  String before_right = csv ? ", " : "\tRight Sensor: ";
  String before_time  = csv ? ", " : "\t Time Stamp: ";  
  Serial.print(before_left);
  Serial.print( val_1 );
  Serial.print(before_right);
  Serial.print( val_2 );
  Serial.print(before_time);
  Serial.println( micros() );

}

void set_right_power( int val, int dir ) {
  motor.move(1, val, dir);
}

void set_left_power( int val, int dir ) {
  motor.move(0, val, dir);
}

void set_power( int val, int dir ) {
  set_right_power( val, dir );
  set_left_power( val, dir );
}




