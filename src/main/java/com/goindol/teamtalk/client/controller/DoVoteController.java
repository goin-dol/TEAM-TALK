package com.goindol.teamtalk.client.controller;

import com.goindol.teamtalk.client.model.UserDTO;
import com.goindol.teamtalk.client.model.VoteVarDTO;
import com.goindol.teamtalk.client.service.VoteDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DoVoteController implements Initializable {

    @FXML public BorderPane borderPane;
    @FXML public ListView voteList;
    @FXML public Button voteButton;
    private ToggleGroup group = new ToggleGroup();
    public VoteDAO voteDAO = VoteDAO.getInstance();

    public int chatid;
    public int voteId;
    public int voteVarId;
    List<String> tempList = new ArrayList<String>();
    public UserDTO userDTO;

    public void saveVoteResult() {
        if(tempList.size() >= 2) {
            if(voteDAO.checkVote(voteId)) {
                for(String temp : tempList) {
                    voteDAO.choiceVote(voteId, temp, userDTO.getNickName());
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("warning");
                alert.setHeaderText("OverLap vote");
                alert.setContentText("You Only One Vote");
                alert.show();
            }
        }else {
            for(String temp : tempList) {
                voteDAO.choiceVote(voteId, temp, userDTO.getNickName());
            }
        }


        //String temp = group.getSelectedToggle().toString();
        //String value = temp.substring(temp.indexOf("'")+1, temp.length()-1);
        //TODO temp

    }

    public void initialVoteList() {
        //TODO DB에서 해당 채팅방 투표의 투표 항목 불러오기
        voteId = voteDAO.getVoteId(chatid);
        ObservableList names = FXCollections.observableArrayList();
        List<VoteVarDTO> voteVarDTOList = voteDAO.ReadVoteList(voteId);
        if(voteVarDTOList != null) {
            for(VoteVarDTO voteVar : voteVarDTOList) {
                voteVarId = voteVar.getVoteVar_id();
                names.add(voteVar);
            }


            voteList.setItems(names);
            voteList.setCellFactory(param -> new RadioListCell());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        voteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saveVoteResult();
                Stage stage = (Stage) borderPane.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setChatRoomId(int chatid) {
        this.chatid = chatid;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    private class RadioListCell extends ListCell<VoteVarDTO> {
        @Override
        public void updateItem(VoteVarDTO obj, boolean empty) {
            setStyle("-fx-border-style: hidden hidden hidden hidden");
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                CheckBox checkBox = new CheckBox(obj.getContent());
                checkBox.setText(obj.getContent());
                checkBox.selectedProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        if(checkBox.isSelected()) {
                            tempList.add(obj.getContent());
                        }else {
                            tempList.remove(obj.getContent());
                        }

                    }
                });

                        setGraphic(checkBox);
                //RadioButton radioButton = new RadioButton(obj.getContent());
                //radioButton.setToggleGroup(group);
                //setGraphic(radioButton);
            }
        }
    }
}