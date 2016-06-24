package com.ashmp.magneticfieldlocalizer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;

/**
 * Created by Ashmp on 6/6/2016.
 */
public class Logger {
    private File file;
    boolean canWrite = false;

    private String path;
    private long currTime;
    private long timeStamp;

    public Logger(){}

    public void CreateNewLog( String filename) {
        String pathStr = Environment.getExternalStorageDirectory() + "/Magnetometer_Data";
        File dir = new File(pathStr);
        if(!dir.exists())
            dir.mkdir();
        file = new File(dir,filename + "_" + Calendar.getInstance().getTimeInMillis()+".csv");
        try {
            if (!file.exists()){
                file.createNewFile();
                file.setReadable(true);
            }
        }catch(java.io.IOException e){

            System.err.println(e);
        }

        canWrite = true;

    }
    public void writeLog(float[] sensorReadings, long time ) {
        writeLog(null, sensorReadings, time);
    }
    public void writeLog( Float magnitude, float[] sensorReadings, long time ) {
        String stringVal = ( magnitude == null ? "" : magnitude + ",");
        stringVal += sensorReadings[0] + "," + sensorReadings[1] + "," + sensorReadings[2];
        stringVal += "," + time;
        writeLog(stringVal);
    }
    public void writeLog(String output) {
        if (canWrite) {
            FileOutputStream outputStream;
            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                path = file.getAbsolutePath();
                System.out.println(path);
                writer.append(output);
                writer.newLine();
                writer.flush();
                writer.close();

            } catch (java.io.IOException e) {
                System.err.println(e);
            }
        }
    }

    public void endLog() {
        canWrite = false;
    }

    public void setTimeStamp(long timestamp) {
        timeStamp = timestamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getfilepath() {
        return path;
    }

}
