package com.goindol.teamtalk;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.userDAO;

import java.io.IOException;

public class UserController {
    userDAO userdao = userDAO.getInstance();

    @FXML
    private Button signup;

    @FXML
    TextField id;
    @FXML TextField password;
    @FXML TextField nickName;

    public void signUpPage(Stage stage) {
        signup.setOnAction((event) -> {
            System.out.println("Signup clicked");

            System.out.println("id = " + id.getText());
            System.out.println("password = " + password.getText());
            System.out.println("nickName = " + nickName.getText());
            signUp(id.getText(), password.getText(), nickName.getText());

            gotoPage("InitialView", stage);
        });
    }

    private HelloController gotoPage(String url, Stage stage) {
        HelloController helloController = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/" + url + ".fxml"));
            helloController = (HelloController) fxmlLoader.getController();
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return helloController;
    }

    public void signUp(String userId, String userPassword, String nickName) {
        userdao.signUp(userId,userPassword,nickName);
    }

    public void logOut(String userId) {
        userdao.logout(userId);
        System.out.println("logOut Success");
    }

}
