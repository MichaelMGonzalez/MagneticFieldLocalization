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

    TextView timeView;
    TextView teslaView;
    TextView x,y,z;

    File file;

    private long currtime;
    private long time;

    boolean saveTimeStamp;
    boolean timer;

    Logger fileLog = new Logger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
            fileLog.setTimeStamp(event.timestamp);
        }

        if (timer)
            time = event.timestamp - fileLog.getTimeStamp();
        currtime = event.timestamp;
        // Displaying values
        timeView.setText(String.format("%.2f",((double)(time)/Math.pow(10,9)))+"s");
        teslaView.setText(mag);

        float x_val = magnetic_vector[0];
        float y_val = magnetic_vector[1];
        float z_val = magnetic_vector[2];

        x.setText("x-axis: "+String.format("%.3f",x_val));
        y.setText("y-axis: "+String.format("%.3f",y_val));
        z.setText("z-axis: "+String.format("%.3f",z_val));

        String output = Float.toString(magnitude)+","+Float.toString(x_val)+","+Float.toString(y_val)+","+Float.toString(z_val)+
                ","+Long.toString(time);
        fileLog.writeLog(output);
    }

    // BUTTONS!
    public void stopLog(View view) {
        fileLog.endLog();
        timer = false;
    }

    public void startLog(View view) {
        fileLog.CreateNewLog(this, "Magnetometer_Output"+Long.toString(currtime)+".txt");
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
