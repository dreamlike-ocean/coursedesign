package com.course.service;

import com.course.dao.BloodSugarRecordMapper;
import com.course.event.BloodSugarScoreEvent;

import com.course.event.EventBus;
import com.course.pojo.BloodSugarRecord;
import com.course.pojo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class BloodSugarService {
    @Autowired
    BloodSugarRecordMapper bloodSugarRecordMapper;
    @Autowired
    EventBus eventBus;

    public void recordBloodSugar(String record){
        LoginUser user = USER_CONTEXT.get();
        BloodSugarRecord sugarRecord = new BloodSugarRecord(user.getUserId(), record);
        bloodSugarRecordMapper.insertIntoRecord(sugarRecord);
        eventBus.publishEvent(new BloodSugarScoreEvent(user));
    }

}
