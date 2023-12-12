package server;

import common.Account;
import common.Message;

import java.util.Map;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private Map<String, Account> accounts;
    private Map<Account, Integer> authTokens;

    // initialization of socket and input stream
    private ServerSocket serverSocket = null;

    // implementation of constructor
    public Server(int port) {
        // start server and wait for a connection
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                new RequestHandler(this, serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        // String port = args[0];

        String port = "8080";

        new Server(Integer.parseInt(port));
    }
}