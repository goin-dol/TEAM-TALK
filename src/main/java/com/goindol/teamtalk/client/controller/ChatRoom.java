package com.goindol.teamtalk.client.controller;

public class ChatRoom {
    public int id;
    public String title;

    public ChatRoom(int _id, String _title) {
        id  = _id;
        title =  _title;


    }

    public String getTitle(){
        return this.title;
    }
}
