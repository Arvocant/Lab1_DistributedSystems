package org.kaas;

import java.io.*;
import java.net.Socket;

public class FileClient {
    protected Socket clientSocket;
    protected PrintWriter out;
    protected DataInputStream in;

    public FileClient() {

    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new DataInputStream(clientSocket.getInputStream());
    }

    public void stopConnection() throws IOException {
        out.println(".");
        in.close();
        out.close();
        clientSocket.close();
    }


    public File requestFile(String filename) throws IOException {
        String filepath = "src/files/receivedfiles/"+filename;
        File file = new File(filepath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        out.println(filename);
        //unmarshalling the received data and returning the file
        int bytes;
        byte[] buffer = new byte[8 * 1024];
        //TODO: smth goes wrong here and the stream never returns -1, so this.in keeps waiting for smth on the stream
        while ((bytes = in.read(buffer))!=-1){
            fileOutputStream.write(buffer,0,bytes);
        }
        fileOutputStream.close();
        return file;
    }

}
