package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.*;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ScoreStrategy;
import com.course.service.score.UnstableScoreStrategy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    private final ScoreStrategy[] scoreStrategies;
    private final ScoreMapper scoreMapper;
    private final List<Integer> chengzhang = new ArrayList<>();
    private final List<Integer> duihuan = new ArrayList<>();

    public ScoreService(List<ScoreStrategy> scoreStrategies, ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
        int max = -1;
        for (ScoreStrategy strategy : scoreStrategies) {
            max = Math.max(max, strategy.type());
        }
        this.scoreStrategies = new ScoreStrategy[max+1];
        for (ScoreStrategy strategy : scoreStrategies) {
            if (this.scoreStrategies[strategy.type()] != null) {
                throw new RuntimeException("存在策略类型(int)重复");
            }
            this.scoreStrategies[strategy.type()] = strategy;
            if (strategy instanceof UnstableScoreStrategy) {
                duihuan.addAll(((UnstableScoreStrategy) strategy).types());
            }else {
                chengzhang.add(strategy.type());
            }
         }
    }

    @EventListener
    public void loginScore(AccessScoreEvent accessEvent){
        ScoreRecord scoreRecord = scoreStrategies[accessEvent.strategyType()].record(accessEvent.getLoginUser(), Map.of());
        if(scoreRecord == null) return;
        scoreMapper.insertRecord(scoreRecord);
    }
    @EventListener
    public void fillInformationScore(FillInformationScoreEvent event){
        ScoreRecord scoreRecord = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of("isFirst", event.isFirst()));
        if(scoreRecord == null) return;
        scoreMapper.insertRecord(scoreRecord);
    }

    @EventListener
    public void bloodSugarScore(BloodSugarScoreEvent event){
        ScoreRecord record = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }
    @EventListener
    public void bfzScore(BfzScoreEvent event){
        ScoreRecord record = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }
    @EventListener
    public void evaluateReportScore(EvaluateReportScoreEvent event){
        ScoreRecord record = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }

    @EventListener
    public void ydgnScore(YdgnScoreEvent event){
        ScoreRecord record = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of());
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }


    @EventListener
    public void ConvertibleScore(ConvertibleScoreEvent event){
        ScoreRecord record = scoreStrategies[event.strategyType()].record(event.getLoginUser(), Map.of("score",event.getScore(),"type",event.getTrueType()));
        if(record == null) return;
        scoreMapper.insertRecord(record);
    }



    public char calChengZhangeScoreByMonth(LoginUser loginUser){
        long start = LocalDateTime.now().withDayOfMonth(1).withSecond(0).withHour(0).withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        int sum = scoreMapper.sum(loginUser.getUserId(), start, end, chengzhang);
        if (sum<=10) return 'C';
        if (sum<=25) return 'B';
        return 'A';
    }

    public int calDuihuanScore(LoginUser loginUser){
        long start = LocalDateTime.now().minusYears(1).toEpochSecond(ZoneOffset.ofHours(8));
        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        return scoreMapper.sum(loginUser.getUserId(), start, end, chengzhang);
    }
}
