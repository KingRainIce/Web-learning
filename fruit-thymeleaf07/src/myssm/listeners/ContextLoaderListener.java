package myssm.listeners;

import myssm.io.BeanFactory;
import myssm.io.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @title:ContextLoaderListener
 * @Author Ice
 * @Date: 2022/9/22 18:44
 * @Version 1.0
 */

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        ServletContext application = servletContextEvent.getServletContext();
        application.setAttribute("beanFactory", beanFactory);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}