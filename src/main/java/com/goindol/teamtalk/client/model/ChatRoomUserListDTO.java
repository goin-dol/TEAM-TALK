package com.goindol.teamtalk.client.model;

public class ChatRoomUserListDTO {
    private int chatRomUser_id;
    private int chatRoom_id;
    private String nickName;
    private int isNoticeRead;

    public int getChatRomUser_id() {
        return chatRomUser_id;
    }

    public void setChatRomUser_id(int chatRomUser_id) {
        this.chatRomUser_id = chatRomUser_id;
    }

    public int getChatRoom_id() {
        return chatRoom_id;
    }

    public void setChatRoom_id(int chatRoom_id) {
        this.chatRoom_id = chatRoom_id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int isNoticeRead() {
        return isNoticeRead;
    }

    public void setNoticeRead(int noticeRead) {
        isNoticeRead = noticeRead;
    }
}
