package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML public StackPane stackPane;
    @FXML public AnchorPane chatAnchor;
    @FXML public AnchorPane friendAnchor;
    @FXML public TabPane tabContainer;
    @FXML public Tab chatTab;
    @FXML public Tab friendTab;
    @FXML public ListView chatRoomList;
    @FXML public ListView friendList;
    @FXML public TextField searchFriend;
    @FXML public Button addFriendButton;
    @FXML public Button makeChatRoomButton;

    public userDTO userDTO;

    public void showFriendList(){

//        List<String> strings = new ArrayList<>();
//        strings.add("ta");
//        strings.add("gs");
//        ObservableList<String> fr = FXCollections.observableList(strings);
//        friendList.setItems(fr);
//
    }

    public void showChatRoomList(){
        List<ChatRoom> chatRoom = new ArrayList<>();
        chatRoom.add(new ChatRoom(1,"chat1"));
        chatRoom.add(new ChatRoom(2,"chat2"));
        chatRoom.add(new ChatRoom(3,"chat3"));

        ObservableList<ChatRoom> chatRoomObservableList = FXCollections.observableArrayList();
        chatRoomObservableList.addAll(chatRoom);
        chatRoomList.setItems(chatRoomObservableList);
    }

    public void openChatRoom(){
        ChatRoom cr = (ChatRoom) chatRoomList.getSelectionModel().getSelectedItem();
        if(cr==null)
            return;
        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("views/ChatView.fxml"));
            Parent root = (Parent) loader.load();
            ChatController chatController = (ChatController) loader.getController();
            chatController.getChatRoomId(cr.getId());
            chatController.setuserDTO(userDTO);

            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeChatroom(){

        //TODO : DB에 채팅방 저장
        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/setChatRoomTitleView.fxml"));
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriend(){

        /*TODO : searchFriend.getText() = 사용자가 입력한 친구 닉네임

        이 부분에 디비랑 비교해서 친구 추가 가능한지 확인하고
        가능하면 추가하고 true 불가능하면 false

        */
        boolean addFriendCheck = true;

        if(!addFriendCheck) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("Friend Add Error");
            alert.setContentText("Wrong NickName");
            alert.show();
        }
        ObservableList<String> friendListItems = friendList.getItems();
        friendListItems.add(searchFriend.getText());
        friendList.setItems(friendListItems);
        searchFriend.setText("");

    }

    public void logOut(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchFriend.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                searchFriend.setText(keyEvent.getText());
            }
        });

        searchFriend.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    addFriend();
                }
            }
        });

        addFriendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addFriend();
            }
        });

        makeChatRoomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                makeChatroom();
            }
        });

        chatRoomList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openChatRoom();
            }
        });
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
}