package fruit.servlets;

import fruit.dao.FruitDAO;
import fruit.dao.Impl.FruitDAOImpl;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

/**
 * @title:DelServlet
 * @Author Ice
 * @Date: 2022/9/12 22:43
 * @Version 1.0
 */

@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            resp.sendRedirect("index");
        }
    }
}