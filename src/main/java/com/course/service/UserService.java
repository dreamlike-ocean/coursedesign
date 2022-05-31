package com.course.service;

import com.course.dao.UserMapper;
import com.course.event.AccessScoreEvent;
import com.course.event.FillInformationScoreEvent;
import com.course.pojo.LoginUser;
import com.course.utils.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import com.course.event.EventBus;
import org.springframework.stereotype.Service;

import static com.course.configuration.WebConfig.USER_CONTEXT;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EventBus EventBus;

    /**
     * @author dreamlike
     * @param loginUser from request
     * @return login from db
     * @exception AuthenticationException 如果验证失败
     */
    public LoginUser Login(LoginUser loginUser){
        var user = userMapper.selectByUsername(loginUser.getUsername());
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) throw new AuthenticationException();
        EventBus.publishEvent(new AccessScoreEvent(user));
        return user;
    }


    public int fillInformation(String information){
        LoginUser user = USER_CONTEXT.get();
        int i = userMapper.updateInformation(user.getUserId(), information);
        boolean isFirst = user.getInformation() == null;
        user.setInformation(information);
        EventBus.publishEvent(new FillInformationScoreEvent(user, isFirst));
        return i;
    }





}
