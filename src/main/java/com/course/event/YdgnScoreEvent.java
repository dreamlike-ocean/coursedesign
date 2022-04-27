package com.course.event;

import com.course.pojo.LoginUser;
import com.course.service.score.FillInformationScoreStrategy;
import com.course.service.score.YdgnScoreStrategy;
import lombok.Getter;

@Getter
public class YdgnScoreEvent extends BaseScoreEvent {

    public YdgnScoreEvent(LoginUser loginUser) {
        super(loginUser);
    }

    @Override
    public int strategyType() {
        return YdgnScoreStrategy.type;
    }
}
