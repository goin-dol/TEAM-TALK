package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.voteVarDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class showVoteResultController implements Initializable {

    @FXML
    public BorderPane borderPane;

    @FXML
    public ListView voteResultList;
    private ToggleGroup group = new ToggleGroup();

    public void initialVoteList() {
        //TODO DB에서 해당 채팅방 투표의 투표 항목 불러오기
        ObservableList names = FXCollections.observableArrayList();
        List<voteVarDTO> voteVarDTOList = new ArrayList<>();
        voteVarDTOList.add(new voteVarDTO(1,"에그드랍"));
        voteVarDTOList.add(new voteVarDTO(1,"한솥"));
        voteVarDTOList.add(new voteVarDTO(1,"남경"));
        names.addAll(voteVarDTOList);


        voteResultList.setItems(names);
        voteResultList.setCellFactory(param -> new listCell());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialVoteList();

    }

    private class listCell extends ListCell<voteVarDTO> {
        @Override
        public void updateItem(voteVarDTO obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                Label label = new Label(obj.getContent());
                Button button = new Button("투표 인원");
                setGraphic(button);
                setText(obj.getContent());

            }
        }
    }
}
