package com.goindol.teamtalk.client.dao;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.dto.VoteDTO;
import com.goindol.teamtalk.client.dto.VoteResultDTO;
import com.goindol.teamtalk.client.dto.VoteVarDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {

    private static VoteDAO instance = null;

    private Connection conn = null;

    private PreparedStatement pstmt = null;

    private ResultSet rs = null;

    public static VoteDAO getInstance(){
        if(instance==null){
            instance = new VoteDAO();
        }
        return instance;
    }

    //투표 생성 (투표 리스트 다 담아서 만듦)
    public void creatVote(int chatRoom_id,String title,boolean isAnonymous,boolean isOverLap){
        String query =
                "INSERT INTO `DB_ppick`.`vote` (chatRoom_id,title,isAnonymous,isOverLap) VALUES "+
                        "(?,?,?,?)";
        String init =
                "UPDATE `DB_ppick`.`chatRoomParticipants`" +
                        "SET" +
                        "`isVoted` = 1 " +
                        "WHERE `chatRoom_id` = ? ";
        try{
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,chatRoom_id);
            pstmt.setString(2,title);
            pstmt.setBoolean(3,isAnonymous);
            pstmt.setBoolean(4, isOverLap);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(init);
            pstmt.setInt(1, chatRoom_id);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
    }

    public void createVoteVar(String content,int vote_id){
        String query1 =
                "INSERT INTO `DB_ppick`.`voteVar` (vote_id,content) VALUES" +
                        "(?,?)";
        try {
            conn = DBDAO.getConnection();


            pstmt = conn.prepareStatement(query1);
            pstmt.setInt(1, vote_id);
            pstmt.setString(2, content);
            pstmt.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return voteId;
    }

    //투표가 생성되고 투표 리스트들을 확인하는 메소드
    public List<VoteVarDTO> readVoteVar(int vote_id){
        ArrayList<VoteVarDTO> v = null;
        String query =
                "SELECT * FROM `DB_ppick`.`voteVar` WHERE vote_id=?";

        try{
            conn = DBDAO.getConnection();
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return v;
    }

    public void deleteVote(int vote_id){
        String query =
                "DELETE FROM `DB_ppick`.`vote` WHERE vote_id =?";
        String query1 =
                "DELETE FROM `DB_ppick`.`voteVar` WHERE vote_id =?";
        String query2 =
                "DELETE FROM `DB_ppick`.`voteResult` WHERE vote_id =?";
        try {
            conn = DBDAO.getConnection();
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }

    }


    //채팅방에서 해당 채팅 인원이 투표방에서 투표를 선택
    public void choiceVote(int vote_id, int chatRoom_id, String content,String nickName){
        String query =
                "INSERT INTO `DB_ppick`.`voteResult` (vote_id,content,nickName) values (?,?,?)";
        String update =
                "UPDATE `DB_ppick`.`chatRoomParticipants`" +
                        "SET `isVoted`=2 " +
                        "WHERE `nickName`=? and chatRoom_id=?";
        String over =
                "SELECT * FROM `DB_ppick`.`vote` WHERE `vote`.`vote_id` = ?";
        try{

            conn = DBDAO.getConnection();

            pstmt = conn.prepareStatement(over);
            pstmt.setInt(1, vote_id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                pstmt = conn.prepareStatement(update);
                pstmt.setString(1, nickName);
                pstmt.setInt(2, chatRoom_id);
                pstmt.executeUpdate();

                boolean isOverLap = rs.getBoolean("isOverLap");
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1,vote_id);
                pstmt.setString(2,content);
                pstmt.setString(3,nickName);
                pstmt.executeUpdate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
    }

    //vote_id로 VoteDTO 조회
    public VoteDTO findVoteByVoteId(int vote_id, int chatRoom_id){
        VoteDTO voteDTO = null;
        String query = "SELECT * FROM DB_ppick.vote WHERE vote_id=? and chatRoom_id=?";

        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vote_id);
            pstmt.setInt(2,chatRoom_id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                String title = rs.getString("title");
                boolean isAnonymous = rs.getBoolean("isAnonymous");
                boolean isOverLap = rs.getBoolean("isOverLap");

                voteDTO = new VoteDTO(title, isAnonymous, isOverLap);
                voteDTO.setVote_id(vote_id);
                return voteDTO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return voteDTO;
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
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return status;
    }

    //해당 유저가 투표를 했는지
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return status;
    }


    //투표 인원 체크
    public boolean isReadAllParticipants(int chatRoom_id) {
        String query =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomParticipants` where chatRoom_id=?";

        String query1 =
                "SELECT count(*) as count from `DB_ppick`.`chatRoomParticipants` where isVoted=? and chatRoom_id=?";

        try {
            conn = DBDAO.getConnection();
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return false;
    }


    //각 투표 리스트 별로 투표한 사람들 리스트 조회
    public List<String> readVoteUserByContent(int vote_id, String content){
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return result;
    }

    //투표한 투표 리스트 보여주기
    public List<VoteResultDTO> showVoteList(int vote_id){

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
            conn = DBDAO.getConnection();
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
            if(conn != null) try {conn.close();}catch(SQLException ex ) {}
        }
        return arr;
    }


}