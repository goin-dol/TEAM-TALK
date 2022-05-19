package com.goindol.teamtalk.client.model;

public class friendDTO {

    private int f_id;
    private String nickName;
    private String friendNickName;
    private boolean friendStatus;

    public int getF_id() {
        return f_id;
    }
    public void setF_id(int f_id) {
        this.f_id = f_id;
    }
    public String getNickName() {return nickName;}
    public void setNickName(String nickName) {this.nickName = nickName;}
    public String getFriendNickName() {
        return friendNickName;
    }
    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }
    public boolean isFriendStatus() {return friendStatus;}
    public void setFriendStatus(boolean friendStatus) {this.friendStatus = friendStatus;}

}
