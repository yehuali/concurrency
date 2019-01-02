package com.example.interView.spring.autoconfig;

import com.example.zhangjiespringbootstarter.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TimeUtilsStarterController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping("/time/greet")
    public String gainNowTime() {
       return exampleService.wrap("zhangjie");
    }
}
