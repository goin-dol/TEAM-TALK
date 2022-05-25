package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.model.voteVarDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.scene.layout.Priority;
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

    public int chatid;
    public userDTO userDTO;


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

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }

    private class listCell extends ListCell<voteVarDTO> {
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
        public void updateItem(voteVarDTO obj, boolean empty) {
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
