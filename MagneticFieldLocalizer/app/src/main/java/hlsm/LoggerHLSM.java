package hlsm;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.ashmp.magneticfieldlocalizer.ExtraMath;
import com.ashmp.magneticfieldlocalizer.Logger;
import com.ashmp.magneticfieldlocalizer.MainActivity;

/**
 * Created by Michael on 6/23/2016.
 */

public class LoggerHLSM extends LoggerAbstractHLSM {
    public boolean startSig = false;
    public boolean stopSig  = false;
    public long startTime = -1;
    private Logger magnetometerLog = new Logger();
    private Logger accelerometerLog = new Logger();
    private MainActivity activity;
    public LoggerHLSM( MainActivity activity ) {
        this.activity = activity;
        ctx = activity;
    }
    @Override
    protected void ExecuteActionInit() {
        activity.setMagnetometerView(new float[]{0, 0, 0}, 0);
    }

    @Override
    protected void ExecuteActionNotLogging() {

    }

    @Override
    protected void ExecuteActionStartLogging() {
        magnetometerLog.CreateNewLog("Magnetometer_Output");
        accelerometerLog.CreateNewLog("Accelerometer_Output");
        activity.logging = true;
        activity.registerListeners();
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
        activity.logging = false;
        activity.unregisterListeners();

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

    public float processSensorEvent(SensorEvent e) {
        if( state == WAITFORSENSOR )
            startTime = e.timestamp;
        float magnitude = ExtraMath.Magnitude(e.values);
        long time = e.timestamp - startTime;
        switch( e.sensor.getType() ) {
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerLog.writeLog(magnitude, e.values, time);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerLog.writeLog(e.values, time);
                break;
        }
        return magnitude;
    }

}
