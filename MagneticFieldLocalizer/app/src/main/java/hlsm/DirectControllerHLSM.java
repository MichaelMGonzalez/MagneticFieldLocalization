package hlsm;

import com.ashmp.magneticfieldlocalizer.activity.MainActivity;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Michael on 6/24/2016.
 */
public class DirectControllerHLSM extends DirectControllerAbstractHLSM {
    public DirectControllerHLSM(MainActivity activity, ConcurrentLinkedQueue<String> messageQueue )
    {
        this.messageQueue = messageQueue;
        this.activity = activity;
        ctx = activity;
    }

    private ConcurrentLinkedQueue<String> messageQueue;
    private MainActivity activity;
    private static final String PRESSEDPREFIX  = "P";
    private static final String RELEASEDPREFIX = "R";

    public String readMsg = "NA";


    private void readMessage() {
        return;
        /**String rv = "NA";
        if( !messageQueue.isEmpty()) {
            rv = messageQueue.poll();
            while( !messageQueue.isEmpty() && messageQueue.peek().equals(rv)){
                messageQueue.remove();
            }
        }
        readMsg = rv;*/
    }

    @Override
    protected void ExecuteActionInit() {

    }

    @Override
    protected void ExecuteActionBackward() {
        readMessage();
    }

    @Override
    protected void ExecuteActionLeft() {
        readMessage();
    }

    @Override
    protected void ExecuteActionRight() {
        readMessage();
    }

    @Override
    protected void ExecuteActionForward() {
        readMessage();
    }

    @Override
    protected void ExecuteActionIdle() {
        readMessage();
    }

    @Override
    protected void ExecuteActionForwardLeft() {
        readMessage();
    }

    @Override
    protected void ExecuteActionForwardRight() {
        readMessage();
    }

    @Override
    protected void ExecuteActionBackLeft() {
        readMessage();
    }

    @Override
    protected void ExecuteActionBackRight() {
        readMessage();
    }

    @Override
    protected boolean aDown() {
        return readMsg.equals(PRESSEDPREFIX + "a");
    }

    @Override
    protected boolean wUp() {
        return readMsg.equals(RELEASEDPREFIX + "w");
    }

    @Override
    protected boolean aUp() {
        return readMsg.equals(RELEASEDPREFIX + "a");
    }

    @Override
    protected boolean wDown() {
        return readMsg.equals(PRESSEDPREFIX + "w");
    }

    @Override
    protected boolean sDown() {
        return readMsg.equals(PRESSEDPREFIX + "s");
    }

    @Override
    protected boolean sUp() {
        return readMsg.equals(RELEASEDPREFIX + "s");
    }

    @Override
    protected boolean dUp() {
        return readMsg.equals(RELEASEDPREFIX + "d");
    }

    @Override
    protected boolean dDown() {
        return readMsg.equals(PRESSEDPREFIX + "d");
    }

    @Override
    protected void onTransition() {
        setActivityLabel(activity.controlLabel, stateLabels[state]);
    }
}
