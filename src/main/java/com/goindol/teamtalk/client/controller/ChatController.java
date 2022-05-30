package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;
import com.goindol.teamtalk.client.service.chatRoomListDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ChatController implements Initializable {
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    //String IP = "192.168.219.106";

    int port = 9500;
    chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    chatLogDAO chatLogDAO = com.goindol.teamtalk.client.service.chatLogDAO.getInstance();
    @FXML private BorderPane chatRoomContainer;
    @FXML private Label chatRoomTitle;
    @FXML private Label chatRoomInfo;
    @FXML private Label noticeCheck;
    @FXML private Label noticeMake;
    @FXML private Label voteCheck;
    @FXML public Label voteMake;
    @FXML private TextArea chat;
    @FXML private TextField userInput;
    @FXML private Button send;

    public int chatid;
    public userDTO userDTO;

    public void startClient(String IP, int port) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, port);
                    send(Integer.toString(chatid) + "/" + userDTO.getNickName());
                    receive();
                } catch(Exception e) {
                    e.printStackTrace();
                    if(!socket.isClosed()) {
                        stopClient();
                        System.out.println("Server Failed");
                    }

                }
            }
        };
        thread.start();
    }

    public void stopClient() {
        try {
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receive() {
        while(true) {
            try {
                InputStream in = socket.getInputStream();
                System.out.println("receiving");
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                String message = new String(buffer, 0, length, "UTF-8");
                Platform.runLater(()->{
                    chat.appendText(message);
                });
            }catch(Exception e) {
                stopClient();
                break;
            }
        }
    }

    public void send(String message) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    System.out.println("message : " + message);

                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                }catch(Exception e) {
                    stopClient();
                }
            }
        };
        thread.start();
    }


    public void goToBack(){
        try {
            Stage stage = (Stage) chatRoomContainer.getScene().getWindow();
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

    public void Chattting(){


    }

    public void initialChat() {
        List<String> content = chatLogDAO.showChatLog(chatid);
        if(content != null) {
            for(String log : content) {
                chat.appendText(log);
            }
        }

    }

    public void setChatRoomTitle(){
        String title = chatRoomListDAO.getCurrentChatRoomName(chatid);
        chatRoomTitle.setText(title);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chat.setEditable(false);
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


        userInput.setOnAction(event-> {
            send(chatid + "/" + userDTO.getNickName() + " : " + userInput.getText() + "\n");
            userInput.setText("");
            userInput.requestFocus();
        });
        send.setOnAction(event->{
            send(chatid + "/" + userDTO.getNickName() + " : " + userInput.getText() + "\n");
            userInput.setText("");
            userInput.requestFocus();
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
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String ip_str = ia.toString();
            String IP = ip_str.substring(ip_str.indexOf("/") + 1);
            startClient(IP, port);
        }  catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
    public void setChatRoomId(int id) {
        this.chatid = id;
    }
}
