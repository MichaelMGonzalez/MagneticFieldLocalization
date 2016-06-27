package com.ashmp.magneticfieldlocalizer;

import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import java.util.Set;

import hlsm.AndroidHLSM;
import hlsm.ClientHLSM;
import hlsm.LoggerHLSM;
import hlsm.SerialHLSM;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private LoggerHLSM loggerHLSM;
    private ClientHLSM clientHLSM;
    private SerialHLSM serialHLSM;

    private TextView magnetometerLabelMagnitude, accelerometerLabelMagnitude;
    private TextView magnetometerLabelX, magnetometerLabelY, magnetometerLabelZ,
                     accelerometerLabelX, accelerometerLabelY, accelerometerLabelZ;
    private Button pauseButton;
    private final String NUMFMT = "%.2f";
    public TextView timeView, networkStatusLabel, networkMsgLabel, controlLabel, serialLabel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        ActivityUtils.verifyStoragePermissions(this);


        // Get resources
        timeView = (TextView) findViewById(R.id.timeValue);
        pauseButton = (Button) findViewById(R.id.pause_button);
        magnetometerLabelX = (TextView) findViewById(R.id.x_axis);
        magnetometerLabelY = (TextView) findViewById(R.id.y_axis);
        magnetometerLabelZ = (TextView) findViewById(R.id.z_axis);
        magnetometerLabelMagnitude = (TextView) findViewById(R.id.edit_tesla);

        accelerometerLabelX = (TextView) findViewById(R.id.accelerometer_label_x);
        accelerometerLabelY = (TextView) findViewById(R.id.accelerometer_label_y);
        accelerometerLabelZ = (TextView) findViewById(R.id.accelerometer_label_z);
        accelerometerLabelMagnitude = (TextView) findViewById(R.id.accelerometer_label_mag);
        networkStatusLabel = (TextView) findViewById(R.id.NetworkStatusValue);
        networkMsgLabel = (TextView) findViewById(R.id.lastMsgReceivedValue);
        controlLabel = (TextView) findViewById(R.id.controlValue);
        serialLabel = (TextView) findViewById(R.id.serial_status_value);
        // Setup toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Create state machine
        loggerHLSM = new LoggerHLSM(this);
        loggerHLSM.start();
        clientHLSM = new ClientHLSM(this);
        clientHLSM.start();
        serialHLSM = new SerialHLSM(this);
        serialHLSM.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.set_ip:
                SetIPDialog ipDialog = new SetIPDialog();
                ipDialog.show(getFragmentManager(), "SetIP");
                break;
        }
        return true;
    }
    protected void onResume() {
        super.onResume();
        if( loggerHLSM.state == loggerHLSM.LOGGING ) {
            loggerHLSM.registerListeners();
            setPauseButtonToPause();
        }
    }
    protected void onPause(){
        super.onPause();
        loggerHLSM.unregisterListeners();
    }

    public void onSensorChanged(SensorEvent e) {
        if( loggerHLSM.state == loggerHLSM.LOGGING )
            setTimeView((float)((e.timestamp - loggerHLSM.startTime)  / Math.pow(10, 9)));

        switch( e.sensor.getType() ) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                // Displaying values
                float magnitude = ExtraMath.Magnitude(e.values);
                setMagnetometerView(e.values, magnitude);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float[] accel = ExtraMath.gravityFilter(e.values, loggerHLSM.gravity);
                setAccelerometerView(accel, ExtraMath.Magnitude(accel));
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
    public void setAccelerometerView( float[] sensorReadings, float magnitude ) {
        String magnitudeLabel = String.format(NUMFMT, magnitude);
        accelerometerLabelMagnitude.setText(magnitudeLabel);
        accelerometerLabelX.setText(String.format(NUMFMT, sensorReadings[0]));
        accelerometerLabelY.setText(String.format(NUMFMT, sensorReadings[1]));
        accelerometerLabelZ.setText(String.format(NUMFMT, sensorReadings[2]));
    }
    public void pause(View view) {
        loggerHLSM.unregisterListeners();
        setPauseButtonToContinue();
    }
    public void continueReading(View view) {
        loggerHLSM.registerListeners();
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


    public void setTimeView( float time ) {
        timeView.setText(String.format(NUMFMT, time) + "s");
    }
    public void resetLabels() {
        setMagnetometerView(new float[]{0, 0, 0}, 0);
        setAccelerometerView(new float[]{0, 0, 0}, 0);
    }

    public void onAccuracyChanged(Sensor s, int i) {
        //not used - needed here because it's and abstract method in SensorEventListener
    }
    public void startLog(View v) {
        loggerHLSM.startSig = true;
        loggerHLSM.stopSig = false;
    }
    public void stopLog(View v) {
        loggerHLSM.stopSig = true;
        loggerHLSM.startSig = false;
        SystemClock.sleep(500);
        resetLabels();
    }
}
