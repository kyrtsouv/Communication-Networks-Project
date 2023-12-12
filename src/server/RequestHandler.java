package server;

import common.Command;

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
            String authToken = null;
            String msgID = null;
            switch (command) {
                case CreateAccount:
                    String username = (String) in.readObject();
                    out.writeObject(server.createAccount(username));
                    break;
                case ShowAccounts:
                    authToken = (String) in.readObject();
                    out.writeObject(server.showAccounts(authToken));
                    break;
                case SendMessage:
                    authToken = (String) in.readObject();
                    String recipient = (String) in.readObject();
                    String msgBody = (String) in.readObject();
                    out.writeObject(server.sendMessage(authToken, recipient, msgBody));
                    break;
                case ShowInbox:
                    authToken = (String) in.readObject();
                    out.writeObject(server.showInbox(authToken));
                    break;
                case ReadMessage:
                    authToken = (String) in.readObject();
                    msgID = (String) in.readObject();
                    out.writeObject(server.readMessage(authToken, msgID));
                    break;
                case DeleteMessage:
                    authToken = (String) in.readObject();
                    msgID = (String) in.readObject();
                    out.writeObject(server.deleteMessage(authToken, msgID));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
