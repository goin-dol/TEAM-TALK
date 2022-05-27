package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.ClientInfo;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.userDAO;
import javafx.application.Platform;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ChatController implements Initializable {
    PrintWriter out;
    BufferedReader in;
    int chatRoomNum;
    String nickName;
    userDTO userDTO;
    Socket socket;
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

    public void Connection() {


        try {
            socket = new Socket("192.168.0.16", 9500);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        }catch(IOException e) {
            System.out.println("Disconnected");
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("@@@@");
                while(true) {


                }
            }
        };
        thread.start();

    }



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
        ObservableList<String> friendListItems = chat.getItems();
        friendListItems.add(userInput.getText());
        chat.setItems(friendListItems);
        userInput.setText("");
        nickName = userDTO.getNickName();
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
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/makeNotice.fxml"));
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


        userInput.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                userInput.setText(keyEvent.getText());
            }
        });


        noticeCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/noticeCheck.fxml"));
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
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/makeVote.fxml"));
                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> {stage.close();});
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
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/doVoteView.fxml"));
                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> {stage.close();});
                    stage.setResizable(false);
                    stage.setX(0);
                    stage.setY(0);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        chatRoomInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/ChatRoomInfoView.fxml"));
                    stage.setScene(new Scene(root, 250, 400));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.setX(1100);
                    stage.setY(300);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Connection();

    }

    public void setuserDTO(userDTO userDTO) throws IOException {
        this.userDTO = userDTO;
    }
    public void setChatRoomNum(int chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }
    public String getNickName() {
        return userDTO.getNickName();
    }
}
