package com.itcpa.service;


import com.itcpa.pojo.User;

public interface UserService {
    /**
     * 查询用户名是否存在
     * @param userName
     */
    User findByName(String userName);

    /**
     * 用户注册
     * @param userName
     * @param password
     */
    void registerUser(String userName, String password);
}
