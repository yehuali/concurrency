package com.example.interView.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ApplicationContext Bean生命周期
 * 参考：https://www.jianshu.com/p/3944792a5fff
 */
public class SpringBeanStudy {

    public static void main(String[] args) {
        System.out.println("开始初始化容器");
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("xml加载完毕");
        Person person1 = (Person) ac.getBean("person1");
        System.out.println(person1);
        System.out.println("关闭容器");
        ((ClassPathXmlApplicationContext)ac).close();

    }
}
