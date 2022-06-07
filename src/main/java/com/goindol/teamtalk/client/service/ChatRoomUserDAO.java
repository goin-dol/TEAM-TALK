package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatRoomUserDAO {
    private static ChatRoomUserDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private ChatRoomUserDAO() {}

    public static ChatRoomUserDAO getInstance() {
        if(instance == null)
            instance = new ChatRoomUserDAO();
        return instance;
    }

    //채팅방에 친구 추가시 이미 채팅방에 존재하는 친구인지 확인
    public boolean overlapChatRoomUser(int chatRoom_id, String nickName){
        boolean check = false;
        String query = "SELECT * FROM DB_ppick.chatRoomUserList WHERE chatRoom_id=? and nickName=?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setString(2,nickName);
            rs = pstmt.executeQuery();

            if(rs.next()){
                check = true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return check;
    }

    // 해당 채팅방 클릭시 채팅방 유저 닉네임을 뽑아냄
    public ArrayList<String> getChatRoomUserList(int chatRoom_id) {
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
        }
        return nickName;
    }

    //채팅방 나가기
    public void exitChatRoom(int chatRoomId, String nickName) {
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
        }
    }










}