#include "IRWheelEncoder.h"
IRWheelEncoder::IRWheelEncoder( uint8_t data_pin )  {
    pin = data_pin;
}

void IRWheelEncoder::setup() {
    last_scan = angular_velocity = 0;
    last_rising_edge = 0;
    waiting_for_edge = false;
}
float IRWheelEncoder::update(long curr_time) {
    int sensor_reading = analogRead(pin);
    if( sensor_reading > detection_threshold && waiting_for_edge ) {
        waiting_for_edge = false;   
        float delta_t = (curr_time - last_rising_edge) / 1000000.0;
        angular_velocity = 6.28318530718 / encoder_count / delta_t  ;
        last_rising_edge = curr_time;
    }
    else if( sensor_reading < fallen_threshold ) {
        waiting_for_edge = true;
    } 
    return angular_velocity;
}
void IRWheelEncoder::set_thresholds(int detection, int fallen) {
    detection_threshold = detection;
    fallen_threshold = fallen;
}
