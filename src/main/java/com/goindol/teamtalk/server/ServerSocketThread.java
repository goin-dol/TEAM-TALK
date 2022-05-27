package com.goindol.teamtalk.server;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ServerSocketThread extends Thread{
    Socket socket;
    MainServer server;
    BufferedReader in;
    PrintWriter out;
    String name;
    String threadName;

    public ServerSocketThread(MainServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        threadName = super.getName();

        System.out.println(socket.getInetAddress() + "님이 입장하셨습니다.");
        System.out.println("Thread name : " + threadName);
    }

    public void sendMessage(String str) {
        out.println(str);
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //true : auto Flush
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            while(true) {
                String str_in = in.readLine();
                server.broadCasting("[" + name + "] : " + str_in);
            }
        }catch(SocketException e) {
            server.broadCasting("[" + name + "] 님이 퇴장하셨습니다.");
            System.out.println(threadName + " 퇴장했습니다.");
            server.removeClient(this);
        }catch(IOException e) {
            server.broadCasting("[" + name + "] 님이 퇴장하셨습니다.");
            System.out.println(threadName + " 퇴장했습니다.");
            server.removeClient(this);
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

}