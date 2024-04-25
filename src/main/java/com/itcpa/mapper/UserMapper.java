package com.itcpa.mapper;

import com.itcpa.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    /**
     * 查看用户名是否重复
     * @param userName
     * @return
     */
    @Select("select * from user where username = #{userName}")
    User findUserName(String userName);

    /**
     * 用户添加
     * @param userName
     * @param password
     * @return
     */
    @Insert("insert into user(username,password,create_time,update_time) values(#{userName},#{password},now(),now())")
    int registerUser(String userName, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id = #{id}")
    void update(User user);
}
