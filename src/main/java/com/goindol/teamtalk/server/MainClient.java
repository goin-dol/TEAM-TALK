package com.goindol.teamtalk.server;

import com.goindol.teamtalk.client.service.ChatRoomUserListDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class MainClient {
    ChatRoomUserListDAO chatRoomUserListDAO = ChatRoomUserListDAO.getInstance();
    ServerSocket serverSocket;
    Socket socket;
    String key;


    public MainClient(Socket socket) {
        this.socket = socket;

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
                        while(length == -1) throw new NullPointerException();
                        System.out.println("Success!! : " + socket.getRemoteSocketAddress() + " - " + Thread.currentThread().getName());
                        String message = new String(buffer, 0, length, "UTF-8");
                        String[] data = message.split("/");
                        String code = data[0];
                        String roomId = data[1];
                        String value = data[2];

                        System.out.println("code : " + code);
                        if(code.equals("login")) {
                            for(Map.Entry<String, MainClient> entry : MainServer.clients.entrySet()) {
                                entry.getValue().send("login");
                            }
                        }else if(code.equals("chatRoom")) {
                            ArrayList<String> sendUser = chatRoomUserListDAO.getChatRoomUser(Integer.parseInt(roomId));
                            for(Map.Entry<String, MainClient> entry : MainServer.clients.entrySet()) {
                                for(String user : sendUser) {
                                    if(entry.getKey().equals(user)) {
                                        entry.getValue().send(code);
                                    }
                                }
                            }
                        }else if(code.equals("notice")) {
                            ArrayList<String> sendUser = chatRoomUserListDAO.getChatRoomUser(Integer.parseInt(roomId));
                            for(Map.Entry<String, MainClient> entry : MainServer.clients.entrySet()) {
                                for(String user : sendUser) {
                                    if(entry.getKey().equals(user)) {
                                        entry.getValue().send(code);
                                    }
                                }
                            }
                        }else if(code.equals("vote")) {

                        }

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
