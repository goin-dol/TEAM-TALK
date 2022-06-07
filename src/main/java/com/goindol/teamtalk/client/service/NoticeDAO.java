package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.NoticeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NoticeDAO {

    private static NoticeDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;

    private PreparedStatement pstmt = null;

    private ResultSet rs = null;

    public static NoticeDAO getInstance(){
        if(instance==null){
            instance = new NoticeDAO();
        }
        return instance;
    }

    //공지 생성버튼을 누르고 공지 제목과 내용을 받은 페이지
    public void createNotice(String nickName, int chatRoom_id,String title,String content){
        String query=
                "INSERT INTO `DB_ppick`.`notice`" +
                        "(" +
                        "`nickName`," +
                        "`chatRoom_id`," +
                        "`title`," +
                        "`content`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?," +
                        "?" +
                        ")";
        String init =
                "UPDATE `DB_ppick`.`chatRoomUserList`" +
                        "SET" +
                        "`isNoticeRead` = 1 " +
                        "WHERE `chatRoom_id` = ? ";

        String update="UPDATE `DB_ppick`.`chatRoomUserList`" +
                "SET `isNoticeRead`=2 " +
                "WHERE `nickName`=? and chatRoom_id=?";
        try {

            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nickName);
            pstmt.setInt(2, chatRoom_id);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(init);
            pstmt.setInt(1, chatRoom_id);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(update);
            pstmt.setString(1,nickName);
            pstmt.setInt(2, chatRoom_id);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

    //공지 삭제
    public void deleteNotice(int chatRoom_id){
        String query =
                "DELETE FROM `DB_ppick`.`notice` WHERE chatRoom_id =?";
        String update =
                "UPDATE `DB_ppick`.`chatRoomUserList`" +
                        "SET `isNoticeRead`= 1 " +
                        "WHERE chatRoom_id=?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(update);
            pstmt.setInt(1, chatRoom_id);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

    }


    // 해당 채팅 방에 공지가 있는지
    public boolean checkNotice(int chatRoom_id){
        String query =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                return true;
            }

        }catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

        return false;
    }


    //공지 내용을 보여줌
    public NoticeDTO showNoticeContent(int chatRoom_id, String nickName){
        NoticeDTO noticeDTO = null;
        String select =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";
        String update =
                "UPDATE `DB_ppick`.`chatRoomUserList`" +
                        "SET `isNoticeRead`=2 " +
                        "WHERE `nickName`=? and chatRoom_id=?";
        String query =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(select);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                pstmt = conn.prepareStatement(update);
                pstmt.setString(1, nickName);
                pstmt.setInt(2, chatRoom_id);
                pstmt.executeUpdate();

                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, chatRoom_id);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    noticeDTO = new NoticeDTO();
                    noticeDTO.setNotice_id(rs.getInt("notice_id"));
                    noticeDTO.setNickName(rs.getString("nickName"));
                    noticeDTO.setChatRoom_id(rs.getInt("chatRoom_id"));
                    noticeDTO.setTitle(rs.getString("title"));
                    noticeDTO.setContent(rs.getString("content"));
                }
            }

        }catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

        return noticeDTO;
    }


    //공지 읽기 (확인)
    public void readNotice(String nickName, int chatRoom_id){

        //공지가 있을 때 공지 확인
        String update =
                "UPDATE `DB_ppick`.`chatRoomUserList`" +
                        "SET `isNoticeRead`=2 " +
                        "WHERE `nickName`=? and chatRoom_id=?";

        try {
            conn = DB.getConnection();

            //공지가 하나라도 있으면 수정 가능
            pstmt = conn.prepareStatement(update);
            pstmt.setString(1,nickName);
            pstmt.setInt(2,chatRoom_id);
            pstmt.executeUpdate();

        } catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }
    //공지 읽은 사람 리스트
    public ArrayList<String> readNoticeUserList(int chatRoom_id){

        ArrayList<String> chatPeople = new ArrayList<>();
        String query =
                "SELECT * FROM `DB_ppick`.`chatRoomUserList` WHERE chatRoom_id =? and isNoticeRead=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setInt(2,2);
            rs = pstmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("nickName");
                chatPeople.add(nickName);
            }

        }catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return chatPeople;
    }

    // 공지를 전부 읽었는지 확인
    public boolean allReadNotice(int chatRoom_id){
        String query =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomUserList` where chatRoom_id=?";

        String query1 =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomUserList` where isNoticeRead=? and chatRoom_id=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, 2);
                pstmt.setInt(2, chatRoom_id);
                ResultSet rs1 = pstmt.executeQuery();

                if(rs1.next()){
                    int cnt = rs.getInt(1);
                    int cnt1 = rs1.getInt(1);
                    if(cnt==cnt1){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        } catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return false;
    }

}