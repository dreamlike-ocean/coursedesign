package com.course;


import com.course.dao.UserMapper;
import com.course.pojo.LoginUser;
import com.course.service.UserService;
import com.course.utils.AuthenticationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class loginTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;
    @BeforeEach
    public void flushDB(){
        jdbcTemplate.update("delete from score_record");
    }



    @Test
    @Rollback
    public void testLoginMapper(){
        //language=sql
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        assertEquals(userMapper.selectByUsername("test_username").getPassword(), "p1");
        assertNull(userMapper.selectByUsername(""));
    }


    @Test
    @Rollback
    public void testLoginScore(){
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        //测试：错误账号应该抛出异常
        assertThrows(AuthenticationException.class, () -> userService.Login(new LoginUser("test_username","p2")));
        assertThrows(AuthenticationException.class, () -> userService.Login(new LoginUser("test_userna","p2")));

        LoginUser user  = userService.Login(new LoginUser("test_username", "p1"));
        user = userService.Login(user);
        ArrayList<Integer> counts = new ArrayList<>();
        jdbcTemplate.query("select * from score_record where user_id = ?",(rs)->{
            counts.add(rs.getInt("count"));
        },user.getUserId());
        assertEquals(1, counts.size(),"重复登录应该只存在一个记录");
        assertEquals(1,counts.get(0) , "当日登录只加一分");

    }

    @Test
    @Rollback
    public void testFillInformationScore(){
        jdbcTemplate.update("insert into login_user (username, password) VALUES ('test_username','p1')");
        LoginUser user  = userService.Login(new LoginUser("test_username", "p1"));
        TestUtil.setUser(user);
        String fill = "test";
        userService.fillInformation(fill);

        assertEquals(fill,user.getInformation(),"当前上下文中的用户应被修改填充");
        jdbcTemplate.query("select information from login_user where user_id = ?",(rs)->{
            assertEquals(fill,rs.getString("information"), "数据库中的用户应被修改填充");
        },user.getUserId());

        userService.fillInformation(fill);

        ArrayList<Integer> counts = new ArrayList<>();
        jdbcTemplate.query("select * from score_record where user_id = ?",(rs)->{
            //因为登录还有个积分记录 过滤掉
            if (rs.getInt("type") == 1) {
                counts.add(rs.getInt("count"));
            }
        },user.getUserId());

        assertEquals(1, counts.size(),"重复填充应该只存在一个记录");
        assertEquals(2,counts.get(0) , "填充只加两分");


        TestUtil.removeUser();
    }
}
