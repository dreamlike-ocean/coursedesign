package com.course.event;

import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.LoginScoreStrategy;
import com.sun.jdi.connect.spi.TransportService;
import lombok.Getter;


@Getter
public class AccessScoreEvent extends BaseScoreEvent {

    public AccessScoreEvent(LoginUser loginUser) {
       super(loginUser);
    }

    @Override
    public int strategyType() {
        return LoginScoreStrategy.type;
    }
}