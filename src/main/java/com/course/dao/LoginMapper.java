package com.course.dao;

import com.course.pojo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper {
    @Select("select * from login_user where  username = #{username}")
    LoginUser selectByUsername(String username);

}
