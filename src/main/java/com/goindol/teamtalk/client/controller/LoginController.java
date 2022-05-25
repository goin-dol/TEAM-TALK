package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.userDAO;
import com.goindol.teamtalk.server.MainServer;
import com.goindol.teamtalk.server.UserThread;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;

public class LoginController{
    public static LoginController instance = new LoginController();
    BufferedReader br;
    PrintWriter pw;
    userDTO userDTO;
    userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    Socket socket;
    MainController main;
    public static MainServer mainServer;
    @FXML public Pane pane;
    @FXML public TextField Id;
    @FXML public TextField Password;

    public LoginController() {

    }

    public LoginController LoginController() {
        if(instance == null)
            instance = new LoginController();
        return instance;
    }

    public void loginButtonAction() throws IOException {
        String id = Id.getText();
        String password = Password.getText();

        System.out.println("user info : " + id);
        System.out.println("user info : " + password);
        //TODO : 디비랑 아이디 비번 비교
        if (userDAO.checkLogin(id, password)) {
            try {
                this.userDTO = userDAO.getUser(id, password);
                mainServer = MainServer.getInstance();

                Stage stage = (Stage) Id.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
                Parent root = loader.load();
                MainController main = loader.getController();
                //mainServer.setMainController(main);
                //main.setSocket(socket);
                main.setuserDTO(userDTO);
                main.showChatRoomList();

                stage.setScene(new Scene(root, 400, 600));
                stage.setTitle("Team Talk");
                stage.setOnCloseRequest(event -> {System.exit(0);});
                stage.setResizable(false);
                stage.show();

                //socket = new Socket("192.168.0.52", 9500);
                //main.setSocket(socket);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("Log In Error");
            alert.setContentText("Wrong Id or Password");
            alert.show();


        }
    }

    public void signupButtonAction() {
        try {
            Stage stage = (Stage) Id.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/SignUpView.fxml"));
            stage.setScene(new Scene(root, 400, 600));
            stage.setTitle("Team Talk");
            stage.setOnCloseRequest(event -> {System.exit(0);});
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public userDTO getUserDTO() {
        return userDTO;
    }

}
