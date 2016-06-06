package com.ashmp.magneticfieldlocalizer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;

    TextView timeView;
    TextView teslaView;
    TextView x,y,z;

    File file;

    private long starttime;
    private long currtime;
    private long time;

    boolean saveTimeStamp;
    boolean writeLog;
    boolean timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //Initializing
        teslaView = (TextView) findViewById(R.id.edit_tesla);
        timeView = (TextView) findViewById(R.id.edit_time);
        x = (TextView) findViewById(R.id.x_axis);
        y = (TextView) findViewById(R.id.y_axis);
        z = (TextView) findViewById(R.id.z_axis);

        // Control/ locks
        saveTimeStamp = true;
        writeLog = false;
        timer = true;

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        Button e = (Button) findViewById(R.id.pause_button);
        e.setText("Pause");
        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pause(v);
            }
        });
    }
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        float[] magnetic_vector = event.values;

        float magnitude = (float)Math.sqrt(Math.pow(magnetic_vector[0],2)+Math.pow(magnetic_vector[1],2)+Math.pow(magnetic_vector[2],2));
        String mag = String.format("%.3f",magnitude);

        if (saveTimeStamp) {
            saveTimeStamp = false;
            setTimeStamp(event.timestamp);
        }
        currtime = event.timestamp;
        if (timer)
            time = currtime - starttime;

        // Displaying values
        timeView.setText(String.format("%.2f",((double)(time)/Math.pow(10,9)))+"s");
        teslaView.setText(mag);

        x.setText("x-axis: "+String.format("%.3f",magnetic_vector[0]));
        y.setText("y-axis: "+String.format("%.3f",magnetic_vector[1]));
        z.setText("z-axis: "+String.format("%.3f",magnetic_vector[2]));

        if (writeLog)
            writeToFile(magnitude,magnetic_vector[0],magnetic_vector[1],magnetic_vector[2]);
       // teslaView.setText("I GOT CALLED TOO");
        // Will be used I think
    }

    public void setTimeStamp (long newTimeStamp) {
        starttime = newTimeStamp;
    }
    /* Writes output to a file named Magnetometer_output.txt (saved in downloads folder)
        Format magnitude,x_val,y_val,z_val,timestamp
     */
    public void writeToFile(float mag, float x_val, float y_val, float z_val) {

        FileOutputStream outputStream;
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(),true));
            writer.append(Float.toString(mag)+","+Float.toString(x_val)+","+Float.toString(y_val)+",");
            writer.append(Float.toString(z_val)+","+Long.toString(time));
            writer.newLine();
            writer.flush();
            writer.close();

        } catch(java.io.IOException e ){}
    }
    // BUTTONS!
    public void stopLog(View view) {
        writeLog = false;
        timer = false;
    }

    public void startLog(View view) {
        writeLog = true;
        saveTimeStamp = true;
        timer = true;
        // Creating output file CSV format
        file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),"Magnetometer_Output"+Long.toString(currtime)+".txt");
    }

    public void pause(View view) {
        onPause();

        Button e =(Button)findViewById(R.id.pause_button);
        e.setText("Continue");
        e.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                continueReading(v);
            }
        });

    }
    public void continueReading(View view) {
        onResume();

        Button e = (Button) findViewById(R.id.pause_button);
        e.setText("Pause");
        e.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pause(v);
            }
        });
    }



    public void onAccuracyChanged(Sensor s, int i) {
        //not used - needed here because it's and abstract method in SensorEventListener
    }
}
