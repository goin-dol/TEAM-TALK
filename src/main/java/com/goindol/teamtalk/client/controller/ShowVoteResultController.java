package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.model.VoteResultDTO;
import com.goindol.teamtalk.client.model.VoteVarDTO;
import com.goindol.teamtalk.client.service.VoteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowVoteResultController implements Initializable {

    @FXML
    public BorderPane borderPane;

    @FXML
    public ListView voteResultList;
    private ToggleGroup group = new ToggleGroup();

    public int chatid;
    public int voteId;
    public VoteDAO voteDAO = VoteDAO.getInstance();
    public UserDTO userDTO;

    public void initialVoteList() {
        //TODO DB에서 해당 채팅방 투표의 투표 항목 불러오기
        ObservableList names = FXCollections.observableArrayList();
        List<VoteResultDTO> voteVarDTOList = voteDAO.ShowVoteList(voteId);

        for(VoteResultDTO voteResult : voteVarDTOList) {
            names.add(voteResult);
        }


        voteResultList.setItems(names);
        //voteDAO.checkAnnony(voteId);  <-- 이용해서 익명 여부 확인 후 닉네임 안띄워주기
        //voteResultList.setCellFactory(param -> new listCell());

        //투표결과닉네임
//        List<String> resultNickname = voteDAO.ShowVoteList(vote_id);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setChatRoomId(int chatid) {
        this.chatid = chatid;
    }
    public void setVoteId(int voteId) { this.voteId = voteId; }
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    private class listCell extends ListCell<VoteVarDTO> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button button = new Button("(>)");
        String lastItem;

        public listCell() {
            super();
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(lastItem + " : " + actionEvent);
                }
            });
        }

        @Override
        public void updateItem(VoteVarDTO obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem=obj.getContent();
                label.setText(obj.getContent()!=null ? obj.getContent() : "<null>");
                setGraphic(hbox);
            }
        }
    }
}
