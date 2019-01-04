package com.example.interView.spring.mvc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * https://blog.csdn.net/ltwang_tech/article/details/67641888
 * ServletContextListener接口，用来监听Servlet
 */
@WebListener
public class PmsServletContextListener implements ServletContextListener {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        logger.info("liting: contextDestroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
        logger.info("liting: contextInitialized");
    }

}
