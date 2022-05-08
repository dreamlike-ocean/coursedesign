package com.course;

import com.course.dao.ScoreMapper;
import com.course.event.AccessScoreEvent;
import com.course.event.ConvertibleScoreEvent;
import com.course.pojo.LoginUser;
import com.course.pojo.ScoreRecord;
import com.course.service.BfzNoteService;
import com.course.service.ScoreService;
import com.course.service.UserService;
import com.course.service.score.BloodSugarScoreStrategy;
import com.course.service.score.LoginScoreStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.course.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ScoreTest {
    @Autowired
    ScoreMapper scoreMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ScoreService scoreService;
    @Autowired
    UserService userService;
    LoginUser loginUser;
    @BeforeEach
    public void initEachUser(){
        //https://stackoverflow.com/questions/58657587/beforeall-and-transaction-are-not-working-changes-on-db-side-are-not-rollbac
        if (loginUser == null) {
            loginUser = creatTestUser();
        }
        setUser(loginUser);
    }
    @Test
    @Rollback
    public void testChengzhang(){
        ArrayList<ScoreRecord> records = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            records.add(new ScoreRecord(getUser().getUserId(), 1, BloodSugarScoreStrategy.type));
        }
        scoreMapper.insertRecords(records);
        //目前是5分应该是c
        assertEquals('C',scoreService.calChengZhangeScoreByMonth(getUser()));

        records = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            records.add(new ScoreRecord(getUser().getUserId(), 3, BloodSugarScoreStrategy.type));
        }
        ScoreRecord record = new ScoreRecord(getUser().getUserId(), 1000, LoginScoreStrategy.type);
        //插入一条早于一个月的记录 应该不造成影响
        record.setTimestamp(LocalDateTime.now().plusMonths(-1).toEpochSecond(ZoneOffset.ofHours(8)));
        records.add(record);
        scoreMapper.insertRecords(records);
        //目前是17分应该是B
        assertEquals('B',scoreService.calChengZhangeScoreByMonth(getUser()));

        records = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            records.add(new ScoreRecord(getUser().getUserId(), 3, BloodSugarScoreStrategy.type));
        }
        scoreMapper.insertRecords(records);
        assertEquals('A',scoreService.calChengZhangeScoreByMonth(getUser()));
    }

    @Test
    @Rollback
    public void testDuihuan(){
        ArrayList<ScoreRecord> records = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            records.add(new ScoreRecord(getUser().getUserId(), 3, ConvertibleScoreEvent.ConvertibleScore.FollowUp.getTrueType()));
        }
        for (int i = 0; i < 5; i++) {
            ScoreRecord record = new ScoreRecord(getUser().getUserId(), 3, ConvertibleScoreEvent.ConvertibleScore.FollowUp.getTrueType());
            //超过一年的积分
            record.setTimestamp(LocalDateTime.now().minusYears(1).minusMonths(1).toEpochSecond(ZoneOffset.ofHours(8)));
            records.add(record);
            // 成长积分
            records.add(new ScoreRecord(getUser().getUserId(), 1, BloodSugarScoreStrategy.type));
        }
        scoreMapper.insertRecords(records);
        int i = scoreService.calDuihuanScore(getUser());
        assertEquals(15, i);
    }

    private LoginUser creatTestUser() {
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        return userService.Login(new LoginUser("test_username", "p1"));
    }
}
