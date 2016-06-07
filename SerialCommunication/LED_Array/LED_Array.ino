#include <Wire.h>
#include "Adafruit_LEDBackpack.h"
#include "Adafruit_GFX.h"
#include "SerialMsgHeader.h"

Adafruit_8x8matrix matrix = Adafruit_8x8matrix();

String toPrint = "0";
int p_idx = 0;
byte  draws [] = { 0, 0, 0, 0, 0, 0, 0, 0 };
void setup() {
  // put your setup code here, to run once:
  matrix.begin(0x70);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  matrix.setRotation(3);

}

void serialEvent() {
    while(Serial.available()) {
    int readIn = Serial.read();
    toPrint = String(readIn);
    draws[p_idx] = readIn;
    p_idx = (p_idx+1)%8;
    matrix.clear();
    for( int i = 0; i < 8; i++ ) {
      drawByte(i, draws[i]);
    }
  }
  
  matrix.writeDisplay();
}


void drawByte( int x, byte b ) {
  for( int i = 0; i < 8; i++ ) {
    if( (1 << i) & b )
      matrix.drawPixel(x, i, 1 );
  }
 
}

