package org.kaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    private ServerSocket serverSocket;
    public static class ClientHandler extends Thread{

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }

        @Override
        public void run(){

            try {
                out = new PrintWriter(clientSocket.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));

                String inputLine;
                while((inputLine = in.readLine())!= null){
                    if(".".equals(inputLine)){
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine);
                }
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public MultiThreadedServer(){

    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        //permanently listen and create new clientHandlers
        while(true){//Every client gets a new thread here
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch(IOException e){
                break;
            }
        }
        stop();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer();
        try {
            server.start(5555);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
