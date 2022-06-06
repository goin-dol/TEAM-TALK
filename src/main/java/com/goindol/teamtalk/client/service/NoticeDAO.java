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

    //예외 흐름 1 공지 내용이 없을 때
    public boolean createNoticeEx1(int chatRoom_id){
        String query =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                String content1 = rs.getString("content");
                if(content1.equals("")){
                    // 예외흐름1
                    return true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return false;
    }

    // 공지 보여줌
    public NoticeDTO showNotice(int chatRoom_id){
        NoticeDTO noticeDTO = null;
        String query =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String title = rs.getString("title");
                String content = rs.getString("content");
                noticeDTO = new NoticeDTO(title, content);

            }else{
                System.out.println("공지 없음요");
            }

        }
        catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

        return noticeDTO;
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

    public void updateIsNoticed(int chatRoom_id,String nickName){
        String query="UPDATE `DB_ppick`.`chatRoomUserList`" +
                "SET `isNoticeRead`=2 " +
                "WHERE `nickName`=? and chatRoom_id=?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            pstmt.setString(2,nickName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

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

    //공지 생성 버튼을 눌렀을때 (예외흐름 2)
    public void createNoticeEx2(String nickName, int chatRoom_id,String title,String content){

        if(AllReadNotice(chatRoom_id)){
            //공지 제목과 내용을 입력하는 페이지로 넘어가야함 (예외흐름 3)
        }else{
            //예외흐름 2 , 모든 인원이 공지를 다 읽지 않았을 때
            System.out.println("아직 공지를 확인하지 않은 인원이 있습니다. 공지 생성을 진행하시겠습니까?");
        }


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
    public ArrayList<String> readNoticeList(int chatRoom_id){

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
    public boolean AllReadNotice(int chatRoom_id){
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