package project1;


import javafx.application.Application;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;


public class UdpClient{



    public static void main(String[] args) throws IOException {
        final int SOCKET_NUMBER = 3555;
        final String FILE_TO_SEND = "";

        System.out.print("udp client running");

       DatagramSocket socket = new DatagramSocket(SOCKET_NUMBER);






    }






}