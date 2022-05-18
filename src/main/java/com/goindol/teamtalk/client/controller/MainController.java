package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import com.goindol.teamtalk.client.service.userDAO;
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

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static userDAO userDAO;
    public static userDTO userDTO;
    public static Connection conn;

    @FXML public StackPane stackPane;
    @FXML public AnchorPane chatAnchor;
    @FXML public AnchorPane friendAnchor;
    @FXML public TabPane tabContainer;
    @FXML public Tab chatTab;
    @FXML public Tab friendTab;
    @FXML public ListView chatRoomList;
    @FXML public ListView friendList;

    public void showFriendList(){
        List<String> strings = new ArrayList<>();
        ArrayList<friendDTO> friends = userDAO.getFriendList(userDTO.getNickName());
        for(int i = 0; i < friends.size(); i++) {
            strings.add(friends.get(i).getFriendNickName());
        }

        ObservableList<String> fr = FXCollections.observableList(strings);
        friendList.setItems(fr);
    }

    public  void showChatRoomList(){
        /*List<String> strings = new ArrayList<>();
        strings.add("chaat1");
        strings.add("chaat2");

        List<ChatRoom> chatRoom = new ArrayList<>();
        chatRoom.add(new ChatRoom(1,"chat1"));
        chatRoom.add(new ChatRoom(2,"chat2"));
        chatRoom.add(new ChatRoom(3,"chat3"));

        ObservableList<ChatRoom> chatRoomObservableList = FXCollections.observableArrayList();

        chatRoomObservableList.addAll(chatRoom);

        chatRoomList.setItems(chatRoomObservableList);*/
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


}
