package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.chatRoomUserListDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import com.goindol.teamtalk.client.service.chatRoomUserListDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatRoomInfoController implements Initializable {

    public int chatId;
    public userDTO userDTO;
    public MainController mainController;
    public chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    public chatRoomUserListDAO chatRoomUserListDAO = com.goindol.teamtalk.client.service.chatRoomUserListDAO.getInstance();
    @FXML private TextArea chatRoomUserList;
    @FXML private TextField userInput;
    @FXML private Button invite;
    @FXML private Button exitRoom;


    public void showChatRoomUserList() {
        List<String> strings = new ArrayList<>();
        ArrayList<String> chatRoomUsers = chatRoomUserListDAO.getChatRoomUser(chatId);
        for(String users : chatRoomUsers) {
            chatRoomUserList.appendText(users + "\n");
        }
    }

    public void inviteFriend() {
        chatRoomListDAO.inviteChatRoom(chatId, userInput.getText());
        Platform.runLater(()->{
            chatRoomUserList.appendText(userInput.getText() + "\n");
        });
        userInput.setText("");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatRoomUserList.setEditable(false);
        invite.setOnMouseClicked(event-> {
            chatRoomListDAO.inviteChatRoom(chatId, userInput.getText());
            chatRoomUserList.appendText(userInput.getText() + "\n");
            mainController.send("chatRoom/"+ chatId + "/" + userInput.getText());
            userInput.setText("");
        });
    }

    public void setChatRoomId(int chatId) {
        this.chatId = chatId;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setMainController(MainController mainController) { this.mainController = mainController; }
}
