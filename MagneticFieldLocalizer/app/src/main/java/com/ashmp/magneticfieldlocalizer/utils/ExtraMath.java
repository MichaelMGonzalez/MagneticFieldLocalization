package com.ashmp.magneticfieldlocalizer.utils;

/**
 * Created by Michael on 6/23/2016.
 */
public class ExtraMath {
    public static float Magnitude( float[] vector ) {
        float rv = 0;
        for( float f : vector ) rv += ( f*f);
        return (float) Math.sqrt(rv);
    }

    public static float[] gravityFilter( float[] accel, float[] gravity ) {
        // In this example, alpha is calculated as t / (t + dT),
        // where t is the low-pass filter's time-constant and
        // dT is the event delivery rate.

        final float alpha = 0.8f;
        float [] rv = new float[3];

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * accel[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * accel[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * accel[2];

        // Remove the gravity contribution with the high-pass filter.
        rv[0] = accel[0] - gravity[0];
        rv[1] = accel[1] - gravity[1];
        rv[2] = accel[2] - gravity[2];
        return rv;
    }
}
