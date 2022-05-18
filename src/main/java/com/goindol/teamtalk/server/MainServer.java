package com.goindol.teamtalk.server;



import com.goindol.teamtalk.client.model.chatRoomListDTO;
import com.goindol.teamtalk.client.model.friendDTO;
import com.goindol.teamtalk.client.model.userDTO;
import com.goindol.teamtalk.client.service.userDAO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
    private ServerSocket serverSocket;
    private ArrayList<friendDTO> friendList;
    private ArrayList<chatRoomListDTO> chatRoomList;
    private ArrayList<String> allUserList;

    public MainServer(String userId, String userPassword, int chatRoom_id) {
        userDAO userDAO = com.goindol.teamtalk.client.service.userDAO.getInstance();
        userDTO userDTO = null;
        try {
            userDTO = userDAO.getUser(userId, userPassword);
            allUserList = new ArrayList<String>();
            serverSocket = new ServerSocket(9500);
            System.out.println("Server Stared...");

            while(true) {
                Socket socket = serverSocket.accept();
                userDAO.login(userId, userPassword, socket.getInetAddress().toString());
                UserThread userThread = new UserThread(socket, userDTO, userId, userPassword);
                userThread.start();
                allUserList.add(userDTO.getNickName());
                //여기에 모든 사용자한테 친구목록 새로고침하는 소스 부르기

            }

        } catch(IOException io) {
            io.printStackTrace();

        }

    }


}


class Document {
    Page[] p1;
}

class Page {

}