package client;

import common.Command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        // String ip = args[1];
        // String port = args[2];
        // String command = args[3];
        // String username = args[4];
        // String authToken = args[4];
        // String recipient = args[5];
        // String msgID = args[5];
        // String msgBody = args[6];

        String ip = "localhost";
        String port = "8080";
        String commandArg = "1";
        String username = "test";
        String authToken = "test";
        String recipient = "test";
        String msgBody = "Body";
        String msgID = "1";

        try (Socket socket = new Socket(ip, Integer.parseInt(port))) {

            // get the outputstream of client
            out = new ObjectOutputStream(socket.getOutputStream());
            // get the inputstream of client
            in = new ObjectInputStream(socket.getInputStream());

            Command command = Command.values()[Integer.parseInt(commandArg)];

            out.writeObject(command);
            switch (command) {
                case CreateAccount:
                    out.writeObject(username);
                    System.out.println(in.readObject());
                    break;
                case ShowAccounts:
                    out.writeObject(authToken);
                    System.out.println(in.readObject());
                    break;
                case SendMessage:
                    out.writeObject(authToken);
                    out.writeObject(recipient);
                    out.writeObject(msgBody);
                    System.out.println(in.readObject());
                    break;
                case ShowInbox:
                    out.writeObject(authToken);
                    System.out.println(in.readObject());
                    break;
                case ReadMessage:
                    out.writeObject(authToken);
                    out.writeObject(msgID);
                    System.out.println(in.readObject());
                    break;
                case DeleteMessage:
                    out.writeObject(authToken);
                    out.writeObject(msgID);
                    System.out.println(in.readObject());
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}