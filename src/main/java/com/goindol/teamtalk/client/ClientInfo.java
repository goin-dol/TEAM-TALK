package com.goindol.teamtalk.client;

import java.io.*;

//직렬화
public class ClientInfo implements Serializable {
    int chatRoomNum;
    String nickName;

    public ClientInfo(int chatRoomNum, String nickName) {
        this.chatRoomNum = chatRoomNum;
        this.nickName = nickName;
    }




}
