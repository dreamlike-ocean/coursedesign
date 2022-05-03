package com.course;

import com.course.dao.ScoreMapper;
import com.course.pojo.ScoreRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ScoreTest {
    @Autowired
    ScoreMapper scoreMapper;
    @Test
    public void testInsert(){
        System.out.println(LocalDateTime.now().minusYears(-1));
    }
}
