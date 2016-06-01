#include "DifferentialDrive.h"
#include "../UnitConversions.h"
#include "../robot.h"
#include <iostream>

using namespace std;
int main() {
    float rads_per_s = RPM_TO_RPS( 40 );
    cout << "Radians per second: " << rads_per_s << endl;
    DifferentialDrive controller = 
               DifferentialDrive( WHEEL_RADIUS, DISTANCE_BETWEEN_WHEELS);
    controller.set_wheel_angular_velocity(RIGHT_WHEEL, rads_per_s);
    controller.set_wheel_angular_velocity(LEFT_WHEEL, .5 * rads_per_s);
    cout << "Linear Speed: " << controller.get_linear_speed() << endl;
    cout << "Angular Velocity: " << controller.get_angular_velocity () << endl;
    return 0;
}
