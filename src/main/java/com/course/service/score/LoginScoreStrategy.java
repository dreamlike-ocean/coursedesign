package com.course.service.score;

import com.course.dao.ScoreMapper;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginScoreStrategy implements ScoreStrategy {
    public static final int type = 0;
    @Autowired
    ScoreMapper scoreMapper;
    //可以做成定时的lru那种 需求简单不做了
    private ConcurrentHashMap<Integer, Long> lastGet = new ConcurrentHashMap<>();

    @Override
    public int type() {
        return type;
    }

    @Override
    public ScoreRecord record(LoginUser loginUser, Map<?, ?> context) {
        long start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).toEpochSecond(ZoneOffset.ofHours(8));
        long now = System.currentTimeMillis();
        Long last = lastGet.get(loginUser.getUserId());
        if (last != null) {
            //fast path 当天登录
            if (last <= end && last >= start) return null;
            else {
                lastGet.put(loginUser.getUserId(), now);
                return new ScoreRecord(loginUser.getUserId(), 1, type());
            }
        }

        int count = scoreMapper.count(loginUser.getUserId(), type(), start, end);
        //缓存失效 看看db
        lastGet.put(loginUser.getUserId(), now);
        //db 内无数据 则插入
        if (count == 0) {
            return new ScoreRecord(loginUser.getUserId(), 1, now, type());
        } else {
            return null;
        }
    }
}
