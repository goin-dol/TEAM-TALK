package com.goindol.teamtalk.client.service;


import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class friendDAO {

    private static friendDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    public static friendDAO getInstance(){
        if(instance==null)
            instance = new friendDAO();
        return instance;
    }

    public void addFriend(String userId, String nickName, String friendId){
        String query =
                "INSERT INTO `DB_ppick`.`friendInfo`" +
                        "(" +
                        "`userId`," +
                        "`nickName`," +
                        "`friendId`," +
                        "`friendStatus`" +
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
            pstmt.setString(1, userId);
            pstmt.setString(2, nickName);
            pstmt.setString(3, friendId);
            pstmt.setBoolean(4, false);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }


}