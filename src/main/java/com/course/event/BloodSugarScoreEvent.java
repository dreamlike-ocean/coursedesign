package com.course.event;

import com.course.pojo.LoginUser;
import com.course.service.score.BloodSugarScoreStrategy;

public class BloodSugarScoreEvent extends BaseScoreEvent{

    public BloodSugarScoreEvent(LoginUser loginUser) {
        super(loginUser);
    }

    @Override
    public int strategyType() {
        return BloodSugarScoreStrategy.type;
    }
}
