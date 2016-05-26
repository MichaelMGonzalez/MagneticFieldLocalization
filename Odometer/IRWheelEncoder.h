#ifndef IR_WHEEL_ENCODER
#define IR_WHEEL_ENCODER

class IRWheelEncoder {
    public:
        IRWheelEncoder( uint8_t data_pin ); 
        void setup();
        /**
         * Function Name: update(long)
         * Description: This function polls the IR sensor to see whether an 
         *              obstacle is obstructing its view. It should be called
         *              within an update loop with no blocking functions. 
         *              This function will return the last derived belief of 
         *              the wheel's angular velocity
         * Arguments: long curr_time - The current time in microseconds
         */
        float update(long curr_time);

        /**
         * Function Name: set_threshholds(int,int)
         * Description: This function sets the threshold when a wheel spoke 
         *              should be detected and the threshold when a spoke can
         *              be assumed to have passed the sensor.
         * Arguments: int detection - The analog read value at which the sensor
         *                            detects a spoke
         *            int fallen    - The analog read value at which the sensor
         *                            does not a spoke
         */
        void set_threshholds( int detection, int fallen );
    private:
        uint8_t pin;
        bool waiting_for_edge;
        int last_scan;
        long last_rising_edge;
};


#endif
