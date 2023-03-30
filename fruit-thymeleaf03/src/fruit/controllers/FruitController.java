package fruit.controllers;

import fruit.dao.FruitDAO;
import fruit.dao.Impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @title:FruitServlet
 * @Author Ice
 * @Date: 2022/9/15 20:50
 * @Version 1.0
 */


public class FruitController extends ViewBaseServlet {

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.init(servletContext);
    }

    private FruitDAO fruitDAO = new FruitDAOImpl();


    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer pageNo = 1;

        String oper = req.getParameter("oper");
        String keyword = null;

//        此时说明是查询发来的请求，pageNo 应该还原成1
        if (StringUtil.isNotEmpty(oper)&&"search".equals(oper)){
            pageNo = 1;

            keyword = req.getParameter("keyword");
            if (StringUtil.isEmpty(keyword)){
                keyword = "";
            }
            session.setAttribute("keyword", keyword);
        }else{
            String pageNoStr = req.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String)keywordObj;
            }else{
                keyword = "";
            }
        }

        session.setAttribute("pageNo", pageNo);

        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList(keyword,pageNo);

        //保存到session作用域
        session.setAttribute("fruitlist", fruitList);

        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = (fruitCount+ 5 - 1) / 5;
        session.setAttribute("pageCount",pageCount);

        super.processTemplate("index", req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fname = req.getParameter("fname");
        Integer price = Integer.parseInt(req.getParameter("price"));
        Integer fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        Fruit fruit = new Fruit(0, fname,price, fcount, remark);

        fruitDAO.addFruit(fruit);
        resp.sendRedirect("fruit.do");
    }

    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDAO.delFruit(fid);

            resp.sendRedirect("fruit.do");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDAO.getFruitById(fid);
            req.setAttribute("fruit", fruit);
            super.processTemplate("edit", req, resp);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        resp.sendRedirect("fruit.do");
    }

}