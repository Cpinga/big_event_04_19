package com.itcpa.controller;

import com.itcpa.pojo.Result;
import com.itcpa.pojo.User;
import com.itcpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public Result register(String username, String password) {
        User user = userService.findByName(username);
        //判断用户名称是否为空
        if (user == null) {
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            userService.registerUser(username, md5Password);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }
}
