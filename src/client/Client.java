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

        String ip = "localhost";
        String port = "8080";
        String commandArg = "2";

        try (Socket socket = new Socket(ip, Integer.parseInt(port))) {

            // get the outputstream of client
            out = new ObjectOutputStream(socket.getOutputStream());
            // get the inputstream of client
            in = new ObjectInputStream(socket.getInputStream());

            Command command = Command.values()[Integer.parseInt(commandArg)];

            out.writeObject(command);
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