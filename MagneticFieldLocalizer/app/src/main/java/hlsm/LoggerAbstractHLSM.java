package hlsm;
import java.util.Calendar;
public abstract class LoggerAbstractHLSM  extends AndroidHLSM {
    public final int INIT = 0;
    public final int NOTLOGGING = 1;
    public final int STARTLOGGING = 2;
    public final int LOGGING = 3;
    public final int STOPLOGGING = 4;
    public int state;
    @Override
	protected void runHLSM( ) {
        while(true) {
            int prevState = state;
            // The following switch statement handles the state machine's action logic
            switch(state) {
                case INIT:
                    ExecuteActionInit();
                    break;
                case NOTLOGGING:
                    ExecuteActionNotLogging();
                    break;
                case STARTLOGGING:
                    ExecuteActionStartLogging();
                    break;
                case LOGGING:
                    ExecuteActionLogging();
                    break;
                case STOPLOGGING:
                    ExecuteActionStopLogging();
                    break;
            }
            // The following switch statement handles the HLSM's state transition logic
            switch(state) {
                case INIT:
                    state = NOTLOGGING;
                    break;
                case NOTLOGGING:
                    if ( startSignal() ) {
                        state = STARTLOGGING;
                    }
                    break;
                case STARTLOGGING:
                    state = LOGGING;
                    break;
                case LOGGING:
                    if ( stopSignal() ) {
                        state = STOPLOGGING;
                    }
                    break;
                case STOPLOGGING:
                    state = NOTLOGGING;
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
    protected abstract void ExecuteActionNotLogging();
    protected abstract void ExecuteActionStartLogging();
    protected abstract void ExecuteActionLogging();
    protected abstract void ExecuteActionStopLogging();
    protected abstract boolean startSignal();
    protected abstract boolean stopSignal();
    protected abstract void onTransition();
}