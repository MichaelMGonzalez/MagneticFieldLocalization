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
PIDController l_pid_controller = PIDController( 2.5, 0, 1);
PIDController r_pid_controller = PIDController( 2.5, 0, 1);



// State variables
// Left Wheel's Angular Velocity
float l_a = 0;
// Right Wheel's Angular Velocity
float r_a = 0;
float velocity = 0;
float angular_velocity = 0;

// Wheel Control values
int l_c = 0;
int r_c = 0;



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
   // Setup encoders
   left_encoder.setup();
   left_encoder.set_thresholds(934, 915);
   right_encoder.setup();
   right_encoder.set_thresholds(1017, 992);
   // Setup differential drive
   ddController.velocity = .05f;
   // Setup PID controllers
   l_pid_controller.dest = ddController.get_wheel_angular_velocity( LEFT_WHEEL );
   r_pid_controller.dest = ddController.get_wheel_angular_velocity( RIGHT_WHEEL );
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
  //test_sensors(true);
  long t = micros();
  // Update encoders
  l_a = left_encoder.update( t );
  r_a = right_encoder.update( t);
  // Update Differential Drive Controller
  ddController.set_wheel_angular_velocity(RIGHT_WHEEL, r_a);
  ddController.set_wheel_angular_velocity(LEFT_WHEEL, l_a);
  // Update velocity beliefs
  velocity = ddController.get_linear_speed();
  angular_velocity = ddController.get_angular_velocity();

  l_c += (int) l_pid_controller.update( l_a, t );
  r_c += (int) r_pid_controller.update( r_a, t );
  set_left_power( l_c, 0 );
  set_right_power( r_c, 0 );
  debug(t);
}

void debug(long t) {
  Serial.print( "L Angular Velocity: " );
  Serial.print( l_a * WHEEL_RADIUS);
  Serial.print( "\t R Angular Velocity: " );
  Serial.print( r_a * WHEEL_RADIUS);
  Serial.print( "\tLinear Velocity: " );
  Serial.print( velocity );
  Serial.print( "\tAngular Velocity: " );
  Serial.print( angular_velocity );
  Serial.print("\tl_c: ");
  Serial.print(l_c);
  Serial.print("\tr_c: ");
  Serial.println(r_c);
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




