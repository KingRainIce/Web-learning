import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title:demo2
 * @Author Ice
 * @Date: 2022/4/13 21:56
 * @Version 1.0
 */

public class demo02 extends HttpServlet{

        public demo02(){
            System.out.println("正在实例化....");
        }

        @Override
        public void init() throws ServletException {
            System.out.println("正在初始化.....");
        }

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("正在服务.......");
        }

        @Override
        public void destroy() {
            System.out.println("正在销毁......");
        }
    }