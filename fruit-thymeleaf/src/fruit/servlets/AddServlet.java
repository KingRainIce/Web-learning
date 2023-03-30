package fruit.servlets;

import fruit.dao.FruitDAO;
import fruit.dao.Impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:AddServlet
 * @Author Ice
 * @Date: 2022/9/12 23:10
 * @Version 1.0
 */

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        Fruit fruit = new Fruit(0, fname,price, fcount, remark);

        fruitDAO.addFruit(fruit);
        resp.sendRedirect("index");
    }
}