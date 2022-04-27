package com.course;

import com.course.pojo.LoginUser;

import static com.course.configuration.WebConfig.USER_CONTEXT;

public class TestUtil {

    public static void setUser(LoginUser u){
        USER_CONTEXT.set(u);
    }

    public static void removeUser(){
        USER_CONTEXT.remove();
    }
}
