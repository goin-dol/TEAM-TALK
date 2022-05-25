package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
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
    @FXML private BorderPane chatRoomContainer;
    @FXML private Label chatRoomTitle;
    @FXML private Label chatRoomInfo;
    @FXML private Label noticeCheck;
    @FXML private Label noticeMake;
    @FXML private Label voteCheck;
    @FXML public Label voteMake;
    @FXML private ListView chat;
    @FXML private TextArea userInput;
    @FXML private Label send;

    public int chatid;
    public userDTO userDTO;

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

    public void setChatRoomTitle(){
        String s = "채팅방1";
        chatRoomTitle.setText(s);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialChat();
        setChatRoomTitle();
        noticeMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/makeNotice.fxml"));
                    Parent root = (Parent) loader.load();
                    makeNoticeController makeNoticeController = (makeNoticeController) loader.getController();
                    makeNoticeController.getChatRoomId(chatid);
                    makeNoticeController.setuserDTO(userDTO);

                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        noticeCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/noticeCheck.fxml"));
                    Parent root = (Parent) loader.load();
                    showNoticeController showNoticeController = (showNoticeController) loader.getController();
                    showNoticeController.getChatRoomId(chatid);
                    showNoticeController.setuserDTO(userDTO);

                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        voteMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/makeVote.fxml"));
                    Parent root = (Parent) loader.load();
                    makeVoteController makeVoteController = (makeVoteController) loader.getController();
                    makeVoteController.getChatRoomId(chatid);
                    makeVoteController.setuserDTO(userDTO);

                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        voteCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boolean ifAlreadyVote = false;
                if(ifAlreadyVote) {
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("views/showVoteResult.fxml"));
                        Parent root = (Parent) loader.load();
                        showVoteResultController showVoteResultController = (showVoteResultController) loader.getController();
                        showVoteResultController.getChatRoomId(chatid);
                        showVoteResultController.setuserDTO(userDTO);

                        stage.setScene(new Scene(root, 400, 600));
                        stage.setTitle("Team Talk");
                        stage.setOnCloseRequest(event -> stage.close());
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("views/doVoteView.fxml"));
                        Parent root = (Parent) loader.load();
                        doVoteController doVoteController = (doVoteController) loader.getController();
                        doVoteController.getChatRoomId(chatid);
                        doVoteController.setuserDTO(userDTO);

                        stage.setScene(new Scene(root, 400, 600));
                        stage.setTitle("Team Talk");
                        stage.setOnCloseRequest(event -> stage.close());
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        chatRoomInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/ChatRoomInfoView.fxml"));
                    Parent root = (Parent) loader.load();
                    ChatRoomInfoController chatRoomInfoController = (ChatRoomInfoController) loader.getController();
                    chatRoomInfoController.getChatRoomId(chatid);
                    chatRoomInfoController.setuserDTO(userDTO);

                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
    public void getChatRoomId(int id) {
        this.chatid = id;
    }
}
