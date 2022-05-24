package com.goindol.teamtalk.client.model;

public class voteResultDTO {
    private int vote_id;
    private int voteVar_id;
    private String content;
    private String nickName;

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public int getVoteVar_id() {
        return voteVar_id;
    }

    public void setVoteVar_id(int voteVar_id) {
        this.voteVar_id = voteVar_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}