package com.goindol.teamtalk.client.service;


import com.goindol.teamtalk.client.DB.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class chatLogDAO {
    private static chatLogDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private chatLogDAO() {}

    public static chatLogDAO getInstance() {
        if(instance == null)
            instance = new chatLogDAO();
        return instance;
    }


    public void writeLog(String nickName, int chatRoom_id, String content) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String query =
                "INSERT INTO `DB_ppick`.`chatLog`" +
                        "(" +
                        "`nickName`," +
                        "`chatRoom_id`," +
                        "`content`," +
                        "`regDate`" +
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
            pstmt.setString(3, content);
            pstmt.setString(4, sdf.format(now));
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
