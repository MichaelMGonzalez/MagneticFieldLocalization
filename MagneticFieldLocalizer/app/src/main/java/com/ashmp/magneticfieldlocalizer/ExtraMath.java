package com.ashmp.magneticfieldlocalizer;

/**
 * Created by Michael on 6/23/2016.
 */
public class ExtraMath {
    public static float Magnitude( float[] vector ) {
        float rv = 0;
        for( float f : vector ) rv += ( f*f);
        return (float) Math.sqrt(rv);
    }
}
