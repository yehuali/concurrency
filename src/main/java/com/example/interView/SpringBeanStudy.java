package com.example.interView;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring生命周期
 * 1.BeanFactory:负责Bean的整个生命周期的管理
 */
@Component
public class SpringBeanStudy {
    public String sayHello(){
        return "Hello world!";
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringBeanStudy helloService = context.getBean(SpringBeanStudy.class);
        System.out.println(helloService.sayHello());
    }
}
