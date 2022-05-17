package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupController {

    @FXML private TextField Id;
    @FXML private TextField Password;
    @FXML private TextField Nickname;

    public void registerUserAction() {
        String id = Id.getText();
        String password = Password.getText();
        String nickname = Nickname.getText();
        boolean isSignupSuccess = true;

        if (isSignupSuccess) {
            try {
                Stage stage = (Stage) Id.getScene().getWindow();
                Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/InitialView.fxml"));
                stage.setScene(new Scene(root, 400, 600));
                stage.setTitle("Team Talk");
                stage.setOnCloseRequest(event -> {System.exit(0);});
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
