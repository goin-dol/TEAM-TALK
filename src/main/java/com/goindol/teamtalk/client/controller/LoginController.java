package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.userDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;

public class LoginController{
    MainController mainController;
    Socket socket;
    com.goindol.teamtalk.client.model.userDTO userDTO;
    com.goindol.teamtalk.client.service.userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
    @FXML public Pane pane;
    @FXML public TextField Id;
    @FXML public TextField Password;

    public void showScene() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) Id.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(400);
            stage.setHeight(600);
            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            stage.setScene(this.Id.getScene());
        });
    }

    public void loginButtonAction() {
        String id = Id.getText();
        String password = Password.getText();

        //TODO : 디비랑 아이디 비번 비교

        if (userDAO.checkLogin(id, password)) {
            try {
                this.userDTO = userDAO.getUser(id, password);

                Stage stage = (Stage) Id.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
                Parent root = loader.load();
                mainController = loader.getController();
                mainController.setuserDTO(userDTO);
                mainController.showChatRoomList();

                stage.setScene(new Scene(root, 400, 600));
                stage.setTitle("Team Talk");
                stage.setOnCloseRequest(event -> {System.exit(0);});
                stage.setResizable(false);
                stage.show();



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

}
