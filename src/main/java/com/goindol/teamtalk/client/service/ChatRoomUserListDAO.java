package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatRoomUserListDAO {
    private static ChatRoomUserListDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private ChatRoomUserListDAO() {}

    public static ChatRoomUserListDAO getInstance() {
        if(instance == null)
            instance = new ChatRoomUserListDAO();
        return instance;
    }

    public ArrayList<String> getChatRoomUser(int chatRoom_id) {
        ArrayList<String> nickName = null;
        String query = "SELECT nickName FROM DB_ppick.chatRoomUserList where chatRoom_id = ?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                nickName = new ArrayList<String>();
                do {
                    nickName.add(rs.getString("nickName"));

                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return nickName;
    }

    public void existRoom(int chatRoomId, String nickName) {
        String query = "DELETE FROM `DB_ppick`.`chatRoomUserList` " +
                "WHERE chatRoom_id = ? and nickName = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoomId);
            pstmt.setString(2, nickName);
            pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }










}