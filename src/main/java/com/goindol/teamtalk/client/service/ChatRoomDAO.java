package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.ChatRoomListDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatRoomDAO {
    private static ChatRoomDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private ChatRoomDAO() { }

    public static ChatRoomDAO getInstance() {
        if(instance == null)
            instance = new ChatRoomDAO();
        return instance;
    }

    //채팅 생성
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
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

    // 채팅방을 만들고 자기 자신을 초대할 때 채팅방 id 필요
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
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return cnt;
    }

    // 채팅방에 초대할 때 친구인지 확인
    public boolean checkFriend(String nickName,String friend){
        Boolean check=false;
        String check_query =
                "select * from friendInfo where nickName = ? and friendNickName=?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(check_query);
            pstmt.setString(1,nickName);
            pstmt.setString(2,friend);
            rs=pstmt.executeQuery();
            if(rs.next()) check = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return check;
    }

    //최초 방 생성시 자기 자신 아이디 정보 입력
    //여러명 초대시 해당 매개변수 리스트에 담아서 반복 실행
    public int inviteChatRoom(int chatRoom_id, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`chatRoomUserList`" +
                        "(" +
                        "`chatRoom_id`," +
                        "`nickName`," +
                        "`isNoticeRead`," +
                        "`isVoted`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?," +
                        "?" +
                        ")";
        String notice =
                "SELECT * FROM DB_ppick.notice WHERE chatRoom_id = ?";
        String vote =
                "SELECT * FROM vote WHERE chatRoom_id = ?";
        try {

            pstmt = conn.prepareStatement(notice);
            pstmt.setInt(1, chatRoom_id);
            rs = pstmt.executeQuery();

            pstmt = conn.prepareStatement(vote);
            pstmt.setInt(1, chatRoom_id);
            ResultSet rs1 = pstmt.executeQuery();

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            pstmt.setString(2, nickName);
            if(rs.next())
                pstmt.setInt(3, 1);
            else
                pstmt.setInt(3, 0);
            if(rs1.next())
                pstmt.setInt(4, 1);
            else
                pstmt.setInt(4, 0);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return 1;
    }


    //현재 채팅방 이름을 가져옴
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
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return title;
    }

    //채팅방 리스트를 보여줄때 해당 회원이 참여하고 있는 채팅방 이름들을 가져옴
    public ArrayList<ChatRoomListDTO> getChatRoomNameList(String nickName) {
        ArrayList<ChatRoomListDTO> roomName = null;
        String query =
                "select p.chatRoom_id, " +
                        "   p.chatRoomName, " +
                        "   q.isNoticeRead, " +
                        "       q.isVoted "  +
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
                    chatRoomListDTO.setVoted(rs.getInt("isVoted"));
                    roomName.add(chatRoomListDTO);
                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return roomName;
    }


}