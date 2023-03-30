package fruit.servlets;

import fruit.dao.FruitDAO;
import fruit.dao.Impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @title:IndexServlet
 * @Author Ice
 * @Date: 2022/4/13 23:50
 * @Version 1.0
 */

@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
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
}