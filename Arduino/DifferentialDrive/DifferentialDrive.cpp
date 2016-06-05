#include "DifferentialDrive.h"

DifferentialDrive::DifferentialDrive( float wheel_radius, float wheel_distance) {
    this-> wheel_radius   = (float)wheel_radius;
    this-> wheel_distance = (float)wheel_distance;
    this->velocity;
    this->angular_velocity;
}

void DifferentialDrive::set_wheel_angular_velocity( char wheel, float omega ) {
    float distance = (float)(omega * wheel_radius);
    if( wheel == RIGHT_WHEEL ) distance_traveled_right = distance;
    else  distance_traveled_left = distance;
}
float DifferentialDrive::get_angular_velocity() {
    float diff = (distance_traveled_right - distance_traveled_left);
    return ((distance_traveled_right - distance_traveled_left) / wheel_distance);
}
float DifferentialDrive::get_linear_speed() {
    return (distance_traveled_right + distance_traveled_left) / 2.0;
}
float DifferentialDrive::get_wheel_angular_velocity( char wheel ) {
    float offset = (angular_velocity * wheel_distance) / 2.0;
    offset *= wheel == RIGHT_WHEEL ? 1 : -1;
    return (velocity / wheel_radius ) + offset;
}
