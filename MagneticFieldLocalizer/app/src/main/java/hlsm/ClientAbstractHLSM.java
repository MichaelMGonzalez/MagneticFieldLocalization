package hlsm;
import java.util.Calendar;
public abstract class ClientAbstractHLSM  extends AndroidHLSM {
    public final int INIT = 0;
    public final int CONNECTING = 1;
    public final int CONNECTED = 2;
    public final int RECONNECTING = 3;
    public int state = INIT;
    public static final String[] stateLabels = new String[] { "Init", "Connecting", "Connected", "Reconnecting" };
    @Override
	protected void runHLSM( ) {
        while(true) {
            int prevState = state;
            // The following switch statement handles the state machine's action logic
            switch(state) {
                case INIT:
                    ExecuteActionInit();
                    break;
                case CONNECTING:
                    ExecuteActionConnecting();
                    break;
                case CONNECTED:
                    ExecuteActionConnected();
                    break;
                case RECONNECTING:
                    ExecuteActionReconnecting();
                    break;
            }
            // The following switch statement handles the HLSM's state transition logic
            switch(state) {
                case INIT:
                    state = CONNECTING;
                    break;
                case CONNECTING:
                    if ( onConnection() ) {
                        state = CONNECTED;
                    }
                    break;
                case CONNECTED:
                    if ( onDisconnect() ) {
                        state = RECONNECTING;
                    }
                    break;
                case RECONNECTING:
                    if ( onConnection() ) {
                        state = CONNECTED;
                    }
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
    protected abstract void ExecuteActionConnecting();
    protected abstract void ExecuteActionConnected();
    protected abstract void ExecuteActionReconnecting();
    protected abstract boolean onDisconnect();
    protected abstract boolean onConnection();
    protected abstract void onTransition();
}