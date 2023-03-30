package myssm.baseDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @title:connUtil
 * @Author Ice
 * @Date: 2022/9/22 12:38
 * @Version 1.0
 */

public class connUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static final String DRIVER = "com.mysql.jdbc.Driver" ;
    public static final String URL = "jdbc:mysql://localhost:3306/fruit?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String USER = "root";
    public static final String PWD = "2003" ;

    public static Connection createConn(){
        try {
            //1.加载驱动
            Class.forName(DRIVER);
            //2.通过驱动管理器获取连接对象
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConn(){
       Connection conn = threadLocal.get();
       if (conn == null){
           conn = createConn();
           threadLocal.set(conn);
       }
       return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null)
            return;
        if (!conn.isClosed()){
            conn.close();
            threadLocal.set(null);
        }
    }



}