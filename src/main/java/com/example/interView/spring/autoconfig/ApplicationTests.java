package com.example.interView.spring.autoconfig;

import com.example.zhangjiespringbootstarter.service.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    private ExampleService exampleService;

    @Test
    public void testStarter() {
        System.out.println(exampleService.wrap("hello"));
    }
}
