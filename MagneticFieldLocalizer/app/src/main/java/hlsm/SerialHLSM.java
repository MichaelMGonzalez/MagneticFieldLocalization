package hlsm;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.ashmp.magneticfieldlocalizer.MainActivity;

import java.util.ArrayList;
import java.util.List;

import usbserial.driver.UsbSerialDriver;
import usbserial.driver.UsbSerialPort;
import usbserial.driver.UsbSerialProber;

/**
 * Created by Michael on 6/26/2016.
 */
public class SerialHLSM extends SerialAbstractHLSM {
    private final String TAG = SerialHLSM.class.getSimpleName();

    private UsbManager mUsbManager;
    private final MainActivity activity;

    public SerialHLSM(MainActivity activity) {
        this.ctx = activity;
        this.activity = activity;
    }

    @Override
    protected void ExecuteActionInit() {
        mUsbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
    }

    @Override
    protected void ExecuteActionScanning() {
        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);

        final List<UsbSerialPort> result = new ArrayList<UsbSerialPort>();
        for (final UsbSerialDriver driver : drivers) {
            final List<UsbSerialPort> ports = driver.getPorts();
            Log.d(TAG, String.format("+ %s: %s port%s",
                    driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
            result.addAll(ports);
        }
        setActivityLabel(activity.serialLabel, "" + result.size());
        pause(100);
    }

    @Override
    protected void ExecuteActionConnecting() {

    }

    @Override
    protected void ExecuteActionConnected() {

    }

    @Override
    protected boolean available() {
        return false;
    }

    @Override
    protected boolean onConnect() {
        return false;
    }

    @Override
    protected void onTransition() {

    }
}
