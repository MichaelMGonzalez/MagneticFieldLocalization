package com.ashmp.magneticfieldlocalizer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private Sensor accelerometer;
    private TextView timeView;
    private TextView teslaView;
    private TextView x,y,z;
    private Button pauseButton;
    TextView acc;

    private long currentTime;
    private long startTime;

    boolean resetStartTime;
    boolean acc_ts;


    private Logger magnetometerLog = new Logger();
    private Logger accelerometerLog = new Logger();


    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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

        //Initializing
        teslaView = (TextView) findViewById(R.id.edit_tesla);
        timeView = (TextView) findViewById(R.id.timeValue);
        pauseButton = (Button) findViewById(R.id.pause_button);
        x = (TextView) findViewById(R.id.x_axis);
        y = (TextView) findViewById(R.id.y_axis);
        z = (TextView) findViewById(R.id.z_axis);
        //acc = (TextView) findViewById(R.id.edit_acc);
        // Control/ locks
        resetStartTime = true;
        acc_ts = true;

    }

    protected void onResume() {
        super.onResume();
        registerListeners();
        setPauseButtonToPause();
    }
    protected void onPause(){
        super.onPause();
        unregisterListeners();
    }

    public void onSensorChanged(SensorEvent event) {
        if (resetStartTime) {
            resetStartTime = false;
            startTime = event.timestamp;
        }
        Sensor sensor = event.sensor;
        currentTime = event.timestamp;
        float x_val = event.values[0];
        float y_val = event.values[1];
        float z_val = event.values[2];
        float magnitude = (float)Math.sqrt(x_val * x_val + y_val * y_val + z_val * z_val);
        long time = currentTime - startTime;
        String output = "";
        switch( sensor.getType() ) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                String magnitudeLabel = String.format("%.2f", magnitude);

                // Displaying values
                timeView.setText(String.format("%.2f", (1.0 * time  / Math.pow(10, 9))) + "s");
                teslaView.setText(magnitudeLabel);
                x.setText(String.format("%.2f", x_val));
                y.setText(String.format("%.2f", y_val));
                z.setText(String.format("%.2f", z_val));

                output = Float.toString(magnitude) + "," + Float.toString(x_val) + "," +
                        Float.toString(y_val) + "," + Float.toString(z_val) + ","
                        + Long.toString(time);
                magnetometerLog.writeLog(output);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                output = Float.toString(x_val)+ "," +Float.toString(y_val)
                        + "," + Float.toString(z_val)+ "," +Long.toString(time);
                accelerometerLog.writeLog(output);
                break;
        }
    }


    public void stopLog(View view) {
        magnetometerLog.endLog();
        accelerometerLog.endLog();
        unregisterListeners();
    }

    public void startLog(View view) {
        magnetometerLog.CreateNewLog(this,"Magnetometer_Output" + currentTime);
        accelerometerLog.CreateNewLog(this, "Accelerometer_Output" + currentTime);

        resetStartTime = true;
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
        pauseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                continueReading(v);
            }
        });
    }

    private void registerListeners() {
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    private void unregisterListeners() {
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor s, int i) {
        //not used - needed here because it's and abstract method in SensorEventListener
    }
}
