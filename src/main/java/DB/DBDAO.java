package DB;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBDAO {
    private static DBDAO instance = null;

    public static DBDAO getInstance() {
        if (instance == null)
            instance = new DBDAO();

        return instance;
    }

    public static Connection getConnection() throws Exception {
        Connection conn = null;
        Context ctx;

        ctx = new InitialContext();
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql:// 54.159.140.253:3306/DB_ppick", "ppick", "Ppick0420!");

        System.out.println("알림 - DB연동이 정상적으로 되었습니다.");

        return conn;
    }
}
