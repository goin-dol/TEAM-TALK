package com.goindol.teamtalk.server;



import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.controller.LoginController;
import com.goindol.teamtalk.client.controller.MainController;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.userDAO;
import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
    ServerSocket serverSocket;
    Socket socket;
    List<Thread> list;

    public MainServer() {
        list = new ArrayList<Thread>();
        System.out.println("Server is started..");
    }

    public void giveAndTake() {
        try {
            serverSocket = new ServerSocket(9500);
            serverSocket.setReuseAddress(true);
            while(true) {
                socket = serverSocket.accept(); //1. 소캣 접속 대기 2. 소켓 접속 허락
                ServerSocketThread thread = new ServerSocketThread(this, socket);
                thread.run();
                addClient(thread);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addClient(ServerSocketThread thread) {
        list.add(thread);
        System.out.println("Client 1명 입장. 현재 " + list.size() + "명");
    }

    public synchronized void removeClient(ServerSocketThread thread) {
        list.remove(thread);
        System.out.println("Client 1명 퇴장. 현재 " + list.size() + "명");
    }

    //채팅 내용 전달
    public synchronized void broadCasting(String str) {
        for(int i = 0; i < list.size(); i++) {
            ServerSocketThread thread = (ServerSocketThread) list.get(i);
            thread.sendMessage(str);
        }
    }

    public static void main(String[] args) {
        MainServer server = new MainServer();
        server.giveAndTake();
    }
}