package org.kaas;

import java.net.*;
import java.io.*;

public class GreetServer {
    protected ServerSocket serverSocket;
    protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;

    public GreetServer() {
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public static void main(String[] args) {
        GreetServer server = new GreetServer();
        try {
            server.start(6666);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


