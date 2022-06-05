package com.goindol.teamtalk.client.model;

public class ChatRoomListDTO {
    private int chatRoom_id;
    private String chatRoomName;
    private String nickName;
    private int isNoticeRead;

    public ChatRoomListDTO() {

    }
    public ChatRoomListDTO(int chatRoom_id, String chatRoomName) {
        this.chatRoom_id = chatRoom_id;
        this.chatRoomName = chatRoomName;
    }
    
    public int getChatRoom_id() {
        return chatRoom_id;
    }
    public void setChatRoom_id(int chatRoom_id) {
        this.chatRoom_id = chatRoom_id;
    }
    public String getChatRoomName() {
        return chatRoomName;
    }
    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
    public String getNickName() {return nickName;}
    public void setNickName(String nickName) {this.nickName = nickName;}
    public int isNoticeRead() {
        return isNoticeRead;
    }

    public void setNoticeRead(int noticeRead) {
        isNoticeRead = noticeRead;
    }
    public String toString() {
        if(isNoticeRead == 0 || isNoticeRead == 2)
            return chatRoomName;
        else
            return chatRoomName + "\t\t New";

    }
}
