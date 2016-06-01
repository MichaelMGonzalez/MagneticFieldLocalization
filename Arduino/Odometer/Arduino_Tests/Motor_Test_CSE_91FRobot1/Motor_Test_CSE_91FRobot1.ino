// Libraries we will use
#include <Wire.h>

#include "GadgetManager.h"
#include <PinChangeInt.h>
#include "Motor.h"
#include "ServoMotor.h"
#include "Servo.h"
#include "MomentaryButton.h"
#include "RGBLED.h"
#include "LED.h"
#include <Buzzer.h>
#include <Song.h>
#include "Adafruit_LEDBackpack.h"
#include "Adafruit_GFX.h"

// Constants that determine which pins on the microcontroller we will use.
#define PWMA 3
#define PWMB 5
#define LED_R 6
#define LED_G 9
#define LED_B 10
#define BUTTON 0
#define BUZZER 1
#define STBY 4
#define AIN1 8
#define AIN2 11
#define BIN1 12
#define BIN2 13
#define LED1 A0
#define LED2 A1
#define LED3 A2


#define WAITING 0
#define RUNNING 1
#define SPIN 2
#define FINISHED 3
#define SPIN_FOR 1000

int state = WAITING;

static const uint8_t PROGMEM
  circle_bmp[] =
  { B00111100,
    B01000010,
    B10100101,
    B10000001,
    B10100101,
    B10011001,
    B01000010,
    B00111100 };
static uint8_t rand_array[] =
  { 0, 0, 0, 0, 0, 0, 0, 0 };

// Create the variables that represent the motors and the buttons
Motor motors = Motor(STBY, PWMA, AIN1, AIN2, PWMB, BIN1, BIN2);
MomentaryButton button = MomentaryButton(BUTTON, 200);
RGBLED rgb_led = RGBLED(LED_R, LED_G, LED_B);
LED led1 = LED(LED1);
LED led2 = LED(LED2);
LED led3 = LED(LED3);
Adafruit_8x8matrix matrix = Adafruit_8x8matrix();
Buzzer buzzer = Buzzer(BUZZER);
Song song(buzzer);


long wait_until = 0;

/*
 * This function runs once when the robot first powers on and when it is reset.
 * Setup code goes here.
 */
void setup() {
     motors.setup(); // initialized the motors
     button.setup(); // initializen the button
     rgb_led.setup(); // initialize the RGB LED
     matrix.begin(0x70);
     state = WAITING;
     //Serial.begin(9600);

}

/*
 * This function runs over and over again in an infinite loop.
 */
void loop() {

  motors.forward();


}
