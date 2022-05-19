package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.chatRoomUserListDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class chatRoomUserListDAO {
    private static chatRoomUserListDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private chatRoomUserListDAO() {}

    public static chatRoomUserListDAO getInstance() {
        if(instance == null)
            instance = new chatRoomUserListDAO();
        return instance;
    }

    public ArrayList<chatRoomUserListDTO> getChatRoomUser(int chatRoom_id) {
        ArrayList<chatRoomUserListDTO> chatRoomUserList = null;
        String query = "SELECT * FROM DB_ppick.chatRoomUserList where chatRoom_id = ?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                chatRoomUserList = new ArrayList<chatRoomUserListDTO>();
                do {
                    chatRoomUserListDTO chatRoomUserListDTO = new chatRoomUserListDTO();
                    chatRoomUserListDTO.setChatRoom_id(rs.getInt("chatRoom_id"));
                    chatRoomUserListDTO.setNickName(rs.getString("nickName"));
                    chatRoomUserListDTO.setNoticeRead(rs.getBoolean("isNoticeRead"));

                    chatRoomUserList.add(chatRoomUserListDTO);

                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return chatRoomUserList;
    }










}
