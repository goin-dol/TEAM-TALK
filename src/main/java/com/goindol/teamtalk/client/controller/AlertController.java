package com.goindol.teamtalk.client.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public final class AlertController implements Initializable {

    @FXML
    private Label lblTitle, lblMessage, lblClose;
    @FXML
    private AnchorPane rootNode;


    public void setAlert(String title, boolean type) {
        lblTitle.setText(title);
        if(type) {
            lblMessage.setText("새로운 공지가 생성되었습니다.");
        } else {
            lblMessage.setText("새로운 투표가 생성되었습니다.");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) rootNode.getScene().getWindow();
                stage.close();
            }
        });
    }
}

