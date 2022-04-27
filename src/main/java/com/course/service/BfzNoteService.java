package com.course.service;

import com.course.dao.BfzMapper;
import com.course.event.BfzScoreEvent;
import com.course.pojo.BfzRecord;
import com.course.pojo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static com.course.configuration.WebConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11 并发症
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class BfzNoteService {
    @Autowired
    private BfzMapper bfzMapper;
    @Autowired
    ApplicationContext applicationContext;

    public void bfzNote(String bfz){
        LoginUser user = USER_CONTEXT.get();
        bfzMapper.insertIntoRecord(new BfzRecord(user.getUserId(), bfz));
        applicationContext.publishEvent(new BfzScoreEvent(user));
    }

}
