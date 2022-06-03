package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.NoticeDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class noticeDAO {

    private static noticeDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;

    private PreparedStatement pstmt = null;

    private ResultSet rs = null;

    public static noticeDAO getInstance(){
        if(instance==null){
            instance = new noticeDAO();
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

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nickName);
            pstmt.setInt(2, chatRoom_id);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();

            //예외흐름 1
            if(createNoticeEx1(chatRoom_id)){
                System.out.println("입력하지 않은 항목이 있습니다. 라는 오류메시지");
                // 공지 삭제
                deleteNotice(chatRoom_id);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

    //공지 삭제
    public void deleteNotice(int chatRoom_id){
        String query =
                "DELETE FROM `DB_ppick`.`notice` WHERE chatRoom_id =?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
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
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String content1 = rs.getString("content");
                if(content1.equals("")){
                    // 예외흐름1
                    return true;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return false;
    }

    // 공지 보여줌
    public NoticeDTO showNotice(int chatRoom_id){
        String query =
                "SELECT * FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                String title = rs.getString("title");
                String content = rs.getString("content");
                NoticeDTO noticeDTO = new NoticeDTO(title, content);
                return noticeDTO;
            }else{
                System.out.println("공지 없음요");
            }

        }
        catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

        return null;
    }
    // 해당 채팅 방에 공지가 있는지
    public boolean checkNotice(int chatRoom_id){
        String query =
                "SELECT count(*) as count FROM `DB_ppick`.`notice` WHERE chatRoom_id=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                int cnt = rs.getInt("count");
                if(cnt==1){
                    return true;
                } else return false;
            }

        }catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }

        return false;
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

        //공지가 있는지 확인하기 위한 쿼리문
        String query =
                "SELECT count(*) as count FROM `DB_ppick`.`notice`";

        //공지가 있을 때 공지 확인
        String update =
                "UPDATE `DB_ppick`.`chatRoomUserList`" +
                        "SET `isNoticeRead`=TRUE " +
                        "WHERE `nickName`=? and chatRoom_id=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int noticeCnt = rs.getInt("count");

                //공지가 하나라도 있으면 수정 가능
                if(noticeCnt==1){
                    pstmt = conn.prepareStatement(update);
                    pstmt.setString(1,nickName);
                    pstmt.setInt(2,chatRoom_id);
                    pstmt.executeUpdate();
                }else{
                    System.out.println("공지가 없습니다. 라는 오류메시지 출력");
                }
            }

        } catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }
    //공지 읽은 사람 리스트
    public List<String> readNoticeList(int chatRoom_id){

        ArrayList<String> chatPeople = new ArrayList<>();
        String query =
                "SELECT * FROM `DB_ppick`.`chatRoomUserList` WHERE chatRoom_id =? and isNoticeRead=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setBoolean(2,true);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("nickName");
                chatPeople.add(nickName);
            }
            return chatPeople;
        }catch(Exception e) {
            //시스템이 오류 메시지 출력
            e.printStackTrace();
        } finally {
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return null;
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
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                pstmt = conn.prepareStatement(query1);
                pstmt.setBoolean(1, true);
                pstmt.setInt(2, chatRoom_id);
                ResultSet rs1 = pstmt.executeQuery();

                if(rs1.next()){
                    int cnt = rs.getInt("count");
                    int cnt1 = rs1.getInt("count");
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
            //if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            //if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return false;
    }

}