import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @title:demo03
 * @Author Ice
 * @Date: 2022/4/13 22:28
 * @Version 1.0
 */

//演示Session 会话
public class demo03 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取session,如果获取不到，则创建一个新的
        HttpSession session = req.getSession();
        System.out.println(session.getId());
    }
}