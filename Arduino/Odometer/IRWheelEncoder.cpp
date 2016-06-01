#include "IRWheelEncoder.h"
IRWheelEncoder::IRWheelEncoder( uint8_t data_pin )  {
    pin = data_pin;
}

void IRWheelEncoder::setup() {
    pegs_seen = last_scan = 0;
    last_rising_edge = rotation_period = 0;
    waiting_for_edge = false;
}
float IRWheelEncoder::update(long curr_time) {
    int sensor_reading = analogRead(pin);
    if( sensor_reading > last_scan && sensor_reading > detection_threshold && waiting_for_edge ) {
        //Serial.println("Rising edge detected!");
        //Serial.println( time_read - last_rising_edge );
        waiting_for_edge = false;   
        pegs_seen = (pegs_seen+1)%encoder_count;
        //Serial.println(pegs_seen);
        if( pegs_seen == 0 ) {
            //Serial.println( time_read - rotation_period );
            rotation_period = curr_time - last_rising_edge;
        }
        last_rising_edge = curr_time;
    }
    else if( sensor_reading < fallen_threshold ) {
        waiting_for_edge = true;
    } 
    last_scan = sensor_reading;
    return sensor_reading;
}
void IRWheelEncoder::set_thresholds(int detection, int fallen) {
    detection_threshold = detection;
    fallen_threshold = fallen;
}
