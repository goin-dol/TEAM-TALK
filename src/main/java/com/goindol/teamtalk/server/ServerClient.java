package com.goindol.teamtalk.server;

import com.goindol.teamtalk.client.controller.ChatController;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ServerClient {
    chatLogDAO chatLogDAO = com.goindol.teamtalk.client.service.chatLogDAO.getInstance();
    ServerSocket serverSocket;
    Socket socket;
    String key;


    public ServerClient(Socket socket) {

        this.socket = socket;


        receive();

    }

    public void receiveData() {
                    try {

                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[512];
                        int length = in.read(buffer);
                        while (length == -1) throw new IOException();
                        key = new String(buffer, 0, length, "UTF-8");

                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            System.out.println("Error : " + socket.getRemoteSocketAddress() + " - " + Thread.currentThread().getName());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }

    }


    public void receive() {

        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {

                    while(true) {
                        InputStream in = socket.getInputStream();
                        byte[] buffer = new byte[512];
                        int length = in.read(buffer);
                        while(length == -1) throw new IOException();
                        System.out.println("Success!! : " + socket.getRemoteSocketAddress() + " - " + Thread.currentThread().getName());
                        String message = new String(buffer, 0, length, "UTF-8");
                        String[] data = message.split("/");
                        int chatId = Integer.parseInt(data[0]);
                        String sendMessage = data[1];

                        for(Map.Entry<String, ServerClient> entry : MainServer.clients.entrySet()) {
                            System.out.println("chat Id = " + entry.getKey());
                            String[] keyValue = entry.getKey().split("/");
                            int sendToRoomId = Integer.parseInt(keyValue[0]);
                            if(sendToRoomId == chatId) {
                                entry.getValue().send(sendMessage);
                            }
                        }
                        chatLogDAO.writeLog(chatId, sendMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        System.out.println("Error : " + socket.getRemoteSocketAddress() + " - " + Thread. currentThread().getName());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        //쓰레드 안전적으로 관리
        MainServer.threadPool.submit(thread);
    }

    public void send(String message) {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    try {
                        System.out.println("Error : " + socket.getRemoteSocketAddress() + " - " + Thread. currentThread().getName());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
        MainServer.threadPool.submit(thread);
    }

}
