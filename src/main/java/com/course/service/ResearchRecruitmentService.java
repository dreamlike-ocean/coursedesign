package com.course.service;

import com.course.event.ConvertibleScoreEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import static com.course.configuration.WebConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class ResearchRecruitmentService {
    @Autowired
    private ApplicationContext applicationContext;

    public void researchRecruitment(){
        System.out.println("+++++researchRecruitment积分计算方法执行+++++");
        applicationContext.publishEvent(new ConvertibleScoreEvent(USER_CONTEXT.get(), ConvertibleScoreEvent.ConvertibleScore.ResearchRecruitment));
    }

}
