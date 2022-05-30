package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class makeNoticeController implements Initializable {
    @FXML private BorderPane makeNoticeContainer;
    @FXML private TextField noticeTitle;
    @FXML private TextArea noticeContent;

    @FXML private Button complete;
    public int chatid;
    public userDTO userDTO;



    public void addNotice(){
        // TODO : DB에 공지 제목과 내용 추가
        System.out.println(noticeTitle.getText());
        System.out.println(noticeContent.getText());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        noticeTitle.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                noticeTitle.setText(keyEvent.getText());
            }
        });
        noticeContent.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                noticeContent.setText(keyEvent.getText());
            }
        });



        complete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addNotice();
                Stage stage = (Stage) makeNoticeContainer.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
}



