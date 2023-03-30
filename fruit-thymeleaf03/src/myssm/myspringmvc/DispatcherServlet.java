package myssm.myspringmvc;

import myssm.util.StringUtil;
import org.omg.PortableServer.ServantLocatorPOA;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @title:DispatcherServlet
 * @Author Ice
 * @Date: 2022/9/17 0:02
 * @Version 1.0
 */

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    private Map<String, Object> beanMap = new HashMap<>();

    public void init(){
        try {
//            读取 xml文件里的 beans
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

//            获取 bean 节点
            NodeList bean = document.getElementsByTagName("bean");
            for (int i = 0; i <bean.getLength(); i++){
                Node beanNode = bean.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class controllerBeanClass =  Class.forName(className);
                    Object beanObj = controllerBeanClass.newInstance();
                    Method setServletContextMethod = controllerBeanClass.getDeclaredMethod("setServletContext",
                            ServletContext.class);
                    setServletContextMethod.invoke(beanObj,this.getServletContext())
;                    Field servletContext = controllerBeanClass.getDeclaredField("servletContext");
                    servletContext.setAccessible(true);
                    servletContext.set(beanObj, this.getServletContext());

                    beanMap.put(beanId,beanObj);

                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public DispatcherServlet(){}



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

//        从 url 获取到 fruit
//        如：http://localhost:8080/fruit/fruit.do
//        获取到 ：/hello.do
//        然后获取到 hello
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj = beanMap.get(servletPath);

        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)){
            operate = "index";
        }

        try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(controllerBeanObj, req,resp);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}