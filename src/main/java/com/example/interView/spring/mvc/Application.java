package com.example.interView.spring.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * springboot 依旧兼容 servlet3.0 一系列以 @Web* 开头的注解：@WebServlet，@WebFilter，@WebListener
 * springboot通过TomcatStarter 进行初始化
 * 部署时两种选择：1.打成 jar 包，使用 java -jar 的方式运行(--->会导致容器搜索算法出现问题)
 *                  --->替代选项：ServletContextInitializer
 *                2.打成 war 包，交给外置容器去运行
 * ServletContextInitializer：spring 中 ServletContainerInitializer 的代理
 */
@SpringBootApplication
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
