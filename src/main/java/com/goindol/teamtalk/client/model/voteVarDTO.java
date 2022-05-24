package com.goindol.teamtalk.client.model;

public class voteVarDTO {
    int id;
    int vote_id;
    String content;

    public voteVarDTO(int vote_id, String content) {
        this.vote_id = vote_id;
        this.content = content;
    }

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}