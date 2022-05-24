package com.goindol.teamtalk.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class VoteController implements Initializable {

    @FXML public ListView voteList;
    @FXML public Button voteButton;
    public static final ObservableList names = FXCollections.observableArrayList();
    private ToggleGroup group = new ToggleGroup();

    public void saveVoteResult() {
        System.out.println(group.getUserData());
    }

    public void initialVoteList() {
        names.addAll(
                "Adam", "Alex", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny",
                "Lynne", "Myrtle", "Rose", "Rudolph",
                "Tony", "Trudy", "Williams", "Zach"
        );
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
            }
        });
    }

    private class RadioListCell extends ListCell<String> {
        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                RadioButton radioButton = new RadioButton(obj);
                radioButton.setToggleGroup(group);
                setGraphic(radioButton);
            }
        }
    }}
