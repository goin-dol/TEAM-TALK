package com.goindol.teamtalk;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HelloController {
    @FXML private ListView friendList;

    @FXML
    public TextField Id;
    @FXML
    public TextField Password;

    private Scene scene;

    public static String id, password;

    public void login() {
        id = Id.getText();
        password = Password.getText();
        changeWindow();

    }
    public void changeWindow(){
        try {
            Stage stage = (Stage) Id.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/MainView.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createChat(MouseEvent mouseEvent) {

    }
}