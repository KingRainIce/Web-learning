package myssm.filters;

import myssm.trans.TransactionManager;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @title:OpenSessionViewFliter
 * @Author Ice
 * @Date: 2022/9/22 10:59
 * @Version 1.0
 */

public class OpenSessionViewFliter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TransactionManager.beginTrans();
            filterChain.doFilter(servletRequest, servletResponse);
            TransactionManager.commit();
        } catch (SQLException e) {
            try {
                TransactionManager.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}