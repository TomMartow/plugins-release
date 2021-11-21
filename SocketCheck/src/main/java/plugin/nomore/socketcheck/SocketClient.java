package plugin.nomore.socketcheck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketClient implements Runnable {

    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String key;
    private String toServer;
    private boolean state;
    private final String pluginName;
    public boolean run;

    public SocketClient(Socket socket, String key, boolean s, String pluginName) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println("Error during construction SocketClient.");
        }
        this.key = key;
        this.state = s;
        this.pluginName = pluginName;
    }

    @Override
    public void run() {
        System.out.println(getDebug() + "Client has connected to the server.");
        toServer = getKey() + ":" + pluginName + ":" + getState();
        String fromServer;

        sendMessage(toServer);
        fromServer = messageReceived();
        while (fromServer.equals("accept")) {
            setRun(true);
            sendMessage(toServer);
            fromServer = messageReceived();
        }
        shutDown();
        System.out.println(getDebug() + "Client has disconnected from the server.");
    }


    private String getDebug() {
        return "[SOCKETCLIENT " + getTime() + "]: ";
    }

    private String getTime() {
        try {
            return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
        } catch (Exception e) {
            return "getTime() - Unknown Date";
        }
    }

    public void shutDown() {
        setRun(false);
        sendMessage("shutdown");
        setState(false);
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println(getDebug() + "Error closing socket, bufferred reader and printwriter.");
        }
    }

    public void sendMessage(String toServer) {
        try {
            out.println(toServer);
            //System.out.println(getDebug() + "sendMessage: " + toServer);
        } catch (Exception e) {
            System.err.println(getDebug() + "Unable to send message to client.");
        }
    }

    public String messageReceived() {
        try {
            String serverResponse = in.readLine();
            //System.out.println(getDebug() + "messageReceived: " + serverResponse);
            return serverResponse;
        } catch (Exception e) {
            System.err.println(getDebug() + "Unable to send message to client.");
            return "error retrieving resonse from client";
        }
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
        this.toServer = key + ":" + pluginName + ":" + state;
    }

    public boolean getRun() {
        return this.run;
    }

    public void setRun(boolean b) {
        this.run = b;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
