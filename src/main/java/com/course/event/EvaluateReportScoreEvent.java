package com.course.event;

import com.course.pojo.LoginUser;
import com.course.service.score.BfzScoreStrategy;
import com.course.service.score.EvaluateReportScoreStrategy;

public class EvaluateReportScoreEvent extends BaseScoreEvent{

    public EvaluateReportScoreEvent(LoginUser loginUser) {
        super(loginUser);
    }

    @Override
    public int strategyType() {
        return EvaluateReportScoreStrategy.type;
    }
}
