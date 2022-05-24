package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class voteDAO {

    private static voteDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;

    private PreparedStatement pstmt = null;

    private ResultSet rs = null;

    public static voteDAO getInstance(){
        if(instance==null){
            instance = new voteDAO();
        }
        return instance;
    }

    //투표 리스트 - 지워도 될듯
    public List<String> createVoteVar(List<String> v,String content){
        if(content.equals("")){
            System.out.println("입력하지 않은 항목이 있습니다.");
        }else
            v.add(content);
        return v;
    }

    //투표 생성 (투표 리스트 다 담아서 만듦)
    public void creatVote(List<String> v,int chatRoom_id,String title,boolean isAnonymous,boolean isOverLap){
        String query =
                "INSERT INTO `DB_ppick`.`vote` (chatRoom_id,title,isAnonymous,isOverLap)"+
                        "(?,?,?,?)";

        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setString(2,title);
            pstmt.setBoolean(3,isAnonymous);
            pstmt.setBoolean(4, isOverLap);
            pstmt.executeUpdate();

            for (String content : v) {
                String query1 =
                        "INSERT INTO `DB_ppick`.`voteVar` (vote_id,content)" +
                                "(?,?)";
                try{
                    pstmt = conn.prepareStatement(query1);
                    pstmt.setInt(1,Read_Vote_id(chatRoom_id));
                    pstmt.setString(2,content);
                    pstmt.executeUpdate();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    //Vote_id 조회
    public int Read_Vote_id(int chatRoom_id){
        String query =
                "SELECT * FROM `DB_ppick`.`vote` WHERE chatRoom_id=?";

        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt("vote_id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return chatRoom_id;
    }

    //투표 선택
    public void choiceVote(int vote_id,int voteVar_id,String content,String nickName){
        String query =
                "INSERT INTO `DB_ppick`.`voteResult` (vote_id,voteVar_id,content,nickName) values (?,?,?,?)";

        try{

            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1,vote_id);
            pstmt.setInt(2,voteVar_id);
            pstmt.setString(3,content);
            pstmt.setString(4,nickName);
            if(checkOverLap(vote_id))
                // 중복 투표가 될 때 같은 투표 질문에 투표 했는지 체크
                if(!checkOverlapVoteVar(vote_id,voteVar_id,nickName)) pstmt.executeUpdate();
                else System.out.println("이미 투표한 질문입니다.");
            else{
                //중복 투표가 아닐때 투표 했는지 안했는지 체킹
                if(!checkOverlapVoteVar(vote_id,voteVar_id,nickName) && !checkOverlapVoteVar1(vote_id,nickName)) pstmt.executeUpdate();
                else System.out.println("이미 투표 했습니다 경고문");
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    //같은 투표 중복 체킹
    public boolean checkOverlapVoteVar(int vote_id, int voteVar_id , String nickName){
        String query =
                "SELECT count(*) as count FROM `DB_ppick`.`voteResult` FROM vote_id=? and nickName=? and voteVar_id=?";

        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            pstmt.setString(2, nickName);
            pstmt.setInt(3, voteVar_id);
            ResultSet rs = pstmt.executeQuery(query);
            if(rs.next()){
                int cnt = rs.getInt("count");
                if(cnt>=1) return true;
                else return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return false;
    }

    // 중복 투표가 가능할때
    public boolean checkOverlapVoteVar1(int vote_id, String nickName){
        String query =
                "SELECT count(*) as count FROM `DB_ppick`.`voteResult` FROM vote_id=? and nickName=? and voteVar_id=?";


        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            pstmt.setString(2, nickName);
            ResultSet rs = pstmt.executeQuery(query);
            if(rs.next()){
                int cnt = rs.getInt("count");
                if(cnt>=1) return true;
                else return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return false;
    }

    //중복 투표 가능 여부 체킹
    public boolean checkOverLap(int vote_id){
        String query =
                "SELECT * FROM `DB_ppick`.`vote` FROM vote_id=?";

        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            ResultSet rs = pstmt.executeQuery(query);
            if(rs.next()){
                boolean isOverLap = rs.getBoolean("isOverLap");
                if(isOverLap) return true;
                else return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return false;
    }

    //투표 인원 체크
    public void AllReadVote(int chatRoom_id,int vote_id){
        String query =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomUserList` where chatRoom_id=?";

        String query1 =
                "SELECT count(*) as count from `DB_ppick`.`voteVar` where vote_id=?";

        try{
            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int chatCnt = rs.getInt("count");

                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, vote_id);
                ResultSet rs1 = pstmt.executeQuery();
                if(rs1.next()){
                    int votedCnt = rs1.getInt("count");
                    if(chatCnt==votedCnt){
                        //다시 투표 생성 창 ㄱㄱ
                    }else{
                        System.out.println("아직 투표하지 않은 인원이 있습니다. 투표 생성을 진행하시겠습니까 라는 오류 메시지");
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    //투표한 투표 리스트 보여주기
    public List<String> ShowVoteList(int vote_id){

        ArrayList<String> arr = new ArrayList<>();
        String query =
                "SELECT * FROM `DB_ppick`.`voteResult` WHERE vote_id=?";

        try{
            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1,vote_id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                arr.add(rs.getString("nickName"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return null;
    }

    //중복 투표 체크


    //채팅방 인원 체크

}