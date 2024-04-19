package com.itcpa.service.impl;

import com.itcpa.mapper.UserMapper;
import com.itcpa.pojo.User;
import com.itcpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户名是否存在
     * @param userName
     */
    @Override
    public User findByName(String userName) {
        return userMapper.findUserName(userName);
    }

    /**
     * 用户注册
     *
     * @param userName
     * @param password
     */
    @Override
    public void registerUser(String userName, String password) {
        userMapper.registerUser(userName, password);
    }
}
