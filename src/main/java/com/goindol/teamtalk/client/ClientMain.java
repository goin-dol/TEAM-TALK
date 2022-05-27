package com.goindol.teamtalk.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.Socket;

public class ClientMain extends Application{

    private static Stage primaryStage;
    Socket socket;

    public void startClient(String IP, int port) {

    }

    public void stopClient() {
    }

    public void receive() {
    }

    public void send(String message) {

    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/InitialView.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
    }

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        launch(ClientMain.class, args);



    }


}
