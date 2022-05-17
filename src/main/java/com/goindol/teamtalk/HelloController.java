package com.goindol.teamtalk;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import java.net.URL;
import java.io.IOException;
import java.util.*;

public class HelloController {

//    @FXML public TextField Id;
//    @FXML public TextField Password;
//    public static String id, password;
//
//    public void login() {
//        id = Id.getText();
//        password = Password.getText();
//        changeWindow();
//
//    }
//    public void changeWindow(){
//        try {
//            Stage stage = (Stage) Id.getScene().getWindow();
//            Parent root = FXMLLoader.load(HelloApplication.class.getResource("views/MainView.fxml"));
//            stage.setScene(new Scene(root, 600, 400));
//            stage.setTitle("Team Talk");
//            stage.setOnCloseRequest(event -> {System.exit(0);});
//            stage.setResizable(false);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



        @FXML private TabPane tabContainer;
        @FXML private Tab chatTab;
        @FXML private AnchorPane userProfileContainer;
        @FXML private Tab friendTab;
        @FXML private AnchorPane settingsContainer;
        @FXML private Tab logoutTab;

        private double tabWidth = 90.0;
        public static int lastSelectedTabIndex = 0;
        /// Life cycle
        @FXML
        public void initialize() {
            configureView();
        }
        /// Private
        private void configureView() {

            tabContainer.setRotateGraphic(true);
            EventHandler<Event> replaceBackgroundColorHandler = event -> {
                lastSelectedTabIndex = tabContainer.getSelectionModel().getSelectedIndex();
                Tab currentTab = (Tab) event.getTarget();
                if (currentTab.isSelected()) {
                    currentTab.setStyle("-fx-background-color: -fx-focus-color;");
                } else {
                    currentTab.setStyle("-fx-background-color: -fx-accent;");
                }
            };
            EventHandler<Event> logoutHandler = event -> {
                Tab currentTab = (Tab) event.getTarget();
                if (currentTab.isSelected()) {
                    tabContainer.getSelectionModel().select(lastSelectedTabIndex);
                    // TODO: logout action
                    // good place to show Dialog window with Yes / No question
                    System.out.println("Logging out!");
                }
            };
            configureTab(chatTab, "Chat", userProfileContainer, getClass().getResource("views/chatList.fxml"), replaceBackgroundColorHandler);
            configureTab(friendTab, "Friend", settingsContainer, getClass().getResource("views/friendList.fxml"), replaceBackgroundColorHandler);
//            configureTab(logoutTab, "Logout", null, null, logoutHandler);
            chatTab.setStyle("-fx-background-color: -fx-focus-color;");
        }

        private void configureTab(Tab tab, String title, AnchorPane containerPane, URL resourceURL, EventHandler<Event> onSelectionChangedEvent) {
            Label label = new Label(title);
            label.setMaxWidth(tabWidth);
            label.setPadding(new Insets(5, 0, 0, 0));
            label.setStyle("-fx-text-fill: black; -fx-font-size: 8pt; -fx-font-weight: normal;");
            label.setTextAlignment(TextAlignment.CENTER);
            BorderPane tabPane = new BorderPane();
            tabPane.setRotate(90.0);
            tabPane.setMaxWidth(tabWidth);
            tabPane.setBottom(label);
            tab.setText("");
            tab.setGraphic(tabPane);
            tab.setOnSelectionChanged(onSelectionChangedEvent);

            if (containerPane != null && resourceURL != null) {
                try {
                    Parent contentView = FXMLLoader.load(resourceURL);
                    containerPane.getChildren().add(contentView);
                    AnchorPane.setTopAnchor(contentView, 0.0);
                    AnchorPane.setBottomAnchor(contentView, 0.0);
                    AnchorPane.setRightAnchor(contentView, 0.0);
                    AnchorPane.setLeftAnchor(contentView, 0.0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
