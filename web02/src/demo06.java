import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:demo06
 * @Author Ice
 * @Date: 2022/4/13 22:44
 * @Version 1.0
 */

public class demo06 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("demo06");
        //服务器端内部转发,偷偷地转发
        //req.getRequestDispatcher("demo07").forward(req,resp);

        //客户端重定向
        resp.sendRedirect("demo07");
    }
}