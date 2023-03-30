package myssm.myspringmvc;

import myssm.io.BeanFactory;
import myssm.io.ClassPathXmlApplicationContext;
import myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @title:DispatcherServlet
 * @Author Ice
 * @Date: 2022/9/17 0:02
 * @Version 1.0
 */

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;

    public DispatcherServlet(){}

    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");

//        从 url 获取到 fruit
//        如：http://localhost:8080/fruit/fruit.do
//        获取到 ：/hello.do
//        然后获取到 hello
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)){
            operate = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (operate.equals(method.getName())){
//            获取请求参数
                    Parameter[] parameters = method.getParameters();
//                    用来存放参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++){
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)){
                            parameterValues[i] = req;
                        }else if ("resp".equals(parameterName)){
                            parameterValues[i] = resp;
                        }else if ("session".equals(parameterName)){
                            parameterValues[i] = req.getSession();
                        }else{
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if (parameterObj != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }
//                controller 组件方法调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);
                    String methodReturnStr = (String) returnObj;
//            视图处理
                    if (methodReturnStr.startsWith("redirect")){
//                    比如 redirect:fruit.do
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else {
                        super.processTemplate(methodReturnStr, req, resp); // 比如 edit
                    }
                }
            }

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}