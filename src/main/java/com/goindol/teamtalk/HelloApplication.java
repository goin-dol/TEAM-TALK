package com.goindol.teamtalk;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class HelloApplication extends Application {

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
//        primaryStage = stage;
//        Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/InitialView.fxml"));
//        primaryStage.initStyle(StageStyle.DECORATED);
//        primaryStage.setTitle("Team Talk");
//        Scene scene = new Scene(root,400, 600);
//        scene.setRoot(root);
//        primaryStage.setResizable(false);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        primaryStage.setOnCloseRequest(e -> Platform.exit());
        Parent root = FXMLLoader.load(getClass().getResource("views/Main.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}