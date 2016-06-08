package com.ashmp.magneticfieldlocalizer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.annotation.FloatRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    TextView timeView;
    TextView teslaView;
    TextView x,y,z;
    TextView acc;

    private long currtime;
    private long time;

    boolean saveTimeStamp;
    boolean acc_ts;
    boolean timer;

    Logger magnetometerLog = new Logger();
    Logger accelerometerLog = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Initializing
        teslaView = (TextView) findViewById(R.id.edit_tesla);
        timeView = (TextView) findViewById(R.id.edit_time);
        x = (TextView) findViewById(R.id.x_axis);
        y = (TextView) findViewById(R.id.y_axis);
        z = (TextView) findViewById(R.id.z_axis);
        //acc = (TextView) findViewById(R.id.edit_acc);
        // Control/ locks
        saveTimeStamp = true;
        acc_ts = true;
        timer = false;

    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

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
        Sensor sensor = event.sensor;
        currtime = event.timestamp;

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float[] magnetic_vector = event.values;


            float magnitude = (float) Math.sqrt(Math.pow(magnetic_vector[0], 2) + Math.pow(magnetic_vector[1], 2) + Math.pow(magnetic_vector[2], 2));
            String mag = String.format("%.3f", magnitude);

            if (saveTimeStamp) {
                saveTimeStamp = false;
                magnetometerLog.setTimeStamp(event.timestamp);
            }

            if (timer)
                time = currtime - magnetometerLog.getTimeStamp();
            else
                time = 0;
            //currtime = event.timestamp;
            // Displaying values
            timeView.setText(String.format("%.2f", ((double) (time) / Math.pow(10, 9))) + "s");
            teslaView.setText(mag);

            float x_val = magnetic_vector[0];
            float y_val = magnetic_vector[1];
            float z_val = magnetic_vector[2];

            x.setText("x-axis: " + String.format("%.3f", x_val));
            y.setText("y-axis: " + String.format("%.3f", y_val));
            z.setText("z-axis: " + String.format("%.3f", z_val));

            String output = Float.toString(magnitude) + "," + Float.toString(x_val) + "," + Float.toString(y_val) + "," + Float.toString(z_val) +
                    "," + Long.toString(time);
            magnetometerLog.writeLog(output);
        }
        else if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if (acc_ts) {
                acc_ts = false;
                accelerometerLog.setTimeStamp(event.timestamp);
            }

            if (timer)
                time = currtime - magnetometerLog.getTimeStamp();

            float[] accVector = event.values;

            String output = Float.toString(accVector[0])+","+Float.toString(accVector[1])+","+Float.toString(accVector[2])+","+Long.toString(time);
            accelerometerLog.writeLog(output);
        }
    }

    // BUTTONS!
    public void stopLog(View view) {
        magnetometerLog.endLog();
        accelerometerLog.endLog();
        timer = false;
    }

    public void startLog(View view) {
        magnetometerLog.CreateNewLog("Magnetometer_Output"+Long.toString(currtime)+".txt");
        accelerometerLog.CreateNewLog("Accelerometer_Output"+Long.toString(currtime)+".txt");
        saveTimeStamp = true;
        timer = true;

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
