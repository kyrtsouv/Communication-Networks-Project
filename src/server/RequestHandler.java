package server;

import common.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final Socket clientSocket;
    private final Server server;

    public RequestHandler(Server server, Socket socket) {
        this.server = server;
        this.clientSocket = socket;
    }

    public void run() {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            // get the outputstream of client
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            // get the inputstream of client
            in = new ObjectInputStream(clientSocket.getInputStream());

            Command command = (Command) in.readObject();

            switch (command) {
                case CreateAccount:
                    break;
                case ShowAccounts:
                    break;
                case SendMessage:
                    break;
                case ShowInbox:
                    break;
                case ReadMessage:
                    break;
                case DeleteMessage:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
