package hlsm;
import java.util.Calendar;
public abstract class SerialAbstractHLSM  extends AndroidHLSM {
    public final int INIT = 0;
    public final int SCANNING = 1;
    public final int CONNECTING = 2;
    public final int CONNECTED = 3;
    public int state = INIT;
    public static final String[] stateLabels = new String[] { "Init", "Scanning", "Connecting", "Connected" };
    @Override
	protected void runHLSM( ) {
        while(true) {
            int prevState = state;
            // The following switch statement handles the state machine's action logic
            switch(state) {
                case INIT:
                    ExecuteActionInit();
                    break;
                case SCANNING:
                    ExecuteActionScanning();
                    break;
                case CONNECTING:
                    ExecuteActionConnecting();
                    break;
                case CONNECTED:
                    ExecuteActionConnected();
                    break;
            }
            // The following switch statement handles the HLSM's state transition logic
            switch(state) {
                case INIT:
                    state = SCANNING;
                    break;
                case SCANNING:
                    if ( available() ) {
                        state = CONNECTING;
                    }
                    break;
                case CONNECTING:
                    if ( onConnect() ) {
                        state = CONNECTED;
                    }
                    break;
                case CONNECTED:
                    break;
            }
            pause( delayRate );
            if ( prevState!=state ) {
                transitionedAt = Calendar.getInstance().getTimeInMillis();
                onTransition();
            }
        }
    }
    protected abstract void ExecuteActionInit();
    protected abstract void ExecuteActionScanning();
    protected abstract void ExecuteActionConnecting();
    protected abstract void ExecuteActionConnected();
    protected abstract boolean available();
    protected abstract boolean onConnect();
    protected abstract void onTransition();
}