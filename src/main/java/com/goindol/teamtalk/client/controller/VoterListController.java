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

    public void showVoterList(){

        List<String> strings = new ArrayList<>();
        List<VoteResultDTO> voter_List = voteDAO.ShowVoteList(voteId);
        for(VoteResultDTO voters : voter_List) {
            strings.add(voters.getNickName());
        }
        ObservableList<String> chatRoomObservableUserList = FXCollections.observableList(strings);

        voterList.setItems(chatRoomObservableUserList);
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showVoterList();
    }
}
