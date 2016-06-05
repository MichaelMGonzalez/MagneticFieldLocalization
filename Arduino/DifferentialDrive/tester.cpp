#include "DifferentialDrive.h"
#include "../Robot/UnitConversions.h"
#include "../Robot/robot.h"
#include <iostream>

using namespace std;
void test_forward_calc() {
    DifferentialDrive controller = 
               DifferentialDrive( WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);
    float rads_per_s = RPM_TO_RPS( 40 );
    cout << "Radians per second: " << rads_per_s << endl;
    controller.set_wheel_angular_velocity(RIGHT_WHEEL, rads_per_s);
    controller.set_wheel_angular_velocity(LEFT_WHEEL,  rads_per_s);
    cout << "Linear Speed: " << controller.get_linear_speed() << endl;
    cout << "Angular Velocity: " << controller.get_angular_velocity () << endl;
}
void test_inv_calc() {
    DifferentialDrive controller = 
               DifferentialDrive( WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);
    controller.velocity = .14*WHEEL_RADIUS;
    controller.angular_velocity = -.02;
    cout << controller.get_wheel_angular_velocity( RIGHT_WHEEL ) << endl;
    cout << controller.get_wheel_angular_velocity( LEFT_WHEEL )  << endl;
}
int main() {
    //test_forward_calc();
    test_inv_calc();
    return 0;
}
