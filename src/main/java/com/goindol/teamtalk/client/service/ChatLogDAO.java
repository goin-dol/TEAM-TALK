package com.goindol.teamtalk.client.service;


import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatLogDAO {
    private static ChatLogDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private ChatLogDAO() {}

    public static ChatLogDAO getInstance() {
        if(instance == null)
            instance = new ChatLogDAO();
        return instance;
    }


    public void writeLog(int chatRoom_id, String content) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query =
                "INSERT INTO `DB_ppick`.`chatLog`" +
                        "(" +
                        "`chatRoom_id`," +
                        "`content`," +
                        "`regDate`" +
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
            pstmt.setInt(1, chatRoom_id);
            pstmt.setString(2, content);
            pstmt.setString(3, sdf.format(now));
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
           // if(rs != null) try {rs.close();}catch(SQLException ex ) {}
           // if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
    }

    public List<String> showChatLog(int chatRoomId) {
        List<String> content = null;
        String query = "SELECT * FROM DB_ppick.chatLog WHERE chatRoom_id = ? order by regDate asc";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, chatRoomId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                content = new ArrayList<String>();
                do {
                    content.add(rs.getString("content"));
                }while(rs.next());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // if(rs != null) try {rs.close();}catch(SQLException ex ) {}
           // if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
        }
        return content;
    }

}