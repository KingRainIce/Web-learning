package fruit.servlets;

import fruit.dao.FruitDAO;
import fruit.dao.Impl.FruitDAOImpl;
import fruit.pojo.Fruit;
import myssm.myspringmvc.ViewBaseServlet;
import myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @title:EditServlet
 * @Author Ice
 * @Date: 2022/9/11 23:19
 * @Version 1.0
 */

@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet {

        private FruitDAO fruitDAO = new FruitDAOImpl();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String fidStr = req.getParameter("fid");
                if (StringUtil.isNotEmpty(fidStr)){
                        int fid = Integer.parseInt(fidStr);
                        Fruit fruit = fruitDAO.getFruitById(fid);
                        req.setAttribute("fruit", fruit);
                        super.processTemplate("edit", req, resp);
                }
        }
}