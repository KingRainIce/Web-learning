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
 * @title:UpdateServlet
 * @Author Ice
 * @Date: 2022/9/12 21:25
 * @Version 1.0
 */

@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {

    private FruitDAO fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String fidStr = req.getParameter("fid");
        Integer fid = Integer.parseInt(fidStr);
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        fruitDAO.updateFruit(new Fruit(fid,fname,price,fcount,remark));

//        super.processTemplate("index", req, resp);
        resp.sendRedirect("index");
    }
}