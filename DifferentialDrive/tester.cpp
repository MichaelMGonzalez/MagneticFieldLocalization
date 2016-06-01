#include "DifferentialDrive.h"
#include <iostream>

using namespace std;
int main() {
    DifferentialDrive controller = DifferentialDrive( 1, 10);
    controller.set_wheel_angular_velocity(RIGHT_WHEEL, 10);
    controller.set_wheel_angular_velocity(LEFT_WHEEL, -10);
    cout << "Linear Speed: " << controller.get_linear_speed() << endl;
    cout << "Angular Velocity: " << controller.get_angular_velocity () << endl;
    return 0;
}
