package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.chatRoomListDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class chatRoomListDAO {
    private static chatRoomListDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private chatRoomListDAO() { }

    public static chatRoomListDAO getInstance() {
        if(instance == null)
            instance = new chatRoomListDAO();
        return instance;
    }

    public void createChatRoom(String chatRoomName, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`chatRoomList` (`chatRoomName`, `nickName`) VALUES ( ?, ? ) ";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, chatRoomName);
            pstmt.setString(2, nickName);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    public int getChatRoomId(String chatRoomName, String nickName) {
        int cnt = 0;
        String query = "SELECT " +
                "`chatRoomList`.`chatRoom_id`" +
                "FROM `DB_ppick`.`chatRoomList` WHERE chatRoomName = ? and nickName = ?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, chatRoomName);
            pstmt.setString(2, nickName);
            rs = pstmt.executeQuery();
            if(rs.next())
                cnt = rs.getInt(1);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
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
                        false +
                        ")";
        try {

            conn = DB.getConnection();
            pstmt = conn.prepareStatement(check_query);
            pstmt.setString(1, nickName);
            pstmt.setInt(2, chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                //이미 있는 사람
                return 2;
            }
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            pstmt.setString(2, nickName);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return 1;
    }

    //이 메소드는 전에 테이블 1개로 했을떄 썻던 메소드
    public void inviteFriend(String chatRoomName, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`chatRoom`" +
                        "(" +
                        "`chatRoomName`," +
                        "`nickName`," +
                        "`isNoticeRead`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?" +
                        ")";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, chatRoomName);
            pstmt.setString(2, nickName);
            pstmt.setBoolean(3, false);

            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    public ArrayList<chatRoomListDTO> getChatRoomName(String nickName) {
        ArrayList<chatRoomListDTO> roomName = null;
        String query =
                "select " +
                        "chatRoom_id, " +
                        "chatRoomName " +
                        "from chatRoomList " +
                        "where chatRoom_id in (" +
                        "select chatRoom_id" +
                        "    from chatRoomUserList " +
                        "    where nickName = ?" +
                        ")";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nickName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                roomName = new ArrayList<chatRoomListDTO>();
                do{
                    chatRoomListDTO chatRoomListDTO = new chatRoomListDTO();
                    chatRoomListDTO.setChatRoom_id(rs.getInt("chatRoom_id"));
                    chatRoomListDTO.setChatRoomName(rs.getString("chatRoomName"));

                    roomName.add(chatRoomListDTO);
                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return roomName;
    }


    //EntryChatRoom(int chatRoom_id) ChatLog 엔티티 불러와서 출력



}
