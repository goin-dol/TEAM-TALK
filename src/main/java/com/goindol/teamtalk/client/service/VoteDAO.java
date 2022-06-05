package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.VoteResultDTO;
import com.goindol.teamtalk.client.model.VoteVarDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {

    private static VoteDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;

    private PreparedStatement pstmt = null;

    private ResultSet rs = null;

    public static VoteDAO getInstance(){
        if(instance==null){
            instance = new VoteDAO();
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
    public void creatVote(int chatRoom_id,String title,boolean isAnonymous,boolean isOverLap){
        String query =
                "INSERT INTO `DB_ppick`.`vote` (chatRoom_id,title,isAnonymous,isOverLap) VALUES "+
                        "(?,?,?,?)";

        try{
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setString(2,title);
            pstmt.setBoolean(3,isAnonymous);
            pstmt.setBoolean(4, isOverLap);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    public void createVoteVar(String content,int vote_id){
        String query1 =
                "INSERT INTO `DB_ppick`.`voteVar` (vote_id,content) VALUES" +
                        "(?,?)";
        try {
            conn = DB.getConnection();


            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, vote_id);
            pstmt.setString(2, content);
            pstmt.executeUpdate();



        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    //투표를 생성할 때 먼저 Vote 테이블에 투표를 만들고 Vote_Var에 투표 리스트들을 다 넣어주기 위해서 Vote테이블에서
    // Vote_id를 가져오는 메소드
    public int getVoteId(int chatRoomId) {
        int voteId = 0;
        String query =
                "SELECT vote_id FROM DB_ppick.vote WHERE chatRoom_id = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoomId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                voteId = rs.getInt("vote_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return voteId;
    }

    //content -> voteVar_id
    public int ReadVoteVarByContent(String content){
        String query = "SELECT * FROM `DB_ppick`.`voteVar` WHERE content=?";

        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,content);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                int voteVar_id = rs.getInt("voteVar_id");
                return voteVar_id;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return 0;
    }


    //투표가 생성되고 투표 리스트들을 확인하는 메소드
    public List<VoteVarDTO> ReadVoteList(int vote_id){

        ArrayList<VoteVarDTO> v = null;

        String query =
                "SELECT * FROM `DB_ppick`.`voteVar` WHERE vote_id=?";

        try{
            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                v = new ArrayList<VoteVarDTO>();
                do {
                    VoteVarDTO voteVarDTO = new VoteVarDTO();
                    voteVarDTO.setVoteVar_id(rs.getInt("voteVar_id"));
                    voteVarDTO.setVote_id(rs.getInt("vote_id"));
                    voteVarDTO.setContent(rs.getString("content"));
                    v.add(voteVarDTO);
                }while(rs.next());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return v;
    }

    //

    public void deleteVote(int vote_id){
        String query =
                "DELETE FROM `DB_ppick`.`vote` WHERE vote_id =?";
        String query1 =
                "DELETE FROM `DB_ppick`.`voteVar` WHERE vote_id =?";
        String query2 =
                "DELETE FROM `DB_ppick`.`voteResult` WHERE vote_id =?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,vote_id);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1,vote_id);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(query2);
            pstmt.setInt(1,vote_id);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }

    }


    //채팅방에서 해당 채팅 인원이 투표방에서 투표를 선택
    public void choiceVote(int vote_id, String content,String nickName){
        String query =
                "INSERT INTO `DB_ppick`.`voteResult` (vote_id,content,nickName) values (?,?,?)";
        String over =
                "SELECT * FROM `DB_ppick`.`vote` WHERE `vote`.`vote_id` = ?";
        try{

            conn = DB.getConnection();

            pstmt = conn.prepareStatement(over);
            pstmt.setInt(1, vote_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                boolean isOverLap = rs.getBoolean("isOverLap");
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1,vote_id);
                pstmt.setString(2,content);
                pstmt.setString(3,nickName);
                /*if(isOverLap) {
                    // 중복 투표가 될 때 같은 투표 질문에 투표 했는지 체크
//                if()
//                else System.out.println("이미 투표한 질문입니다.");

                }
                else {*/
                //중복 투표가 아닐때 투표 했는지 안했는지 체킹
//                if(!checkOverlapVoteVar(vote_id,voteVar_id,nickName) && !checkOverlapVoteVar1(vote_id,nickName)) pstmt.executeUpdate();
//                else System.out.println("이미 투표 했습니다 경고문");
                pstmt.executeUpdate();
                //}
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    //투표가 있는지 체크
    public boolean checkVote(int chatRoom_id){
        boolean status=false;
        String query = "SELECT * FROM DB_ppick.vote WHERE chatRoom_id=?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoom_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    public boolean checkAnnony(int voteId) {
        boolean status = false;
        String query =
                "SELECT isAnonymous FROM DB_ppick.vote where vote_id = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, voteId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getBoolean("isAnonymous")) {
                    status = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return status;
    }

    public boolean checkOverlapVote(int voteId) {
        boolean status = false;
        String query =
                "SELECT isOverLap FROM DB_ppick.vote where vote_id = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, voteId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getBoolean("isOverLap"))
                    status = true;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return status;
    }

    public boolean checkOverLap(int voteId, String nickName) {
        boolean status = false;
        String query =
                "SELECT" +
                        "`voteResult`.`voteResult_id`," +
                        "`voteResult`.`vote_id`," +
                        "`voteResult`.`content`," +
                        "`voteResult`.`nickName`" +
                        "FROM `DB_ppick`.`voteResult` WHERE vote_id = ? and nickName = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, voteId);
            pstmt.setString(2, nickName);
            rs = pstmt.executeQuery();
            if(rs.next())
                status = true;
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return status;
    }

    //같은 투표 중복 체킹
    public boolean checkOverlapVoteVar(int voteid, String nickName){
        boolean status = false;
        String select =
                "SELECT" +
                        "`voteResult`.`voteResult_id`," +
                        "`voteResult`.`vote_id`" +
                        "`voteResult`.`content`," +
                        "`voteResult`.`nickName`" +
                        "FROM `DB_ppick`.`voteResult` WHERE vote_id = ? and nickName = ?";
        try{
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(select);
            pstmt.setInt(1, voteid);
            pstmt.setString(1, nickName);
            rs = pstmt.executeQuery(select);
            if(rs.next()){
                status = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return status;
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
                "SELECT * FROM `DB_ppick`.`vote` WHERE `vote`.`vote_id` = ?";

        try{
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            rs = pstmt.executeQuery(query);
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
    public boolean AllReadVote(int chatRoom_id,int vote_id){
        boolean status=false;
        String query =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomUserList` where chatRoom_id=?";

        String query1 =
                "SELECT count(*) as count from `DB_ppick`.`voteVar` where vote_id=?";

        try{
            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                int chatCnt = rs.getInt("count");

                pstmt = conn.prepareStatement(query1);
                pstmt.setInt(1, vote_id);
                ResultSet rs1 = pstmt.executeQuery();
                if(rs1.next()){
                    int votedCnt = rs1.getInt("count");
                    if(chatCnt==votedCnt){
                        status = true;
                        return status;
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
        return status;
    }

    //각 투표 리스트 별로 투표한 사람들 리스트
    public List<String> showResultByContent(int vote_id,String content){

        List<String> result = null;

        String query =
                "SELECT * FROM `DB_ppick`.`voteResult` WHERE vote_id=? and content=?";

        try{
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,vote_id);
            pstmt.setString(2,content);
            rs=pstmt.executeQuery();

            if (rs.next()){
                result = new ArrayList<String>();
                do {
                    String nickName = rs.getString("nickName");
                    result.add(nickName);
                }while(rs.next());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return result;
    }

    //투표한 투표 리스트 보여주기
    public List<VoteResultDTO> ShowVoteList(int vote_id){

        ArrayList<VoteResultDTO> arr = null;
        String query =
                "select " +
                        " p.content, " +
                        " count(q.content) as count " +
                        " from `DB_ppick`.`voteVar` as p " +
                        " left join " +
                        " `DB_ppick`.`voteResult` as q " +
                        " on p.content = q.content " +
                        " where p.vote_id = ? " +
                        " group by p.content ";

        try{
            conn = DB.getConnection();
            pstmt= conn.prepareStatement(query);
            pstmt.setInt(1,vote_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                arr = new ArrayList<>();
                do{
                    VoteResultDTO voteResultDTO = new VoteResultDTO();
                    voteResultDTO.setCount(rs.getInt("count"));
                    voteResultDTO.setContent(rs.getString("content"));
                    arr.add(voteResultDTO);
                }while(rs.next());
            }
            return arr;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return arr;
    }



    //중복 투표 체크


    //채팅방 인원 체크

}