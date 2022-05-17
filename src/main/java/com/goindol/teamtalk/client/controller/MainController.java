package com.goindol.teamtalk.client.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML public StackPane stackPane;
    @FXML public AnchorPane chatAnchor;
    @FXML public AnchorPane friendAnchor;
    @FXML public TabPane tabContainer;
    @FXML public Tab chatTab;
    @FXML public Tab friendTab;
    @FXML public ListView chatList;
    @FXML public ListView friendList;

    public void showFriendList(){
        List<String> strings = new ArrayList<>();
        strings.add("ta");
        strings.add("gs");
        ObservableList<String> fr = FXCollections.observableList(strings);
        friendList.setItems(fr);
    }


}
