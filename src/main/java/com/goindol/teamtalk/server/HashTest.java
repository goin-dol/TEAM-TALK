package com.goindol.teamtalk.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class HashTest {

    public static void main(String[] args) {

        try {
            InetAddress ia = InetAddress.getLocalHost();
            String ip_str = ia.toString();
            String ip = ip_str.substring(ip_str.indexOf("/") + 1);
            System.out.println(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
