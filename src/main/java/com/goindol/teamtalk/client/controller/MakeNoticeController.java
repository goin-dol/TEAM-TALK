package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.service.NoticeDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
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
        if (!noticeTitle.getText().isBlank() && !noticeContent.getText().isBlank()) {
            if (noticeDAO.checkNotice(chatid)) {
                if (noticeDAO.AllReadNotice(chatid)) {// -> 다읽었으면 true, 안읽은 인원 있으면 false
                    noticeDAO.deleteNotice(chatid);
                    noticeDAO.createNotice(userDTO.getNickName(), chatid, noticeTitle.getText(), noticeContent.getText());
                } else {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("warning");
                    alert.setHeaderText("공지 에러");
                    alert.setContentText("아직 공지를 확인하지 않은 인원이 있습니다\n공지 생성을 진행하시겠습니까?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        noticeDAO.deleteNotice(chatid);
                        noticeDAO.createNotice(userDTO.getNickName(), chatid, noticeTitle.getText(), noticeContent.getText());
                    }
                }
            }
            else {
            noticeDAO.createNotice(userDTO.getNickName(), chatid, noticeTitle.getText(), noticeContent.getText());
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("공지 에러");
            alert.setContentText("입력하지 않은 제목이나 내용이 있습니다.");
            alert.show();
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
                mainController.send("notice/"+ chatid + "/" + noticeTitle.getText());
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

    public void setMainController(MainController mainController) { this.mainController = mainController; }
}



