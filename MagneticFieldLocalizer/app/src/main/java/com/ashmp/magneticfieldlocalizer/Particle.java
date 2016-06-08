package com.ashmp.magneticfieldlocalizer;

/**
 * Created by Ashmp on 6/7/2016.
 */
public class Particle {
    float x, y, theta;
    float weight;
    float pose;
    float lifeTime;
    float[] magneticFieldReadings;
    //Robot robot;

    Particle (float x_val, float y_val, float theta_val) { //, Robot robot_obj) {
        x = x_val  ;
        y = y_val;
        theta = theta_val;

        //robot = robot_obj;
        weight = 0;//1.0/robot.numOfParticles(); - need to figure out where to store num of partic. info

        lifeTime = 100000;

        updatePose();
    }

    public void updatePose() {
        // pose = get_pose(x,y,theta); - figure where will this function be defined
    }

    public void rotateRadians(float radians) {
        theta = radians;
        updatePose();
    }

    public void rotate(float degrees) {
        rotateRadians((float)Math.toRadians(degrees));
    }

    public void moveForward() {
       // x,y = projectPoint(float distance, float theta); - figure it out
    }

    public boolean isValid() {
        float v = 0; //replace for function that returns if cell is valid
        if (v!=0) {
            return false;
        }
        return true;
    }
}
