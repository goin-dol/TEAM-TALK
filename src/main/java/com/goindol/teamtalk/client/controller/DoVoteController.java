package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.model.VoteVarDTO;
import com.goindol.teamtalk.client.service.voteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DoVoteController implements Initializable {

    @FXML public BorderPane borderPane;
    @FXML public ListView voteList;
    @FXML public Button voteButton;
    private ToggleGroup group = new ToggleGroup();
    private voteDAO voteDAO;

    public int chatid;
    public UserDTO userDTO;

    public void saveVoteResult() {
        String temp = group.getSelectedToggle().toString();
        String value = temp.substring(temp.indexOf("'")+1, temp.length()-1);
        //TODO temp
        System.out.println(value);
//        content로 투표 리스트의 id 값인 voteVar_id를 구함
//        int voteVar_id = voteDAO.ReadVoteVarByContent(content);
//        투표 선택 (예외 처리 해줘야함 => 이미 투표한 리스트 선택,
//        voteDAO.choiceVote(voteVar_id,voteVar_id,content,nickName);

    }

    public void initialVoteList() {
        //TODO DB에서 해당 채팅방 투표의 투표 항목 불러오기
        ObservableList names = FXCollections.observableArrayList();
        List<VoteVarDTO> voteVarDTOList = new ArrayList<>();
        voteVarDTOList.add(new VoteVarDTO(1,"에그드랍"));
        voteVarDTOList.add(new VoteVarDTO(1,"한솥"));
        voteVarDTOList.add(new VoteVarDTO(1,"남경"));
        names.addAll(voteVarDTOList);


        voteList.setItems(names);
        voteList.setCellFactory(param -> new RadioListCell());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialVoteList();

        voteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saveVoteResult();
                Stage stage = (Stage) borderPane.getScene().getWindow();
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

    private class RadioListCell extends ListCell<VoteVarDTO> {
        @Override
        public void updateItem(VoteVarDTO obj, boolean empty) {
            setStyle("-fx-border-style: hidden hidden hidden hidden");
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                RadioButton radioButton = new RadioButton(obj.getContent());
                radioButton.setToggleGroup(group);
                setGraphic(radioButton);
            }
        }
    }
}
