package com.goindol.teamtalk.client;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket("192.168.0.52", 9500);
        } catch(IOException e) {
            e.printStackTrace();
        }


    }

}
