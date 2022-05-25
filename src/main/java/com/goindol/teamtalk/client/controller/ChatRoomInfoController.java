package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.userDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatRoomInfoController implements Initializable {

    public int chatid;
    public userDTO userDTO;

    @FXML private ListView chatRoomUserList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;


    }
}
