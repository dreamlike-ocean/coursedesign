package com.course.event;

import com.course.pojo.LoginUser;
import com.course.service.score.FillInformationScoreStrategy;
import lombok.Getter;

@Getter
public class FillInformationScoreEvent extends BaseScoreEvent {
    private final boolean isFirst;

    public FillInformationScoreEvent(LoginUser loginUser, boolean isFirst) {
        super(loginUser);
        this.isFirst = isFirst;
    }

    @Override
    public int strategyType() {
        return FillInformationScoreStrategy.type;
    }
}
