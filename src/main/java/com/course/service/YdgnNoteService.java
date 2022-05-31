package com.course.service;

import com.course.event.YdgnScoreEvent;
import org.springframework.beans.factory.annotation.Autowired;
import com.course.event.EventBus;
import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法 胰岛功能
@Service
public class YdgnNoteService {
    @Autowired
    EventBus EventBus;

    public void ydgnNote(){
        System.out.println("+++++ydgnNote积分计算方法执行+++++");
        EventBus.publishEvent(new YdgnScoreEvent(USER_CONTEXT.get()));
    }

}
