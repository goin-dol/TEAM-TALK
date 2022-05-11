package com.goindol.teamtalk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import user.userDAO;

import java.io.IOException;
import java.util.*;

public class HelloController {
    userDAO userdao = userDAO.getInstance();
    UserController userController;

    @FXML
    private Button login;

    @FXML
    private Button signupPage;

    @FXML
    private Button signup;

    @FXML TextField id;
    @FXML TextField password;
    @FXML TextField nickName;

    public void login() {
        if(id.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("로그인 실패");
            alert.setHeaderText("로그인 실패");
            alert.setContentText("아이디를 입력해주세요.");
            alert.showAndWait();
        }else if(password.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("로그인 실패");
            alert.setHeaderText("로그인 실패");
            alert.setContentText("비밀번호를 입력해주세요.");
            alert.showAndWait();
        }else {
            int result = userdao.login(id.getText(), password.getText(), "127.0.0.1");
            if (result == 1) {
                System.out.println("Login Success");
                gotoPage("Main");
            } else {
                System.out.println("Login Failed");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("로그인 실패");
                alert.setHeaderText("로그인 실패");
                alert.setContentText("아이디 또는 비밀번호가 맞지 않습니다.");
                alert.showAndWait();
            }
        }

    }

    public void signupPage() {
        gotoPage("SignUpView");
    }

    public void signup() {
        userdao.signUp(id.getText(), password.getText(), nickName.getText());
        gotoPage("InitialView");
    }

    @FXML
    private Label welcomeText;


    @FXML private ListView friendList;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private HelloController gotoPage(String url) {
        HelloController helloController = null;
        try {
            Stage stage = (Stage) id.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/" + url + ".fxml"));
            Scene scene = new Scene(root, 400, 600);
            stage.setScene(scene);
            stage.setTitle("TEAM TALK");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return helloController;
    }


    @FXML
    protected void setFriendList() {
        List<String> strings = new ArrayList<>();
        strings.add("taewan");
        strings.add("gangsan");
        ObservableList<String> friend = FXCollections.observableList(strings);

        friendList.setItems(friend);
    }

    public void createChat(MouseEvent mouseEvent) {

    }
}