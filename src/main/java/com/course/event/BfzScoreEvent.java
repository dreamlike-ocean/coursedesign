package com.course.event;

import com.course.pojo.LoginUser;
import com.course.service.score.BfzScoreStrategy;
import com.course.service.score.BloodSugarScoreStrategy;

public class BfzScoreEvent extends BaseScoreEvent{

    public BfzScoreEvent(LoginUser loginUser) {
        super(loginUser);
    }

    @Override
    public int strategyType() {
        return BfzScoreStrategy.type;
    }
}
