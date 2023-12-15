package client;

import common.Command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Client(String[] args) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Command command = Command.values()[Integer.parseInt(args[2])];

            out.writeObject(command);
            switch (command) {
                case CreateAccount:
                    out.writeObject(args[3]);
                    System.out.println(in.readObject());
                    break;
                case ShowAccounts:
                    out.writeObject(args[3]);
                    System.out.println(in.readObject());
                    break;
                case SendMessage:
                    out.writeObject(args[3]);
                    out.writeObject(args[4]);
                    out.writeObject(args[5]);
                    System.out.println(in.readObject());
                    break;
                case ShowInbox:
                    out.writeObject(args[3]);
                    System.out.println(in.readObject());
                    break;
                case ReadMessage:
                    out.writeObject(args[3]);
                    out.writeObject(args[4]);
                    System.out.println(in.readObject());
                    break;
                case DeleteMessage:
                    out.writeObject(args[3]);
                    out.writeObject(args[4]);
                    System.out.println(in.readObject());
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client(args);
    }
}