#ifndef PIDCONTROLLER_H
#define PIDCONTROLLER_H

class PIDController {
  public:
    /** Constructor **/
    // Constructor that sets PID values
    PIDController( float p, float i, float d);

    /** Functions **/

    /**
     * Function Name: setup( long )
     * Description:   This function currently only sets the clock of the 
     *                PIDController
     * Arguments:
     *            long  setup_time - The time to set time_prev
     */

    void setup( long setup_time );
    /**
     * Function Name: update( float, long )
     * Description:   This function runs at the top of a Arduino update loop
     *                to produce PID values. The calling code should be 
     *                 non-blocking
     * Arguments:
     *            float new_val   - The new value a sensor is reading
     *            long  curr_time - The current time of the microcontroller
     */
    float update( float new_val, long curr_time );

    /** Variables**/
    // Constants used by the controller
    float p, i, d; 
    // Desination value
    float dest;
  private:
    // Error terms
    float error_prev;
    // Time variables
    long time_prev;
};

#endif
