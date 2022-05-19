package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
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

    public void goToBack(){
        try {
            Stage stage = (Stage) chatRoomContainer.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/MainView.fxml"));
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Chattting(){


    }

    public void initialChat() {
        //TODO : DB에서 해당 채팅방 채팅 불러오기
    }


    public void getChatRoomId(int id) {
        System.out.println("ChatRoom ID : " + id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialChat();
        noticeMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        noticeCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        voteMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        voteCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }
}
