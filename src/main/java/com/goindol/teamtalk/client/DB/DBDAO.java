package com.goindol.teamtalk.client.DB;

import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DBDAO {
    private static DBDAO instance = null;

    public static DBDAO getInstance() {
        if (instance == null)
            instance = new DBDAO();

        return instance;
    }
    public static Connection getConnection() throws Exception {
        DataSource dataSource;
        //BasicDataSource ds = new BasicDataSource();
        Connection conn = null;
        Context ctx;

        ctx = new InitialContext();
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql:// goindoll.ca0cdywkoqkw.us-east-1.rds.amazonaws.com:3306/DB_ppick", "goindoll", "Goindoll0420!");
        //conn = DriverManager.getConnection("jdbc:mysql:// 127.0.0.1:3306/db_team?useUnicode=true&serverTimezone=Asia/Seoul", "root", "3249");
        return conn;
    }
}
