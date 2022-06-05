package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.ChatRoomListDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChatRoomListDAO {
    private static ChatRoomListDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private ChatRoomListDAO() { }

    public static ChatRoomListDAO getInstance() {
        if(instance == null)
            instance = new ChatRoomListDAO();
        return instance;
    }

    public void createChatRoom(String chatRoomName, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`chatRoomList` (`chatRoomName`, `nickName`) VALUES ( ?, ? ) ";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, chatRoomName);
            pstmt.setString(2, nickName);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

    public int getChatRoomId(String chatRoomName, String nickName) {
        int cnt = 0;
        String query = "SELECT " +
                "`chatRoomList`.`chatRoom_id`" +
                "FROM `DB_ppick`.`chatRoomList` WHERE chatRoomName = ? and nickName = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, chatRoomName);
            pstmt.setString(2, nickName);
            rs = pstmt.executeQuery();
            if(rs.next())
                cnt = rs.getInt(1);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return cnt;
    }

    //최초 방 생성시 자기 자신 아이디 정보 입력
    //여러명 초대시 해당 매개변수 리스트에 담아서 반복 실행
    public int inviteChatRoom(int chatRoom_id, String nickName) {
        String check_query =
                "select nickName from chatRoomUserList where nickName = ? and chatRoom_id = ?";
        String query =
                "INSERT INTO `DB_ppick`.`chatRoomUserList`" +
                        "(" +
                        "`chatRoom_id`," +
                        "`nickName`," +
                        "`isNoticeRead`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?" +
                        ")";
        String notice =
                "SELECT * FROM DB_ppick.notice WHERE chatRoom_id = ?";
        try {

            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(check_query);
            pstmt.setString(1, nickName);
            pstmt.setInt(2, chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                //이미 있는 사람
                return 2;
            }
            pstmt = conn.prepareStatement(notice);
            pstmt.setInt(1, chatRoom_id);
            rs = pstmt.executeQuery();

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            pstmt.setString(2, nickName);
            if(rs.next())
                pstmt.setInt(3, 1);
            else
                pstmt.setInt(3, 0);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return 1;
    }


    public String getCurrentChatRoomName(int chatRoomId) {
        String query = "SELECT chatRoomName FROM DB_ppick.chatRoomList WHERE chatRoom_id = ?";
        String title = null;
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoomId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                title = rs.getString("chatRoomName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title;
    }

    public ArrayList<ChatRoomListDTO> getChatRoomName(String nickName) {
        ArrayList<ChatRoomListDTO> roomName = null;
        String query =
                "select p.chatRoom_id," +
                        "   p.chatRoomName, " +
                        "   q.isNoticeRead " +
                        "from chatRoomList as p " +
                        "join chatRoomUserList as q " +
                        "on p.chatRoom_id = q.chatRoom_id " +
                        "where q.nickName = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nickName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                roomName = new ArrayList<ChatRoomListDTO>();
                do{
                    ChatRoomListDTO chatRoomListDTO = new ChatRoomListDTO();
                    chatRoomListDTO.setChatRoom_id(rs.getInt("chatRoom_id"));
                    chatRoomListDTO.setChatRoomName(rs.getString("chatRoomName"));
                    chatRoomListDTO.setNoticeRead(rs.getInt("isNoticeRead"));
                    roomName.add(chatRoomListDTO);
                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return roomName;
    }


}