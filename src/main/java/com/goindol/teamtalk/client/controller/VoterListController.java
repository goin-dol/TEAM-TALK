package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.VoteResultDTO;
import com.goindol.teamtalk.client.service.VoteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VoterListController implements Initializable {
    @FXML private Label voteVar;
    @FXML private ListView voterList;

    public VoteDAO voteDAO = VoteDAO.getInstance();
    private int voteId;
    private String voteVarContent;

    public void showVoterList(){
        voteVar.setText(voteVarContent);
        List<String> strings = new ArrayList<>();
        List<String> voter_List = voteDAO.showResultByContent(voteId, voteVarContent);
        for(String voters : voter_List) {
            strings.add(voters);
        }
        ObservableList<String> chatRoomObservableUserList = FXCollections.observableList(strings);

        voterList.setItems(chatRoomObservableUserList);
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }
    public void setVoteVarContent(String lastItem) { this.voteVarContent = lastItem; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
