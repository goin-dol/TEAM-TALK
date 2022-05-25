package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.model.voteVarDTO;
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

import javax.swing.border.Border;
import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;

public class doVoteController implements Initializable {

    @FXML public BorderPane borderPane;
    @FXML public ListView voteList;
    @FXML public Button voteButton;
    private ToggleGroup group = new ToggleGroup();
    private voteDAO voteDAO;

    public int chatid;
    public userDTO userDTO;



    public void saveVoteResult() {
        String temp = group.getSelectedToggle().toString();
        String value = temp.substring(temp.indexOf("'")+1, temp.length()-1);
        //TODO temp
        System.out.println(value);
    }

    public void initialVoteList() {
        //TODO DB에서 해당 채팅방 투표의 투표 항목 불러오기
        ObservableList names = FXCollections.observableArrayList();
        List<voteVarDTO> voteVarDTOList = new ArrayList<>();
        voteVarDTOList.add(new voteVarDTO(1,"에그드랍"));
        voteVarDTOList.add(new voteVarDTO(1,"한솥"));
        voteVarDTOList.add(new voteVarDTO(1,"남경"));
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

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }

    private class RadioListCell extends ListCell<voteVarDTO> {
        @Override
        public void updateItem(voteVarDTO obj, boolean empty) {
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
