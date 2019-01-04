package com.example.interView.spring.mvc;

import com.example.interView.spring.mvc.filter.HelloWorldFilter;
import com.example.interView.spring.mvc.servlet.HelloWorldServlet;
import org.springframework.boot.web.servlet.ServletContextInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * ServletCantainerInitializer 不能被内嵌容器加载
 * ServletContextInitializer 却能被 springboot 的 EmbeddedWebApplicationContext 加载到，从而装配其中的 servlet 和 filter
 */
public class CustomServletContextInitializer implements ServletContextInitializer {

    private final static String JAR_HELLO_URL = "/hello";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("创建 helloWorldServlet...");

        ServletRegistration.Dynamic servlet = servletContext.addServlet(
                HelloWorldServlet.class.getSimpleName(),
                HelloWorldServlet.class);
        servlet.addMapping(JAR_HELLO_URL);

        System.out.println("创建 helloWorldFilter...");

        FilterRegistration.Dynamic filter = servletContext.addFilter(
                HelloWorldFilter.class.getSimpleName(), HelloWorldFilter.class);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        dispatcherTypes.add(DispatcherType.REQUEST);
        dispatcherTypes.add(DispatcherType.FORWARD);

        filter.addMappingForUrlPatterns(dispatcherTypes, true, JAR_HELLO_URL);
    }
}
