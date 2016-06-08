package com.ashmp.magneticfieldlocalizer;

import android.os.Environment;
import android.os.SystemClock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by Ashmp on 6/6/2016.
 */
public class Logger {
    private File file;
    boolean canWrite = false;

    private String path;
    private long currTime;
    private long timeStamp;

    Logger(){}

    public void CreateNewLog(String filename) {
        file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),filename);//"Magnetometer_Output"+Long.toString(currtime)+".txt");
        try {
            if (!file.exists()){
                file.createNewFile();
            }
        }catch(java.io.IOException e){
            System.err.println(e);
        }

        canWrite = true;

    }

    public void writeLog(String output) {
        if (canWrite) {
            FileOutputStream outputStream;
            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
                path = file.getAbsolutePath();
                writer.append(output);//Float.toString(mag)+","+Float.toString(x_val)+","+Float.toString(y_val)+",");
                //writer.append(Float.toString(z_val)+","+Long.toString(time));
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
