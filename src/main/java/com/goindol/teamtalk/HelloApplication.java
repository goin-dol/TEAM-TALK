package com.goindol.teamtalk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/InitialView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);

        stage.setScene(scene);
        stage.show();

        Stage secondStage = new Stage();
        ss(secondStage);

    }

    public void ss(Stage stage) throws  IOException {
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("views/Main.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 600, 400);
        stage.setScene(scene2);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}