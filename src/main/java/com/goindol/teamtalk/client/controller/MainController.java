package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.*;
import com.goindol.teamtalk.client.service.*;
import javafx.application.Platform;
import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.model.FriendDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML public Tab logoutTab;
    @FXML public ListView chatRoomList;
    @FXML public ListView friendList;
    @FXML public TextField searchFriend;
    @FXML public Button addFriendButton;
    @FXML public ImageView makeChatRoomButton;
    @FXML public ImageView chatRoomListTabImage;
    @FXML public ImageView friendListTabImage;
    @FXML public ImageView logoutTabImage;
    DropShadow dropShadow = new DropShadow();

    chatRoomListDAO chatRoomListDAO = com.goindol.teamtalk.client.service.chatRoomListDAO.getInstance();
    friendDAO friendDAO = com.goindol.teamtalk.client.service.friendDAO.getInstance();
    userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    public UserDTO userDTO;

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
        List<FriendDTO> strings = new ArrayList<>();

        if(userDTO != null) {
            ArrayList<FriendDTO> friends = userDAO.getFriendList(userDTO.getNickName());
            if(friends != null) {
                for(int i = 0; i < friends.size(); i++) {
                    strings.add(friends.get(i));
                }
            }
        }
        ObservableList<FriendDTO> friendObervableList = FXCollections.observableList(strings);
        Platform.runLater(()->{
            friendList.setItems(friendObervableList);
        });
    }

    public void showChatRoomList(){
        List<ChatRoomListDTO> strings = new ArrayList<>();
        if(userDTO != null) {
            ArrayList<ChatRoomListDTO> chatRoom = chatRoomListDAO.getChatRoomName(userDTO.getNickName());
            if(chatRoom != null) {
                for(int i = 0; i < chatRoom.size(); i++) {
                    strings.add(chatRoom.get(i));
                }
            }
        }

        ObservableList<ChatRoomListDTO> chatRoomObservableList = FXCollections.observableArrayList();

        chatRoomObservableList.addAll(strings);

        Platform.runLater(()->{
            chatRoomList.setItems(chatRoomObservableList);
        });
    }

    public void openChatRoom(){
        ChatRoomListDTO cr = (ChatRoomListDTO) chatRoomList.getSelectionModel().getSelectedItem();
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
            chatController.setMainController(this);
            chatController.setChatRoomTitle();
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
        //TODO : DB에 채팅방 저장

        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MakeChatRoomView.fxml"));
            Parent root = (Parent) loader.load();
            MakeChatRoomController chatRoomTitleController = loader.getController();
            chatRoomTitleController.setUserDTO(userDTO);
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

        send("login/roomId/value");
    }

    public void logOut(){
        userDAO.logout(userDTO.getUserId(), userDTO.getNickName());
        send("login/roomId/value");

        try {
            Stage stage = (Stage) stackPane.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/InitialView.fxml"));
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stopClient();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                logOut();
            }
        });


//        makeChatRoomButton.setOnMouseEntered(mouseEvent -> makeChatRoomButton.setEffect(dropShadow));
//        makeChatRoomButton.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                makeChatRoomButton.setEffect(null);
//            }
//        });


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
    private class colorListCell extends ListCell<String> {
        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                Label label = new Label(obj);
                //#TODO 친구 온라인일시 Color.GREEN, 오프라인이면 Color.BLACK
                label.setTextFill(Color.GREEN);
                setGraphic(label);
            }
        }
    }
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}