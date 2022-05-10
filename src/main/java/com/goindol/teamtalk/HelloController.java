package com.goindol.teamtalk;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML private ListView friendList;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void setFriendList() {
        List<String> strings = new ArrayList<>();
        strings.add("taewan");
        strings.add("gangsan");
        ObservableList<String> friend = FXCollections.observableList(strings);

        friendList.setItems(friend);
    }

    public void createChat(MouseEvent mouseEvent) {

    }
}