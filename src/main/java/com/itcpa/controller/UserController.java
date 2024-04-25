package com.itcpa.controller;

import com.itcpa.pojo.Result;
import com.itcpa.pojo.User;
import com.itcpa.service.UserService;
import com.itcpa.utils.JwtUtil;
import com.itcpa.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
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

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        User loginUser = userService.findByName(username);

        //判断用户是否存在
        if (loginUser == null) {
            return Result.error("用户名或密码错误");
        }

        //将从数据库获取的密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (md5Password.equals(loginUser.getPassword())) {
            Map<String, Object> clamis = new HashMap<>();
            clamis.put("id", loginUser.getId());
            clamis.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(clamis);
            return Result.success(token);
        }
        return Result.error("账号或密码错误");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("userInfo")
    public Result<User> userInfo() {
        // 获取登录用户的线程
        Map<String, Object> map = ThreadLocalUtil.getThreadLocal();

        // 在线程中获取用户名
        String username = (String) map.get("username");

        return Result.success(userService.findByName(username));
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping("updateUser")
    public Result updateUserInfo(@RequestBody User user) {
        userService.update(user);
        return Result.success();
    }
}
