package org.kaas;

import java.io.*;
import java.net.*;

public class UDPClient {
    protected DatagramSocket clientSocket;
    protected BufferedReader in;
    public static void main(String args[]) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];

        String sentence = in.readLine();
        sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length);
        clientSocket.receive(receivePacket);

        String modifiedSentence = new String(receivePacket.getData());

        clientSocket.close();
    }
}
