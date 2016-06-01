#include "robot.h"
#include "SerialMsgHeader.h"
#include <DifferentialDrive.h>
#include <PIDController.h>

DifferentialDrive ddController = DifferentialDrive(WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);
void setup() {
  // put your setup code here, to run once:
  Serial.begin(BAUD_RATE);
}

void loop() {
  // put your main code here, to run repeatedly:

}
