package com.course.service;

import com.course.dao.ScoreMapper;
import com.course.event.*;
import com.course.pojo.ScoreRecord;
import com.course.service.score.ScoreStrategy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    private final ScoreStrategy[] scoreStrategies;
    private final ScoreMapper scoreMapper;

    public ScoreService(List<ScoreStrategy> scoreStrategies, ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
        int max = -1;
        for (ScoreStrategy strategy : scoreStrategies) {
            max = Math.max(max, strategy.type());
        }
        this.scoreStrategies = new ScoreStrategy[max];
        for (ScoreStrategy strategy : scoreStrategies) {
            if (this.scoreStrategies[strategy.type()] != null) {
                throw new RuntimeException("存在策略类型(int)重复");
            }
            this.scoreStrategies[strategy.type()] = strategy;
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




}
