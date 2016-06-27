package hlsm;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.ashmp.magneticfieldlocalizer.MainActivity;
import com.ashmp.magneticfieldlocalizer.SerialMsgHeader;

import java.io.IOException;
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
    private List<UsbSerialPort> usbSerialPorts;
    private UsbSerialPort sPort;
    private final MainActivity activity;
    private boolean connected = false;

    public SerialHLSM(MainActivity activity) {
        this.ctx = activity;
        this.activity = activity;
    }

    @Override
    protected void ExecuteActionInit() {
        mUsbManager = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        usbSerialPorts = new ArrayList<UsbSerialPort>();
    }

    @Override
    protected void ExecuteActionScanning() {
        final List<UsbSerialDriver> drivers =
                UsbSerialProber.getDefaultProber().findAllDrivers(mUsbManager);

        usbSerialPorts = new ArrayList<UsbSerialPort>();
        for (final UsbSerialDriver driver : drivers) {
            final List<UsbSerialPort> ports = driver.getPorts();
            Log.d(TAG, String.format("+ %s: %s port%s",
                    driver, Integer.valueOf(ports.size()), ports.size() == 1 ? "" : "s"));
            usbSerialPorts.addAll(ports);
        }

        pause(100);
    }

    @Override
    protected void ExecuteActionConnecting() {
        sPort = usbSerialPorts.get(0);
        UsbDeviceConnection connection = mUsbManager.openDevice(sPort.getDriver().getDevice());
        try {
            sPort.open(connection);
            pause(5000);
            connected = true;
            sPort.setParameters(SerialMsgHeader.BAUD_RATE, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

        } catch (IOException e) {
            Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
            return;
        }
    }

    @Override
    protected void ExecuteActionConnected() {
        try {
            sPort.write(new byte[] { 0 }, 1000 );
        } catch (IOException e) {
            //connected = false;

            e.printStackTrace();
        }
    }

    @Override
    protected void ExecuteActionDisconnected() {
        pause(1000);
    }

    @Override
    protected boolean available() {
        return usbSerialPorts.size() > 0;
    }


    @Override
    protected boolean onConnect() {
        return connected;
    }

    @Override
    protected boolean onDisconnect() {
        return !connected;
    }

    @Override
    protected void onTransition() {
        setActivityLabel(activity.serialLabel, stateLabels[state]);
    }
}
