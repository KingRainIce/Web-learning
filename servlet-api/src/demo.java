import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * @title:demo
 * @Author Ice
 * @Date: 2022/9/18 23:24
 * @Version 1.0
 */


@WebServlet(urlPatterns = {"/demo01"} ,
        initParams = {
            @WebInitParam(name="hello",value="world"),
            @WebInitParam(name="uname",value="jim")
        }
        )

public class demo extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String initValue = config.getInitParameter("hello");
        System.out.println("initValue = " + initValue);

        ServletContext servletContext = getServletContext();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        System.out.println("contextConfigLocation = " + contextConfigLocation);
    }
}