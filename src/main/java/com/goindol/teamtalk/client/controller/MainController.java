package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    //String IP = "192.168.219.106";

    int port = 9600;
    @FXML public StackPane stackPane;
    @FXML public AnchorPane chatAnchor;
    @FXML public AnchorPane friendAnchor;
    @FXML public TabPane tabContainer;
    @FXML public Tab chatTab;
    @FXML public Tab friendTab;
    @FXML public ListView chatRoomList;
    @FXML public ListView friendList;
    @FXML public TextField searchFriend;
    @FXML public Button addFriendButton;
    @FXML public Button makeChatRoomButton;
    chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    friendDAO friendDAO = com.goindol.teamtalk.client.service.friendDAO.getInstance();
    userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    public userDTO userDTO;

    public void startClient(String IP, int port) {

        Thread thread = new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, port);
                    send(userDTO.getNickName());
                    send("login/roomId/value");
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
                if(message.equals("login")) {
                    showFriendList();
                }else if(message.equals("chatRoom")) {
                    showChatRoomList();
                }
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

    public void showFriendList(){
        List<friendDTO> strings = new ArrayList<>();

        if(userDTO != null) {
            ArrayList<friendDTO> friends = userDAO.getFriendList(userDTO.getNickName());
            if(friends != null) {
                for(int i = 0; i < friends.size(); i++) {
                    strings.add(friends.get(i));
                }
            }
            strings.add(new friendDTO(""));
        }
        ObservableList<friendDTO> friendObervableList = FXCollections.observableList(strings);
        Platform.runLater(()->{
            friendList.setItems(friendObervableList);
        });
    }

    public void showChatRoomList(){
        List<chatRoomListDTO> strings = new ArrayList<>();
        if(userDTO != null) {
            ArrayList<chatRoomListDTO> chatRoom = chatRoomListDAO.getChatRoomName(userDTO.getNickName());
            if(chatRoom != null) {
                for(int i = 0; i < chatRoom.size(); i++) {
                    strings.add(chatRoom.get(i));
                }
            }
            strings.add(new chatRoomListDTO(0, ""));
        }

        ObservableList<chatRoomListDTO> chatRoomObservableList = FXCollections.observableArrayList();

        chatRoomObservableList.addAll(strings);

        Platform.runLater(()->{
            chatRoomList.setItems(chatRoomObservableList);
        });
    }

    public void openChatRoom(){
        chatRoomListDTO  cr = (chatRoomListDTO ) chatRoomList.getSelectionModel().getSelectedItem();
        if(cr==null)
            return;
        try {

            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("views/ChatView.fxml"));
            Parent root = (Parent) loader.load();
            ChatController chatController = loader.getController();
            chatController.setuserDTO(userDTO);
            chatController.setChatRoomId(cr.getChatRoom_id());
            chatController.setChatRoomTitle();
            chatController.setMainController(this);
            chatController.initialChat();
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> chatController.stopClient());
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeChatroom(){

        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("views/setChatRoomTitleView.fxml"));
            Parent root = (Parent) loader.load();
            setChatRoomTitle setChatRoomTitle = loader.getController();
            setChatRoomTitle.setUserDTO(userDTO);
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFriend(){


        int status = friendDAO.addFriend(userDTO.getNickName(), searchFriend.getText());
        if(status == 1) {
            searchFriend.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("Friend Add Error");
            alert.setContentText("Already friend");
            alert.show();
        }else if(status == 2){
            searchFriend.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("Friend Add Error");
            alert.setContentText("Wrong NickName");
            alert.show();
        }else {
            ObservableList<String> friendListItems = friendList.getItems();
            friendListItems.add(searchFriend.getText());
            friendList.setItems(friendListItems);
            searchFriend.setText("");
        }


    }

    public void logOut(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addFriendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addFriend();
            }
        });

        makeChatRoomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                makeChatroom();
            }
        });

        chatRoomList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openChatRoom();
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
}