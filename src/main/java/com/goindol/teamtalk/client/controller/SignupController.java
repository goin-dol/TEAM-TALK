package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

        //TODO : 중복 회원가입 여부 확인

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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("warning");
            alert.setHeaderText("Sign Up Error");
            alert.setContentText("Already");
            alert.show();
        }
    }
}
