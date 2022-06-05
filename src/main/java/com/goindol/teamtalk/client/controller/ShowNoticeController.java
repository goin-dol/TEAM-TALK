package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.service.noticeDAO;
import com.goindol.teamtalk.client.model.UserDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowNoticeController implements Initializable {
    @FXML private BorderPane noticeCheckContainer;
    @FXML private Label noticeTitle;
    @FXML private TextArea noticeContent;

    @FXML private ListView readUserList;
    public int chatid;
    public UserDTO userDTO;


    private static noticeDAO noticeDAO;

    public void showNoticeContent() {
//        만약 공지사항이 있다면 checkNotice에서 true값이 나옴
//         if(noticeDAO.checkNotice(chatRoom_id)){
//                noticeDAO.showNotice(chatRoom_id);
//        공지 눌렀을 때 그 사람 이름 체킹
//        noticeDAO.checkNotice(nickName,chatRoom_id);
//        공지 읽은 유저 리스트들
//        List<String> users = noticeDAO.readNoticeList(chatRoom_id);
//    }
//        else  {공지사항 없다는 알림 ㄱㄱ }
//        공지 제목, 내용


        Text t1 = new Text("공지내용");
//
    }

    public void showReadUser(){
//        List<String> users = noticeDAO.readNoticeList(chatRoom_id);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noticeContent.setEditable(false);
        showNoticeContent();
    }

    public void setChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
