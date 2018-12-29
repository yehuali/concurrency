package com.example.interView.spring.autoconfig;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TimeUtilsStarterController {

    @Autowired
    private TimeUtils timeUtils;

    @RequestMapping("/time/utils/gain-now-time.htm")
    public String gainNowTime() {
        return timeUtils.generateCreateTimeText(new DateTime("2017-02-22T15:30:30").toDate());
    }
}
