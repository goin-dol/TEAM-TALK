package com.goindol.teamtalk.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignupController {

    @FXML private TextField Id;
    @FXML private TextField Password;
    @FXML private TextField Nickname;

    public void signupButtonAction() {
        String id = Id.getText();
        String password = Password.getText();
        String nickname = Nickname.getText();
        return;
    }
}
