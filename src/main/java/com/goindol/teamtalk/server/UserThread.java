package com.goindol.teamtalk.server;


import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.chatRoomUserListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.chatLogDAO;
import com.goindol.teamtalk.client.service.chatRoomUserListDAO;
import com.goindol.teamtalk.client.service.userDAO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread{

    private BufferedReader br;
    private PrintWriter pw;
    private com.goindol.teamtalk.client.service.userDAO userDAO;
    com.goindol.teamtalk.client.service.chatRoomListDAO chatRoomListDAO;
    com.goindol.teamtalk.client.service.chatRoomUserListDAO chatRoomUserListDAO;
    com.goindol.teamtalk.client.service.chatLogDAO chatLogDAO;
    private Socket socket;
    private userDTO user;
    private ArrayList<friendDTO> friendList;
    private ArrayList<chatRoomListDTO> chatRoomList;
    private ArrayList<chatRoomUserListDTO> currentRoomUserList;
    private int chatRoom_id;

    public UserThread(Socket socket, userDTO userDTO, String userId, String userPassword) throws IOException {
        this.user = userDTO;
        this.currentRoomUserList = new ArrayList<chatRoomUserListDTO>();
        this.socket = socket;
        this.friendList = userDAO.getFriendList(user.getNickName());
        this.chatRoomList = chatRoomListDAO.getChatRoomName(user.getNickName());
        chatRoomListDAO = chatRoomListDAO.getInstance();
        chatRoomUserListDAO = chatRoomUserListDAO.getInstance();
        chatLogDAO = chatLogDAO.getInstance();
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }


    @Override
    public void run() {




    }

    public void entryRoom(int chatRoom_id) {
        this.chatRoom_id = chatRoom_id;
        String nickName = user.getNickName();

        for(int i = 0; i < chatRoomList.size(); i++) {
            if(chatRoomList.get(i).getChatRoom_id() == chatRoom_id) {
                currentRoomUserList = chatRoomUserListDAO.getChatRoomUser(chatRoom_id);
            }
        }
    }

    public void sendMessage(String content) {
        for(int i = 0; i < currentRoomUserList.size(); i++) {
            currentRoomUserList.get(i).getNickName(); //이 멤버에 대해 내용 전달
            //채팅 전달은 gui에 따라 다름.
        }
        chatLogDAO.writeLog(user.getNickName(), chatRoom_id, content);

        //https://github.com/jungsangsu/Java_CooProject/blob/master/src/Action/MainServer.java

    }


}
