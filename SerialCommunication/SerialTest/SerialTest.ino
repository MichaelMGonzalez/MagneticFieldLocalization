#define LED 13
#include "SerialMsgHeader.h"
void setup() {
  // put your setup code here, to run once:
  pinMode(LED, OUTPUT);
  Serial.begin(BAUD_RATE);
}

void loop() {
  report_values();
}

void report_values() {
  
  send_float( REPORTING_STATE, 11.5 );
  send_float( REPORTING_VELOCITY, .05f );
}


void send_float( byte state, float f ) {
  Serial.write( state );
  byte * buf = (byte * ) &f;
  Serial.write( buf, 4 );
  Serial.write( MSG_SENT );
}
