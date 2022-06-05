package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.service.NoticeDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    public MainController mainController;

    private static NoticeDAO noticeDAO = NoticeDAO.getInstance();

    public void addNotice(){
        // TODO : DB에 공지 제목과 내용 추가
        if(noticeDAO.checkNotice(chatid)) {
            if(noticeDAO.AllReadNotice(chatid)) {// -> 다읽었으면 true, 안읽은 인원 있으면 false
                noticeDAO.deleteNotice(chatid);
                noticeDAO.createNotice(userDTO.getNickName(), chatid, noticeTitle.getText(), noticeContent.getText());
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("warning");
                alert.setHeaderText("All Member Read Notice");
                alert.setContentText("Please Read Notice All Member");
                alert.show();
            }
        }else {
            noticeDAO.createNotice(userDTO.getNickName(), chatid, noticeTitle.getText(), noticeContent.getText());
        }
        

        System.out.println(noticeTitle.getText());
        System.out.println(noticeContent.getText());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        complete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addNotice();
                Stage stage = (Stage) makeNoticeContainer.getScene().getWindow();
                stage.close();
                mainController.send("notice/"+ chatid + "/" + noticeTitle.getText());

            }
        });
    }

    public void setChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setMainController(MainController mainController) { this.mainController = mainController; }
}



