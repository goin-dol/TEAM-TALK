package com.goindol.teamtalk.server;


import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.controller.ChatController;
import com.goindol.teamtalk.client.controller.LoginController;
import com.goindol.teamtalk.client.controller.MainController;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.chatRoomUserListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import com.goindol.teamtalk.client.service.chatRoomUserListDAO;
import com.goindol.teamtalk.client.service.userDAO;
import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread implements Runnable {

    private Socket socket;
    private MainController mainController;
    public static String nickname;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;



    @Override
    public void run() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("views/ChatView.fxml"));
        ChatController chatController = loader.getController();
    }


    /*public static void send(String msg) throws IOException {
        Message createMessage = new Message();
        createMessage.setName(nickname);
        createMessage.setType(MessageType.CHAT);
        createMessage.setMsg(msg);
        oos.writeObject(createMessage);
        oos.flush();
    }*/


}

/*
try {
        LoginController.getInstance().showScene();
        outputStream = socket.getOutputStream();
        oos = new ObjectOutputStream(outputStream);
        is = socket.getInputStream();
        input = new ObjectInputStream(is);
        } catch (IOException e) {
        e.printStackTrace();
        }

        try {
        while(socket.isConnected()) {
        Message message = null;
        message = (Message) input.readObject();
        if(message != null) {
        switch (message.getType()) {
        case NOTICE :
        //공지관련 행위
        break;
        case VOTE :
        //투포 관련 행위
        break;
        case CHAT :
        //채팅 입력 행위
        break;
        case EXIT :
        //로그아웃 행위
        break;

        }
        }
        }
        } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        //강제 로그아웃시 logout 메소드 실행
        }*/
