package com.goindol.teamtalk.client.model;


public class voteDTO {

    private int chatRoom_id;
    private String title;
    private boolean isAnonoymous;
    private boolean isOverLap;

    public int getChatRoom_id() {
        return chatRoom_id;
    }

    public void setChatRoom_id(int chatRoom_id) {
        this.chatRoom_id = chatRoom_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnonoymous() {
        return isAnonoymous;
    }

    public void setAnonoymous(boolean anonoymous) {
        isAnonoymous = anonoymous;
    }

    public boolean isOverLap() {
        return isOverLap;
    }

    public void setOverLap(boolean overLap) {
        isOverLap = overLap;
    }
}