package com.course.service;

import com.course.dao.BloodSugarRecordMapper;
import com.course.event.EvaluateReportScoreEvent;
import com.course.event.EventBus;
import com.course.pojo.LoginUser;
import com.course.utils.NostackException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import static com.course.configuration.InterceptorConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class EvaluateReportService {
    @Autowired
    private EventBus EventBus;
    @Autowired //设计有误 不应该这样直接取
    BloodSugarRecordMapper bloodSugarRecordMapper;

    public String evaluateReport(){
        LoginUser user = USER_CONTEXT.get();
        if (user.getInformation() !=null &&bloodSugarRecordMapper.countByUserId(user.getUserId()) >=10) {
            EventBus.publishEvent(new EvaluateReportScoreEvent(user));
            return "你的报告";
        }
        throw new NostackException("条件不足，无法生成报告");
    }

}
