package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class setChatRoomTitle implements Initializable {
    @FXML private Pane pane;
    @FXML private TextField chatRoomTitle;
    @FXML private Button complete;

    public void setChatRoomTitle(){
        String s = chatRoomTitle.getText();
        System.out.println(s);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatRoomTitle.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                chatRoomTitle.setText(keyEvent.getText());
            }
        });
        complete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setChatRoomTitle();
                try {
                    Stage stage = (Stage) pane.getScene().getWindow();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/ChatView.fxml"));
                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> {System.exit(0);});
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
