package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
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
    public chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    public userDTO userDTO;
    @FXML private Pane pane;
    @FXML private TextField chatRoomTitle;
    @FXML private Button complete;

    public void setChatRoomTitle(){
        chatRoomListDAO.createChatRoom(chatRoomTitle.getText(), userDTO.getNickName());
        int chatId = chatRoomListDAO.getChatRoomId(chatRoomTitle.getText(), userDTO.getNickName());
        chatRoomListDAO.inviteChatRoom(chatId, userDTO.getNickName());
        chatRoomTitle.setText("");
    }
    public void goToBack(){
        try {
            Stage stage = (Stage) chatRoomTitle.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
            Parent root = loader.load();
            MainController main = loader.getController();
            main.setuserDTO(userDTO);
            main.showChatRoomList();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        complete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setChatRoomTitle();
                try {
                    Stage stage = (Stage) pane.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
                    Parent root = loader.load();
                    MainController main = loader.getController();
                    main.setuserDTO(userDTO);
                    main.showChatRoomList();
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

    public void setUserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
}
