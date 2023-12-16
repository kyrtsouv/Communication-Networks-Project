package server;

import java.net.ServerSocket;

public class Server {

    private ServerSocket serverSocket = null;
    private Database db = null;

    public Server(String port) {
        db = new Database();
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            while (true) {
                new RequestHandler(db, serverSocket.accept()).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Server(args[0]);
    }
}