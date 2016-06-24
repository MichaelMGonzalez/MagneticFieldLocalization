package hlsm;

import android.app.Activity;
import android.widget.TextView;

import java.util.Calendar;


public abstract class AndroidHLSM extends Thread {
	// Fields
    protected long delayRate = 100;
    protected Activity ctx;
    public long transitionedAt = 0;

    // Abstract Methods
    protected abstract void runHLSM();
    // Public Methods

    public void run() {
        runHLSM( );
    }
    // Protected Methods
    protected void pause(long delayRate) {
    	try {
			Thread.sleep( delayRate );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    protected long timeInState() {
    	return Calendar.getInstance().getTimeInMillis() - transitionedAt;
    }

    protected void setActivityLabel(final TextView v, final String label) {
        ctx.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                v.setText( label );
            }
        });
    }
    
}