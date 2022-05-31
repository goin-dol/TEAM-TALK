package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.HelloApplication;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.voteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class makeVoteController implements Initializable {

    @FXML public BorderPane borderPane;
    @FXML public TextField voteTitle;
    @FXML public TextField voteVar;
    @FXML public ListView voteVarList;
    @FXML public CheckBox isAnonoymous;
    @FXML public CheckBox isOverLap;
    @FXML public Button addVoteVarbutton;
    @FXML public Button addVoteButton;

    private static voteDAO voteDAO;

    public ObservableList voteVarItems = FXCollections.observableArrayList();
    public List<String> _voteVarList = new ArrayList<>();

    public int chatid;
    public userDTO userDTO;

    public void addVoteVar() {
        _voteVarList.add(voteVar.getText());
        voteVarItems.add(voteVar.getText());
        voteVarList.setItems(voteVarItems);
        voteVar.clear();
    }

    public void addVote() {
        System.out.println(chatid);
//        voteDAO.creatVote(chatRoom_id,title,isAnonoymous,isOverLap);
//        int vote_id=voteDAO.Read_Vote_id(chatRoom_id);
//        for (String content : _voteVarList) {
//        voteDAO.createVoteVar(content,vote_id);
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voteTitle.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                voteTitle.setText(keyEvent.getText());
            }
        });

        voteVar.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                voteVar.setText(keyEvent.getText());
            }
        });

        voteVar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    addVoteVar();
                }
            }
        });

        addVoteVarbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addVoteVar();
            }
        });

        addVoteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                addVote();
            }
        });

    }

    public void getChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setuserDTO(userDTO userDTO) {
        this.userDTO = userDTO;
    }
}
