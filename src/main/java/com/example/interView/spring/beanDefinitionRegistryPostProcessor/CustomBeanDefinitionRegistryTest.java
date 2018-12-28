package com.example.interView.spring.beanDefinitionRegistryPostProcessor;

import com.example.interView.spring.bean.Demo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={SpringConfiguration.class})
public class CustomBeanDefinitionRegistryTest {
//    @Autowired
//    private Hello hello;
//
//    @Test
//    public void test() {
//        //能运行就说明hello是不为空的
//        Assert.assertNotNull(hello);
//        hello.sayHello();
//    }

    @Autowired
    private Demo test;

    @Test
    public void test() {
        //能运行就说明hello是不为空的
        Assert.assertNotNull(test);
        test.demoTest();
    }
}
