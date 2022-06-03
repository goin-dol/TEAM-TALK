package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.service.noticeDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MakeNoticeController implements Initializable {
    @FXML private BorderPane makeNoticeContainer;
    @FXML private TextField noticeTitle;
    @FXML private TextArea noticeContent;

    @FXML private Button complete;
    public int chatid;
    public UserDTO userDTO;


    private static noticeDAO noticeDAO;

    public void addNotice(){
        // TODO : DB에 공지 제목과 내용 추가
//        if(noticeDAO.AllReadNotice(chatRoom_id)) -> 다읽었으면 true, 안읽은 인원 있으면 false
//        아래 메소드 사용시 채팅 내용이 없으면 db에 저장이 안됨 그니까 오류메시지만 출력하면 됨
//        noticeDAO.createNotice(nickName,chatRoom_id, title, content);


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

    public void setChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}



