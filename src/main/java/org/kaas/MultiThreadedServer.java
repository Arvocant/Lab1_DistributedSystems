package org.kaas;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
    private ServerSocket serverSocket;
    public static class ClientHandler extends Thread{

        private Socket clientSocket;
        private DataOutputStream out;
        private BufferedReader in;

        public ClientHandler(Socket socket){
            this.clientSocket = socket;
        }


        @Override
        public void run(){

            try {
                out = new DataOutputStream(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));

                String inputLine;
                label:
                while((inputLine = in.readLine())!= null){
                    switch (inputLine) {
                        case ".":
                            break label;
                        case "otag.jpg":
                            sendFile("otag.jpg");
                            break;
                        case "lab1.pdf":
                            sendFile("lab1.pdf");
                            break;
                    }

                }
                in.close();
                out.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendFile(String filename) throws IOException {
            String filepath = "src/files/serverfiles/"+filename;
            File file = new File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);
            //Marshalling the data (haha I've paid attention to the classes)
            int bytes;
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer))!= -1){
                out.write(buffer,0,bytes);
                out.flush();
            }
            fileInputStream.close();
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
            server.start(6666);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
