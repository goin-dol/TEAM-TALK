package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.service.UserDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML public Pane pane;
    @FXML public TextField Id;
    @FXML public PasswordField Password;
    @FXML public Button loginButton;
    @FXML public Button signupButton;
    DropShadow dropShadow = new DropShadow();

    Socket socket;
    public UserDTO userDTO;
    public UserDAO userDAO = UserDAO.getInstance();

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

        if (userDAO.login(id, password)) {
            try {
                ;
                this.userDTO = userDAO.getUser(id, password);

                Stage stage = (Stage) Id.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
                Parent root = loader.load();
                MainController main = loader.getController();
                main.setUserDTO(userDTO);
                main.showChatRoomList();
                main.send("login/roomId/value");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signupButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                signupButton.setEffect(dropShadow);
            }
        });
        signupButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                signupButton.setEffect(null);
            }
        });

        loginButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginButton.setEffect(dropShadow);
            }
        });
        loginButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginButton.setEffect(null);
            }
        });
    }
}
