package com.course;


import com.course.dao.LoginMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class loginTest {
    @Autowired
    LoginMapper loginMapper;


    @Test
    public void testLoginMapper(){
        assertEquals(loginMapper.selectByUsername("username").getPassword(), "p1");
        assertNull(loginMapper.selectByUsername(""));

    }
}
