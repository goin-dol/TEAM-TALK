package com.goindol.teamtalk.client.service;

import com.goindol.teamtalk.client.DB.DBDAO;
import com.goindol.teamtalk.client.model.FriendDTO;
import com.goindol.teamtalk.client.model.UserDTO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private static UserDAO instance = null;

    private static DBDAO DB = DBDAO.getInstance();

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    //싱글톤 패턴(객체를 단 1개만 생성)
    public static UserDAO getInstance() {
        if (instance == null)
            instance = new UserDAO();
        return instance;
    }

    public void signUp(String userId, String userPassword, String nickName) {
        String query =
                "INSERT INTO `DB_ppick`.`user`" +
                        "(" +
                        "`userId`," +
                        "`userPassword`," +
                        "`nickName`," +
                        "`status`" +
                        ")" +
                        "VALUES" +
                        "(" +
                        "?," +
                        "?," +
                        "?," +
                        "?" +
                        ")";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            pstmt.setString(3, nickName);
            pstmt.setBoolean(4, false);

            pstmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println("중복된 아이디입니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
        }
    }


    public int validateSignUp(String userId, String nickName) {
        int status = 0;
        String query1 =
                "SELECT " +
                        "`user`.`userId`," +
                        "`user`.`userPassword`," +
                        "`user`.`nickName`," +
                        "`user`.`status`" +
                        "FROM `DB_ppick`.`user` WHERE userId = ? ";

        String query =
                "SELECT " +
                        "`user`.`userId`," +
                        "`user`.`userPassword`," +
                        "`user`.`nickName`," +
                        "`user`.`status`" +
                        "FROM `DB_ppick`.`user` WHERE nickName = ?";


        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                status = 1;
                return status;
            }

            pstmt = conn.prepareStatement(query1);
            pstmt.setString(1, nickName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                status = 2;
                return status;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
        }
        return status;
    }


    public int login(String userId, String userPassword) {
        int status = 0;
        String overLap = "SELECT * FROM `DB_ppick`.`user` WHERE userId=?";
        String query =
                "SELECT " +
                        "`user`.`userId`," +
                        "`user`.`userPassword`," +
                        "`user`.`nickName`," +
                        "`user`.`status`" +
                        "FROM `DB_ppick`.`user` WHERE userId = ? AND userPassword = ?";
        String update =
                "UPDATE `DB_ppick`.`user`" +
                        "SET " +
                        "`status` = true " +
                        "WHERE `userId` = ?";

        String login =
                "UPDATE `DB_ppick`.`friendInfo` " +
                        "SET " +
                        "`friendStatus` = true " +
                        "WHERE `friendNickName` = ?";
        try {
            conn = DBDAO.getConnection();


            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pstmt = conn.prepareStatement(overLap);
                pstmt.setString(1, userId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    if (!rs.getBoolean("status")) {
                        pstmt = conn.prepareStatement(update);
                        pstmt.setString(1, userId);
                        pstmt.executeUpdate();

                        pstmt = conn.prepareStatement(login);
                        pstmt.setString(1, rs.getString("nickName"));
                        pstmt.executeUpdate();

                        status = 3;
                    } else {
                        status = 2;
                    }
                }
            } else
                return 1;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
        }
        return status;
    }


    public UserDTO getUser(String userId, String userPassword) {
        String query = "SELECT * FROM user WHERE userId = ? AND userPassword = ?";
        UserDTO userDTO = null;
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("rs.next!!");
                userDTO = new UserDTO();
                userDTO.setUserId(rs.getString("userId"));
                userDTO.setUserPassword(rs.getString("userPassword"));
                userDTO.setNickName(rs.getString("nickName"));
                userDTO.setStatus(rs.getBoolean("status"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
        }
        return userDTO;
    }


    public void logout(String userId, String nickName) {
        String update =
                "UPDATE `DB_ppick`.`user`" +
                        "SET" +
                        "`status` = false " +
                        "WHERE `userId` = ?";
        String logout =
                "UPDATE `DB_ppick`.`friendInfo` " +
                        "SET " +
                        "`friendStatus` = false " +
                        "WHERE `friendNickName` = ?";
        try {
            conn = DBDAO.getConnection();
            pstmt = conn.prepareStatement(update);
            pstmt.setString(1, userId);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(logout);
            pstmt.setString(1, nickName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
        }
    }




}