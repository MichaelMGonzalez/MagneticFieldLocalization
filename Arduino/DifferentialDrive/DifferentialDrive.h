#ifndef DIFFERENTIAL_DRIVE
#define DIFFERENTIAL_DRIVE

#define RIGHT_WHEEL (char) 27
#define LEFT_WHEEL  (char) 28

class DifferentialDrive {
    public:
        DifferentialDrive( float wheel_radius, float wheel_distance );
        /**
         * Function Name: get_linear_speed()
         * Description: Returns an estimate of the magnitude of the robot's 
         *              velocity as meters per second
         */
        float get_linear_speed();
        /**
         * Function Name: get_angular_velocity()
         * Description: Returns an estimate of the robot's angular velocity as
         *              radians per second.
         */
        float get_angular_velocity();
        void set_wheel_angular_velocity( char wheel, float omega );
        float get_wheel_angular_velocity( char wheel );
        float velocity, angular_velocity;
    private:
        float wheel_radius;
        float wheel_distance;
        float distance_traveled_left;
        float distance_traveled_right;
};

#endif
