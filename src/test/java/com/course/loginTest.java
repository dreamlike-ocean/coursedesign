package com.course;


import com.course.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class loginTest {
    @Autowired
    UserMapper userMapper;


    @Test
    public void testLoginMapper(){
        assertEquals(userMapper.selectByUsername("username").getPassword(), "p1");
        assertNull(userMapper.selectByUsername(""));

    }
    @Test
    public void test(){
        System.out.println(LocalDateTime.now().withDayOfMonth(1).withSecond(0).withHour(0).withMinute(0).withSecond(0));
    }
}
