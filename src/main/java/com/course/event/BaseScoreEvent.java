package com.course.event;

import com.course.pojo.LoginUser;
import lombok.Getter;

@Getter
public abstract class BaseScoreEvent {

    protected final Long timeStamp;
    protected final LoginUser loginUser;

    public BaseScoreEvent(LoginUser loginUser) {
        this.timeStamp = System.currentTimeMillis();
        this.loginUser = loginUser;
    }
    public abstract int strategyType();
}
