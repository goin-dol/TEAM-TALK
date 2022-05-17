package com.goindol.teamtalk.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class MainController {

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
        strings.add("ta");
        strings.add("gs");
        ObservableList<String> fr = FXCollections.observableList(strings);
        friendList.setItems(fr);
    }

    public  void showChatRoomList(){
        List<String> strings = new ArrayList<>();
        strings.add("chaat1");
        strings.add("chaat2");

        List<ChatRoom> chatRoom = new ArrayList<>();
        chatRoom.add(new ChatRoom(1,"chat1"));
        chatRoom.add(new ChatRoom(2,"chat2"));
        chatRoom.add(new ChatRoom(3,"chat3"));
        ObservableList<ChatRoom> li = FXCollections.observableList(chatRoom);

        chatRoomList.setItems(li);
    }

    public void test(){
        System.out.println("correct"+chatRoomList.getSelectionModel().getSelectedItem());

    }


}
