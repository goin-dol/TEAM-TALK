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
        FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("views/MainView.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load(), 600, 400);
        secondStage.setScene(scene2);
        secondStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}