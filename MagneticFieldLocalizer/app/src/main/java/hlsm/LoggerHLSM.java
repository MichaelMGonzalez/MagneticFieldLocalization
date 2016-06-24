package hlsm;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ashmp.magneticfieldlocalizer.ExtraMath;
import com.ashmp.magneticfieldlocalizer.Logger;
import com.ashmp.magneticfieldlocalizer.MainActivity;

/**
 * Created by Michael on 6/23/2016.
 */

public class LoggerHLSM extends LoggerAbstractHLSM implements SensorEventListener {
    public boolean startSig = false;
    public boolean stopSig  = false;
    public long startTime = -1;
    public float[] gravity = new float[3];
    private SensorManager sensorManager;
    private Sensor magnetometer, accelerometer, gravitySensor;
    private Logger magnetometerLog = new Logger();
    private Logger accelerometerLog = new Logger();
    private MainActivity activity;
    public LoggerHLSM( MainActivity activity ) {
        this.activity = activity;
        ctx = activity;
    }
    @Override
    protected void ExecuteActionInit() {
        activity.resetLabels();
        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    @Override
    protected void ExecuteActionNotLogging() {

    }

    @Override
    protected void ExecuteActionStartLogging() {
        magnetometerLog.CreateNewLog("Magnetometer_Output");
        accelerometerLog.CreateNewLog("Accelerometer_Output");
        registerListeners();
    }

    @Override
    protected void ExecuteActionLogging() {

    }

    @Override
    protected void ExecuteActionStopLogging() {
        magnetometerLog.endLog();
        accelerometerLog.endLog();
        startTime = -1;
        setActivityLabel(activity.timeView, "0s");
        unregisterListeners();

    }

    @Override
    protected void ExecuteActionWaitForSensor() {

    }

    @Override
    protected boolean onSensorChanged() {
        return startTime != -1;
    }

    @Override
    protected boolean startSignal() {
        boolean rv = startSig;
        if( rv ) startSig = false;
        return rv;
    }

    @Override
    protected boolean stopSignal() {
        boolean rv = stopSig;
        if( rv ) stopSig= false;
        return rv;
    }

    @Override
    protected void onTransition() {

    }


    @Override
    public void onSensorChanged(SensorEvent e) {
        if( state == WAITFORSENSOR ) startTime = e.timestamp;
        long time = e.timestamp - startTime;
        switch( e.sensor.getType() ) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                float magnitude = ExtraMath.Magnitude(e.values);
                magnetometerLog.writeLog(magnitude, e.values, time);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float[] linearAcceleration = ExtraMath.gravityFilter(e.values, gravity);
                accelerometerLog.writeLog(linearAcceleration, time);
                break;
            case Sensor.TYPE_GRAVITY:
                gravity = e.values;
                break;
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void registerListeners() {
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(activity, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(activity, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void unregisterListeners() {
        sensorManager.unregisterListener(this);
        sensorManager.unregisterListener(activity);
    }
}
