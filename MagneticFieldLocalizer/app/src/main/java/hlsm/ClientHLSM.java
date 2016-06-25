package hlsm;

import com.ashmp.magneticfieldlocalizer.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Michael on 6/24/2016.
 */
public class ClientHLSM extends ClientAbstractHLSM {
    public ClientHLSM(MainActivity activity) {
        this.activity = activity;
        this.ctx = activity;
    }
    private MainActivity activity;
    public String ip = "192.168.1.4";
    public int portNumber = 4444;


    // Network streams
    private Socket socket;
    private PrintWriter networkOut;
    private BufferedReader networkIn;

    // Control signals
    private boolean connected;
    private DirectControllerHLSM directControllerHLSM;
    // Message buffer
    ConcurrentLinkedQueue<String> messageBuffer;
    @Override
    protected void ExecuteActionInit() {
        connected = false;
        messageBuffer = new ConcurrentLinkedQueue<>();
        directControllerHLSM = new DirectControllerHLSM(activity, messageBuffer);
        directControllerHLSM.start();
    }

    @Override
    protected void ExecuteActionConnecting() {
        connect();
    }

    @Override
    protected void ExecuteActionConnected() {
        String fromServer = null;
        try {
            fromServer = networkIn.readLine();
            setActivityLabel(activity.networkMsgLabel, fromServer);
            directControllerHLSM.readMsg = fromServer;
            messageBuffer.add(fromServer);
            //System.out.println("Server: " + fromServer);

        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }

    @Override
    protected void ExecuteActionReconnecting() {
        connect();
    }

    @Override
    protected boolean onDisconnect() {
        return !connected;
    }

    @Override
    protected boolean onConnection() {
        return connected;
    }

    @Override
    protected void onTransition() {
        setActivityLabel( activity.networkStatusLabel, stateLabels[state]);
    }

    protected void connect() {
        try {
            socket = new Socket(ip, portNumber);
            networkOut = new PrintWriter(socket.getOutputStream(), true);
            networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
        } catch (Exception e) {
            e.printStackTrace();
            connected = false;
        }
    }
}
