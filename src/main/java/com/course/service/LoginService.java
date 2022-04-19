package com.course.service;

import com.course.dao.LoginMapper;
import com.course.pojo.LoginUser;
import com.course.utils.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lixuy
 * Created on 2019-04-11
 */
//类名与方法名须与controller层拦截的方法一致
@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    /**
     * @author dreamlike
     * @param loginUser from request
     * @return login from db
     * @exception AuthenticationException 如果验证失败
     */
    public LoginUser Login(LoginUser loginUser){
        var user = loginMapper.selectByUsername(loginUser.getUsername());
        if (user == null || !user.getPassword().equals(loginUser.getPassword())) throw new AuthenticationException();
        return user;
    }




}
