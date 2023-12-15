package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private final Socket clientSocket;
    private final Database db;

    public RequestHandler(Database db, Socket socket) {
        this.db = db;
        this.clientSocket = socket;
    }

    public void run() {
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());

            Integer command = in.readInt();
            String authToken = null;
            String msgID = null;
            String recipient = null;
            String msgBody = null;

            switch (command) {
                case 1:
                    String username = in.readUTF();
                    out.writeUTF(db.createAccount(username));
                    break;
                case 2:
                    authToken = in.readUTF();
                    out.writeUTF(db.showAccounts(authToken));
                    break;
                case 3:
                    authToken = in.readUTF();
                    recipient = in.readUTF();
                    msgBody = in.readUTF();
                    out.writeUTF(db.sendMessage(authToken, recipient, msgBody));
                    break;
                case 4:
                    authToken = in.readUTF();
                    out.writeUTF(db.showInbox(authToken));
                    break;
                case 5:
                    authToken = in.readUTF();
                    msgID = in.readUTF();
                    out.writeUTF(db.readMessage(authToken, msgID));
                    break;
                case 6:
                    authToken = in.readUTF();
                    msgID = in.readUTF();
                    out.writeUTF(db.deleteMessage(authToken, msgID));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
