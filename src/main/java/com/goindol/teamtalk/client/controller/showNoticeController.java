package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.userDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class showNoticeController implements Initializable {
    @FXML private BorderPane noticeCheckContainer;
    @FXML private Label noticeTitle;
    @FXML private TextFlow noticeContent;

    @FXML private ListView readUserList;
    public int chatid;
    public userDTO userDTO;


    public void showNoticeContent(){
        Text t1 = new Text("공지내용");
        noticeContent.getChildren().add(t1);
    }

    public void showReadUser(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showNoticeContent();
    }

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
}
