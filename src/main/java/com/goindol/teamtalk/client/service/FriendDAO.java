package com.goindol.teamtalk.client.service;


import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.FriendDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendDAO {

    private static FriendDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public static FriendDAO getInstance(){
        if(instance==null)
            instance = new FriendDAO();
        return instance;
    }



    //친구 추가
    public int addFriend(String nickName, String friendNickName){
        int status = 0;
        String friend =
                "SELECT * FROM DB_ppick.friendInfo WHERE nickName = ? AND friendNickName = ?";
        String valid =
                "SELECT * FROM DB_ppick.user WHERE nickName = ?";
        String query =
                "INSERT INTO `DB_ppick`.`friendInfo`" +
                        "(" +
                        "`nickName`," +
                        "`friendNickName`," +
                        "`friendStatus` " +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?, " +
                        "?, " +
                        "? " +
                        ")";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(friend);
            pstmt.setString(1, nickName);
            pstmt.setString(2, friendNickName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                status = 1; //이미 친구
            }else {
                pstmt = conn.prepareStatement(valid);
                pstmt.setString(1, friendNickName);
                rs = pstmt.executeQuery();
                if(rs.next()) {
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, nickName);
                    pstmt.setString(2, friendNickName);
                    pstmt.setBoolean(3, rs.getBoolean("status"));
                    pstmt.executeUpdate();

                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, friendNickName);
                    pstmt.setString(2, nickName);
                    pstmt.setBoolean(3, true);
                    pstmt.executeUpdate();

                }else {
                    status = 2; //없는 닉네임
                }
            }


        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return status;
    }

    //친구 객체를 불러오기 위한 메소드
    public FriendDTO getFriend(String nickName, String friendNickName) {
        FriendDTO friendDTO = null;
        String query =
                "SELECT * FROM `DB_ppick`.`friendInfo` WHERE friendNickName = ? and nickName = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, friendNickName);
            pstmt.setString(2, nickName);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                friendDTO = new FriendDTO();
                friendDTO.setF_id(rs.getInt("f_id"));
                friendDTO.setNickName(rs.getString("nickName"));
                friendDTO.setFriendNickName(rs.getString("friendNickName"));
                friendDTO.setFriendStatus(rs.getBoolean("friendStatus"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return friendDTO;
    }

    public ArrayList<String> getFriendList(String nickName) {
        ArrayList<String> friendList = null;
        String query =
                "SELECT `friendInfo`.`f_id`," +
                        "`friendInfo`.`nickName`," +
                        "`friendInfo`.`friendNickName`," +
                        "`friendInfo`.`friendStatus`" +
                        "FROM `DB_ppick`.`friendInfo` WHERE `friendInfo`.`nickName` = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nickName);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                friendList = new ArrayList<String>();
                do {
                    friendList.add(rs.getString("friendNickName"));
                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return friendList;
    }


}