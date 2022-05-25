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

public class MainServer {
    public static MainServer instance = new MainServer();
    MainController mainController;
    ServerSocket serverSocket;
    Socket socket;
    userDTO userDTO;
    ArrayList<friendDTO> friendList;
    ArrayList<chatRoomListDTO> chatRoomList;
    ArrayList<String> allUserList;

    private MainServer() {}

    public static MainServer getInstance() {
        if(instance == null)
            instance = new MainServer();
        return instance;
    }

    public void setMainController(MainController main) {
        this.mainController = main;
    }


    public void ServerStart() {
        userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
        try {
            allUserList = new ArrayList<String>();
            serverSocket = new ServerSocket(9500);
            System.out.println("Server Stared...");

            while(true) {
                socket = serverSocket.accept();
                System.out.println("Succes!!");
                UserThread userThread = new UserThread(socket);
                //allUserList.add(userDTO.getNickName());
                //여기에 모든 사용자한테 친구목록 새로고침하는 소스 부르기
                System.out.println("입장 완료");
            }

        } catch(IOException io) {
            io.printStackTrace();

        }

    }

    public static void main(String[] args)
    {
        MainServer main = MainServer.getInstance();
        main.ServerStart();

    }
}