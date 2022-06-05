package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.service.ChatLogDAO;
import com.goindol.teamtalk.client.service.ChatRoomListDAO;
import com.goindol.teamtalk.client.service.VoteDAO;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
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
    String IP = "192.168.0.2";

    int port = 9500;
    ChatRoomListDAO chatRoomListDAO = ChatRoomListDAO.getInstance();
    ChatLogDAO chatLogDAO = ChatLogDAO.getInstance();
    VoteDAO voteDAO = VoteDAO.getInstance();
    public MainController mainController;
    @FXML private BorderPane chatRoomContainer;
    @FXML private Label chatRoomTitle;
    @FXML private Button noticeCheck;
    @FXML private Button noticeMake;
    @FXML private Button voteCheck;
    @FXML private Button voteMake;
    @FXML private TextArea chat;
    @FXML private TextField userInput;
    @FXML private ImageView sendButton;
    @FXML private ImageView chatRoomInfo;
    @FXML private ImageView goBackButton;

    public int chatid;
    public UserDTO userDTO;
    DropShadow dropShadow = new DropShadow();

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
            main.setUserDTO(userDTO);
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
        goBackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goToBack();
            }
        });
        noticeMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/MakeNoticeView.fxml"));
                    Parent root = (Parent) loader.load();
                    MakeNoticeController makeNoticeController = (MakeNoticeController) loader.getController();
                    makeNoticeController.setChatRoomId(chatid);
                    makeNoticeController.setUserDTO(userDTO);
                    makeNoticeController.setMainController(mainController);
                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setX(curStage.getX()+400);
                    stage.setY(curStage.getY());
                    stage.setResizable(false);
                    stage.show();

                    Stage alert = new Stage();
                    FXMLLoader alertLoader = new FXMLLoader();
                    alertLoader.setLocation(HelloApplication.class.getResource("views/AlertView.fxml"));
                    Parent alertRoot = (Parent) alertLoader.load();
                    AlertController alertController = (AlertController) alertLoader.getController();
                    alertController.setAlert("채팅방1",true);

                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    double width = screenSize.getWidth();
                    double height = screenSize.getHeight();
//                    alert.initStyle(StageStyle.UNDECORATED);
                    alert.setScene(new Scene(alertRoot,400,85));
                    alert.setResizable(false);
                    alert.setX(width);
                    alert.setY(height);
                    alert.show();


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

        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                send(chatid + "/" + userDTO.getNickName() + " : " + userInput.getText() + "\n");
                userInput.setText("");
                userInput.requestFocus();
            }
        });


        noticeCheck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Stage stage = new Stage();
                    Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/ShowNoticeView.fxml"));
                    Parent root = (Parent) loader.load();
                    ShowNoticeController showNoticeController = (ShowNoticeController) loader.getController();
                    showNoticeController.setChatRoomId(chatid);
                    showNoticeController.setUserDTO(userDTO);
                    showNoticeController.showNoticeContent();
                    showNoticeController.showReadUser();

                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setX(curStage.getX()+400);
                    stage.setY(curStage.getY());
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
                    Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/MakeVoteView.fxml"));
                    Parent root = (Parent) loader.load();
                    MakeVoteController makeVoteController = (MakeVoteController) loader.getController();
                    makeVoteController.setChatRoomId(chatid);
                    makeVoteController.setUserDTO(userDTO);
                    makeVoteController.setMainController(mainController);
                    stage.setScene(new Scene(root, 400, 600));
                    stage.setTitle("Team Talk");
                    stage.setX(curStage.getX()+400);
                    stage.setY(curStage.getY());
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
                int voteid = voteDAO.getVoteId(chatid);
                boolean ifAlreadyVote = voteDAO.checkOverLap(voteid, userDTO.getNickName());
                if(ifAlreadyVote) {
                    try {
                        Stage stage = new Stage();
                        Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("views/ShowVoteResultView.fxml"));
                        Parent root = (Parent) loader.load();
                        ShowVoteResultController showVoteResultController = (ShowVoteResultController) loader.getController();
                        showVoteResultController.setChatRoomId(chatid);
                        showVoteResultController.setUserDTO(userDTO);
                        showVoteResultController.setVoteId(voteid);
                        showVoteResultController.initialVoteList();
                        stage.setScene(new Scene(root, 400, 600));
                        stage.setTitle("Team Talk");
                        stage.setX(curStage.getX()+400);
                        stage.setY(curStage.getY());
                        stage.setOnCloseRequest(event -> stage.close());
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Stage stage = new Stage();
                        Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(HelloApplication.class.getResource("views/DoVoteView.fxml"));
                        Parent root = (Parent) loader.load();
                        DoVoteController doVoteController = (DoVoteController) loader.getController();
                        doVoteController.setChatRoomId(chatid);
                        doVoteController.setUserDTO(userDTO);
                        doVoteController.initialVoteList();

                        stage.setScene(new Scene(root, 400, 600));
                        stage.setTitle("Team Talk");
                        stage.setX(curStage.getX()+400);
                        stage.setY(curStage.getY());
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
                    Stage curStage = (Stage) chatRoomContainer.getScene().getWindow();
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(HelloApplication.class.getResource("views/ChatRoomInfoView.fxml"));
                    Parent root = (Parent) loader.load();
                    ChatRoomInfoController chatRoomInfoController = (ChatRoomInfoController) loader.getController();

                    chatRoomInfoController.setChatRoomId(chatid);
                    chatRoomInfoController.setUserDTO(userDTO);
                    chatRoomInfoController.setMainController(mainController);
                    chatRoomInfoController.showChatRoomUserList();
                    stage.setScene(new Scene(root, 250, 400));
                    stage.setTitle("Team Talk");
                    stage.setOnCloseRequest(event -> stage.close());
                    stage.setResizable(false);
                    stage.setX(curStage.getX()+400);
                    stage.setY(curStage.getY());
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        startClient(IP, port);



    }

    public void setuserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
    public void setChatRoomId(int id) {
        this.chatid = id;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
