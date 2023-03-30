package servlets;

import fruit.dao.FruitDAO;
import fruit.dao.impl.FruitDAOImpl;
import fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:AddServlet
 * @Author Ice
 * @Date: 2022/4/9 17:07
 * @Version 1.0
 */

public class AddServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        Integer price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        Integer fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        System.out.println("fname = " + fname);
        System.out.println("price = " + price);
        System.out.println("fcount = " + fcount);
        System.out.println("remark = " + remark);

        FruitDAO fruitDAO = new FruitDAOImpl();
        boolean flag = fruitDAO.addFruit(new Fruit(1,fname,price,fcount,remark));

        System.out.println(flag ? "success" : "failed");
    }
}