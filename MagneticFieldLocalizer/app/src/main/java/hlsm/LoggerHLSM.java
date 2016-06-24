package hlsm;

import com.ashmp.magneticfieldlocalizer.MainActivity;

/**
 * Created by Michael on 6/23/2016.
 */

public class LoggerHLSM extends LoggerAbstractHLSM {
    public boolean startSig = false;
    public boolean stopSig  = false;
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
        activity.magnetometerLog.CreateNewLog(activity, "Magnetometer_Output" +
                activity.currentTime);
        activity.accelerometerLog.CreateNewLog(activity, "Accelerometer_Output" +
                activity.currentTime);
        activity.resetStartTime = true;
        activity.logging = true;
        activity.registerListeners();
    }

    @Override
    protected void ExecuteActionLogging() {

    }

    @Override
    protected void ExecuteActionStopLogging() {
        activity.magnetometerLog.endLog();
        activity.accelerometerLog.endLog();
        setActivityLabel(activity.timeView, "0s");
        activity.logging = false;
        activity.unregisterListeners();

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
}
