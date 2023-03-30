import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:demo05
 * @Author Ice
 * @Date: 2022/4/13 22:35
 * @Version 1.0
 */

public class demo05 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object unameObj = req.getSession().getAttribute("uname");
        System.out.println(unameObj);
    }
}