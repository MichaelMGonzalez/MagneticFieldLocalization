#include "PidController.h"
PIDController::PIDController( float p, float i, float d) {
    this->p = p;
    this->i = i;
    this->d = d;
    this->time_prev = 0;
}


void PIDController::setup( long setup_time ) {
    this->time_prev = setup_time;
}

float PIDController::update( float new_val, long curr_time ) {
    // Prepare time variables
    long delta_time = curr_time - time_prev;
    time_prev = curr_time;
    float curr_error = new_val - dest;
    // The derivative of the error with respect to time
    float error_derv = ( curr_error - error_prev ) / delta_time;
    error_prev = curr_error;
    return (p * curr_error) + (d * error_derv);
}
