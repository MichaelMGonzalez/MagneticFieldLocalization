package com.ashmp.magneticfieldlocalizer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import hlsm.LoggerHLSM;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    private LoggerHLSM stateMachine;

    private TextView magnetometerLabelMagnitude;
    private TextView magnetometerLabelX, magnetometerLabelY, magnetometerLabelZ;
    private Button pauseButton;
    private final String NUMFMT = "%.2f";
    public TextView timeView;


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public boolean logging = false;


    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        verifyStoragePermissions(this);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Get resources
        magnetometerLabelMagnitude = (TextView) findViewById(R.id.edit_tesla);
        timeView = (TextView) findViewById(R.id.timeValue);
        pauseButton = (Button) findViewById(R.id.pause_button);
        magnetometerLabelX = (TextView) findViewById(R.id.x_axis);
        magnetometerLabelY = (TextView) findViewById(R.id.y_axis);
        magnetometerLabelZ = (TextView) findViewById(R.id.z_axis);
        // Create state machine
        stateMachine = new LoggerHLSM(this);
        stateMachine.start();
    }

    protected void onResume() {
        super.onResume();
        if( logging ) {
            registerListeners();
            setPauseButtonToPause();
        }
    }
    protected void onPause(){
        super.onPause();
        unregisterListeners();
    }

    public void onSensorChanged(SensorEvent e) {

        float magnitude = stateMachine.processSensorEvent(e);
        if( stateMachine.state == stateMachine.LOGGING )
            setTimeView((float)((e.timestamp - stateMachine.startTime)  / Math.pow(10, 9)));
        System.out.println(stateMachine.startTime);
        switch( e.sensor.getType() ) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                // Displaying values
                setMagnetometerView(e.values, magnitude);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                break;
        }
    }

    public void setMagnetometerView( float[] sensorReadings, float magnitude ) {
        String magnitudeLabel = String.format(NUMFMT, magnitude);
        magnetometerLabelMagnitude.setText(magnitudeLabel);
        magnetometerLabelX.setText(String.format(NUMFMT, sensorReadings[0]));
        magnetometerLabelY.setText(String.format(NUMFMT, sensorReadings[1]));
        magnetometerLabelZ.setText(String.format(NUMFMT, sensorReadings[2]));
    }
    public void pause(View view) {
        unregisterListeners();
        setPauseButtonToContinue();
    }
    public void continueReading(View view) {
        registerListeners();
        setPauseButtonToPause();
    }

    private void setPauseButtonToPause() {
        pauseButton.setText("Pause");
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pause(v);
            }
        });
    }

    private void setPauseButtonToContinue() {
        pauseButton.setText("Continue");
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                continueReading(v);
            }
        });
    }

    public void registerListeners() {
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListeners() {
        sensorManager.unregisterListener(this);
    }

    public void setTimeView( float time ) {
        timeView.setText(String.format(NUMFMT, time) + "s");
    }

    public void onAccuracyChanged(Sensor s, int i) {
        //not used - needed here because it's and abstract method in SensorEventListener
    }
    public void startLog(View v) {
        stateMachine.startSig = true;
        stateMachine.stopSig = false;
    }
    public void stopLog(View v) {
        stateMachine.stopSig = true;
        stateMachine.startSig = false;
        SystemClock.sleep(200);
        setMagnetometerView(new float[]{0, 0, 0}, 0);
    }
}
