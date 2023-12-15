package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    Client(String[] args) {
        DataOutputStream out = null;
        DataInputStream in = null;

        try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            Integer command = Integer.parseInt(args[2]);

            out.writeInt(command);
            switch (command) {
                case 1:
                    out.writeUTF(args[3]);
                    System.out.println(in.readUTF());
                    break;
                case 2:
                    out.writeUTF(args[3]);
                    System.out.println(in.readUTF());
                    break;
                case 3:
                    out.writeUTF(args[3]);
                    out.writeUTF(args[4]);
                    out.writeUTF(args[5]);
                    System.out.println(in.readUTF());
                    break;
                case 4:
                    out.writeUTF(args[3]);
                    System.out.println(in.readUTF());
                    break;
                case 5:
                    out.writeUTF(args[3]);
                    out.writeUTF(args[4]);
                    System.out.println(in.readUTF());
                    break;
                case 6:
                    out.writeUTF(args[3]);
                    out.writeUTF(args[4]);
                    System.out.println(in.readUTF());
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