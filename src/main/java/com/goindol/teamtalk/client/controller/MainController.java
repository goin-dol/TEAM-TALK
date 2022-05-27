package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import com.goindol.teamtalk.client.service.chatRoomUserListDAO;
import com.goindol.teamtalk.client.service.userDAO;
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
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    com.goindol.teamtalk.client.service.chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    com.goindol.teamtalk.client.service.chatRoomUserListDAO chatRoomUserListDAO = com.goindol.teamtalk.client.service.chatRoomUserListDAO.getInstance();
    com.goindol.teamtalk.client.service.chatLogDAO chatLogDAO = com.goindol.teamtalk.client.service.chatLogDAO.getInstance();
    userDTO userDTO;

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
//
    }

    public void showChatRoomList(){
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

    public void openChatRoom(){
        chatRoomListDTO  cr = (chatRoomListDTO ) chatRoomList.getSelectionModel().getSelectedItem();
        if(cr==null)
            return;
        try {

            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("views/ChatView.fxml"));
            Parent root = (Parent) loader.load();
            ChatController chatController = loader.getController();
            chatController.setuserDTO(userDTO);
            chatController.setChatRoomNum(cr.getChatRoom_id());
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

    public void setuserDTO(userDTO userDTO) throws IOException {
        this.userDTO = userDTO;
        userDAO.login(userDTO.getUserId(), userDTO.getUserPassword());
    }

}