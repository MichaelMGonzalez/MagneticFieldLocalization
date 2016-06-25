package hlsm;
import java.util.Calendar;
public abstract class DirectControllerAbstractHLSM  extends AndroidHLSM {
    public final int INIT = 0;
    public final int BACKWARD = 1;
    public final int LEFT = 2;
    public final int RIGHT = 3;
    public final int FORWARD = 4;
    public final int IDLE = 5;
    public final int FORWARDLEFT = 6;
    public final int FORWARDRIGHT = 7;
    public final int BACKLEFT = 8;
    public final int BACKRIGHT = 9;
    public int state = INIT;
    public static final String[] stateLabels = new String[] { "Init", "Backward", "Left", "Right", "Forward", "Idle", "ForwardLeft", "ForwardRight", "BackLeft", "BackRight" };
    @Override
	protected void runHLSM( ) {
        while(true) {
            int prevState = state;
            // The following switch statement handles the state machine's action logic
            switch(state) {
                case INIT:
                    ExecuteActionInit();
                    break;
                case BACKWARD:
                    ExecuteActionBackward();
                    break;
                case LEFT:
                    ExecuteActionLeft();
                    break;
                case RIGHT:
                    ExecuteActionRight();
                    break;
                case FORWARD:
                    ExecuteActionForward();
                    break;
                case IDLE:
                    ExecuteActionIdle();
                    break;
                case FORWARDLEFT:
                    ExecuteActionForwardLeft();
                    break;
                case FORWARDRIGHT:
                    ExecuteActionForwardRight();
                    break;
                case BACKLEFT:
                    ExecuteActionBackLeft();
                    break;
                case BACKRIGHT:
                    ExecuteActionBackRight();
                    break;
            }
            // The following switch statement handles the HLSM's state transition logic
            switch(state) {
                case INIT:
                    state = IDLE;
                    break;
                case BACKWARD:
                    if ( aDown() ) {
                        state = BACKLEFT;
                    }
                    if ( dDown() ) {
                        state = BACKRIGHT;
                    }
                    if ( sUp() ) {
                        state = IDLE;
                    }
                    break;
                case LEFT:
                    if ( aUp() ) {
                        state = IDLE;
                    }
                    if ( wDown() ) {
                        state = FORWARDLEFT;
                    }
                    if ( sDown() ) {
                        state = BACKLEFT;
                    }
                    break;
                case RIGHT:
                    if ( dUp() ) {
                        state = IDLE;
                    }
                    if ( wDown() ) {
                        state = FORWARDRIGHT;
                    }
                    if ( sDown() ) {
                        state = BACKRIGHT;
                    }
                    break;
                case FORWARD:
                    if ( aDown() ) {
                        state = FORWARDLEFT;
                    }
                    if ( wUp() ) {
                        state = IDLE;
                    }
                    if ( dDown() ) {
                        state = FORWARDRIGHT;
                    }
                    break;
                case IDLE:
                    if ( aDown() ) {
                        state = LEFT;
                    }
                    if ( wDown() ) {
                        state = FORWARD;
                    }
                    if ( dDown() ) {
                        state = RIGHT;
                    }
                    if ( sDown() ) {
                        state = BACKWARD;
                    }
                    break;
                case FORWARDLEFT:
                    if ( aUp() ) {
                        state = FORWARD;
                    }
                    if ( wUp() ) {
                        state = LEFT;
                    }
                    break;
                case FORWARDRIGHT:
                    if ( dUp() ) {
                        state = FORWARD;
                    }
                    if ( wUp() ) {
                        state = RIGHT;
                    }
                    break;
                case BACKLEFT:
                    if ( aUp() ) {
                        state = BACKWARD;
                    }
                    if ( sUp() ) {
                        state = LEFT;
                    }
                    break;
                case BACKRIGHT:
                    if ( dUp() ) {
                        state = BACKWARD;
                    }
                    if ( sUp() ) {
                        state = RIGHT;
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
    protected abstract void ExecuteActionBackward();
    protected abstract void ExecuteActionLeft();
    protected abstract void ExecuteActionRight();
    protected abstract void ExecuteActionForward();
    protected abstract void ExecuteActionIdle();
    protected abstract void ExecuteActionForwardLeft();
    protected abstract void ExecuteActionForwardRight();
    protected abstract void ExecuteActionBackLeft();
    protected abstract void ExecuteActionBackRight();
    protected abstract boolean aDown();
    protected abstract boolean wUp();
    protected abstract boolean aUp();
    protected abstract boolean wDown();
    protected abstract boolean sDown();
    protected abstract boolean sUp();
    protected abstract boolean dUp();
    protected abstract boolean dDown();
    protected abstract void onTransition();
}