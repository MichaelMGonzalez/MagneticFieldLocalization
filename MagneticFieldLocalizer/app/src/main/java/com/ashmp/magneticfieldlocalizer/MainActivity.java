package com.ashmp.magneticfieldlocalizer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;
    TextView timeView;
    TextView teslaView;
    TextView x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing
        teslaView = (TextView) findViewById(R.id.edit_tesla);
        timeView = (TextView) findViewById(R.id.edit_time);
        x = (TextView) findViewById(R.id.x_axis);
        y = (TextView) findViewById(R.id.y_axis);
        z = (TextView) findViewById(R.id.z_axis);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        timeView.setText();
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent event) {
        float[] magnetic_vector = event.values;

        float magnitude = (float)Math.sqrt(Math.pow(magnetic_vector[0],2)+Math.pow(magnetic_vector[1],2)+Math.pow(magnetic_vector[2],2));
        String mag = String.format("%.3f",magnitude);

        // Displaying values
        teslaView.setText(mag);

        x.setText("x-axis: "+String.format("%.3f",magnetic_vector[0]));
        y.setText("y-axis: "+String.format("%.3f",magnetic_vector[1]));
        z.setText("z-axis: "+String.format("%.3f",magnetic_vector[2]));


       // teslaView.setText("I GOT CALLED TOO");
        // Will be used I think
    }

    public void startTime(View view) {

    }
    public void onAccuracyChanged(Sensor s, int i) {
        //not used - needed here because it's and abstract method in SensorEventListener
    }
}
