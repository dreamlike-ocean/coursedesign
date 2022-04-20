package com.course;

import com.course.dao.ScoreMapper;
import com.course.pojo.ScoreRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ScoreTest {
    @Autowired
    ScoreMapper scoreMapper;
    @Test
    public void testInsert(){
        ScoreRecord record = new ScoreRecord(32, 1,1);
        int affect = scoreMapper.insertRecord(record);
        assertEquals(affect,1);
    }
}
