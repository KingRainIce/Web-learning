package myssm.trans;

import myssm.baseDao.connUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @title:TransactionManager
 * @Author Ice
 * @Date: 2022/9/22 12:38
 * @Version 1.0
 */

public class TransactionManager {
//    开启事务
    public static void beginTrans() throws SQLException {
        connUtil.getConn().setAutoCommit(false);
    }

//    提交事务
    public static void commit() throws SQLException {
        Connection conn = connUtil.getConn();
        conn.commit();
        connUtil.closeConn();
    }

//    回滚事务
    public static void rollback() throws SQLException {
        Connection conn = connUtil.getConn();
        conn.rollback();
        connUtil.closeConn();
    }
}