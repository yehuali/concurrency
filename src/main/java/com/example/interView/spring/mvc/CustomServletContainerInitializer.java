package com.example.interView.spring.mvc;

import com.example.interView.spring.mvc.filter.HelloWorldFilter;
import com.example.interView.spring.mvc.servlet.HelloWorldServlet;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.Set;

/**
 *  ServletContext 新增的方法要么是在 ServletContextListener 的 contexInitialized 方法中调用，要么是在 ServletContainerInitializer 的 onStartup() 方法中调用
 *
 * ServletContainerInitializer 也是 Servlet 3.0 新增的一个接口
 * 容器在启动时使用 JAR 服务 API(JAR Service API) 来发现 ServletContainerInitializer 的实现类，并且容器将 WEB-INF/lib 目录下 JAR 包中的类都交给该类的 onStartup() 方法处理
 * 在该实现类上使用 @HandlesTypes 注解来指定希望被处理的类
 *
 * ServletContext:维护了整个 web 容器中注册的 servlet，filter，listener
 *
 * Spring通过SpringServletContainerInitializer支持servlet3.0
 * --->委托给WebApplicationInitializer对servlet和filter进行注册
 */
public class CustomServletContainerInitializer implements ServletContainerInitializer {

    private final static String JAR_HELLO_URL = "/hello";

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
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
