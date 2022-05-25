package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import com.goindol.teamtalk.client.service.chatRoomUserListDAO;
import com.goindol.teamtalk.client.service.userDAO;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainController{
    LoginController loginController;
    userDTO userDTO;
    chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    chatRoomUserListDAO chatRoomUserListDAO = com.goindol.teamtalk.client.service.chatRoomUserListDAO.getInstance();
    chatLogDAO chatLogDAO = com.goindol.teamtalk.client.service.chatLogDAO.getInstance();
    Socket socket;
    BufferedReader br;
    PrintWriter pw;
    @FXML public StackPane stackPane;
    @FXML public AnchorPane chatAnchor;
    @FXML public AnchorPane friendAnchor;
    @FXML public TabPane tabContainer;
    @FXML public Tab chatTab;
    @FXML public Tab friendTab;
    @FXML public ListView chatRoomList;
    @FXML public ListView friendList;


    public MainController() {




    }

    public void setSocket(Socket socket) throws IOException{
        System.out.println("Main CTR Success!!");
        this.socket = socket;

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void setBrPw(BufferedReader br, PrintWriter pr) {
        this.br = br;
        this.pw = pw;
    }

    public void showFriendList(){
        List<String> strings = new ArrayList<>();
        if(userDTO == null) {
            strings.add("친구가 없습니다.");
        }else {
            ArrayList<friendDTO> friends = userDAO.getFriendList(userDTO.getNickName());
            for(int i = 0; i < friends.size(); i++) {
                strings.add(friends.get(i).getFriendNickName());
            }
        }

        ObservableList<String> fr = FXCollections.observableList(strings);
        friendList.setItems(fr);
    }

    public  void showChatRoomList(){
        List<chatRoomListDTO> strings = new ArrayList<>();
        if(userDTO == null) {
            strings.add(new chatRoomListDTO(0, "채팅방이 없습니다."));
        }else {
            ArrayList<chatRoomListDTO> chatRoom = chatRoomListDAO.getChatRoomName(userDTO.getNickName());
            for(int i = 0; i < chatRoom.size(); i++) {
                strings.add(chatRoom.get(i));
            }
        }

        ObservableList<chatRoomListDTO> chatRoomObservableList = FXCollections.observableArrayList();

        chatRoomObservableList.addAll(strings);

        chatRoomList.setItems(chatRoomObservableList);
    }

    public void test(){
        chatRoomListDTO chatRoomListDTO = (chatRoomListDTO) chatRoomList.getSelectionModel().getSelectedItem();
        System.out.println("correct : "+chatRoomListDTO.getChatRoom_id());
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/ChatView.fxml"));
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setuserDTO(userDTO userDTO) throws IOException {
        System.out.println("@$!$$$");
        this.userDTO = userDTO;
        socket = new Socket("192.168.0.52", 9500);
        userDAO.login(userDTO.getUserId(), userDTO.getUserPassword(), socket.getInetAddress().toString());
    }

}
