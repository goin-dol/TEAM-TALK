package user;

import DB.DBDAO;
import friend.friendDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userDAO {
    private static userDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    //싱글톤 패턴(객체를 단 1개만 생성)
    public static userDAO getInstance() {
        if(instance == null)
            instance = new userDAO();
        return instance;
    }

    public void signUp(String userId, String userPassword, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`user`" +
                        "(" +
                        "`userId`," +
                        "`userPassword`," +
                        "`nickName`," +
                        "`status`," +
                        "`ip`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?," +
                        "?," +
                        "'0.0.0.0'" +
                        ")";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            pstmt.setString(3, nickName);
            pstmt.setBoolean(4, false);

            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("중복된 아이디입니다.");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }

    public int login(String userId, String userPassword, String ip) {
        int status = -1;
        String query =
                "SELECT " +
                        "`user`.`userId`," +
                        "`user`.`userPassword`," +
                        "`user`.`nickName`," +
                        "`user`.`status`," +
                        "`user`.`ip`" +
                        "FROM `DB_ppick`.`user` WHERE userId = ? AND userPassword = ?";
        String update =
                "UPDATE `DB_ppick`.`user`" +
                        "SET" +
                        "`status` = true," +
                        "`ip` = ?" +
                        "WHERE `userId` = ?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            rs = pstmt.executeQuery();

            if(rs.next())
                status = 1;
            else
                status = 0;

            pstmt = conn.prepareStatement(update);
            pstmt.setString(1, ip);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return status;
    }

    public void logout(String userId) {
        String query =
                "UPDATE `DB_ppick`.`user`" +
                        "SET" +
                        "`status` = false," +
                        "`ip` = '0.0.0.0'" +
                        "WHERE `userId` = ?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
    }


    public int getFriendCnt(String userId) {
        int Cnt = 0;
        String query =
                "SELECT count(*) FROM DB_ppick.friendInfo where userId = ?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if(rs.next())
                Cnt = rs.getInt(1);
        } catch(Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null) try{conn.close();}catch(SQLException ex){}
            if(pstmt != null) try{pstmt.close();}catch(SQLException ex){}
            if(rs != null) try{rs.close();}catch(SQLException ex){}
        }
        return Cnt;
    }

    public List<friendDTO> getFriendList(String userId) {//zxz4641
        List<friendDTO> friendList = null;
        String query =
                "SELECT" +
                        "`friendInfo`.`f_id`," +
                        "`friendInfo`.`nickName`," +
                        "`friendInfo`.`friendId`" +
                        "FROM `DB_ppick`.`friendInfo` WHERE userId = ?";
        try {
            conn = DB.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                friendList = new ArrayList<friendDTO>();

                do {
                    friendDTO friend = new friendDTO();
                    friend.setF_id(rs.getInt("f_id"));
                    friend.setNickName(rs.getString("nickName"));
                    friend.setFriendId(rs.getString("friendId"));

                    friendList.add(friend);
                }while(rs.next());
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) try {rs.close();}catch(SQLException ex ) {}
            if(pstmt != null) try {pstmt.close();}catch(SQLException ex) {}
            if(conn != null) try {conn.close();}catch(SQLException ex) {}
        }
        return friendList;
    }
}
