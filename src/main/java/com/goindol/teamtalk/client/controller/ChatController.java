package com.goindol.teamtalk.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ChatController implements Initializable {
    @FXML public BorderPane chatRoomContainer;
    @FXML public Label chatRoomTitle;
    @FXML public Label chatRoomInfo;
    @FXML public Label noticeCheck;
    @FXML public Label noticeMake;
    @FXML public Label voteCheck;
    @FXML public Label voteMake;
    @FXML public ListView chat;
    @FXML public TextArea userInput;
    @FXML public Label send;

    public void Chattting(){

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> strings = new ArrayList<>();
        strings.add("hi");
        strings.add("hello");
        ObservableList<String> li = FXCollections.observableList(strings);
        chat.setItems(li);
    }
}
