package com.example.onlinemusic.controller;

import com.example.onlinemusic.mapper.UserMapper;
import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 用户相关的登录
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/login1")
    public ResponseBodyMessage<User> login1(@RequestParam String username,
                                            @RequestParam String password,
                                            HttpServletRequest request) {
        User userLogin = new User();
        userLogin.setUsername(username);
        userLogin.setPassword(password);

        User user = userMapper.login(userLogin);

        if(user == null) {
            System.out.println("Login is failed.");
            return new ResponseBodyMessage<>(-1, "Login is failed", userLogin);
        } else {
            // 可以优化为，登录成功后，把用户存到session里面. 通过UserInfo_Session_Key来获取user的value
            // USERINFO_SESSION_KEY is defined in Contant class to avoid spelling mistakes.
            // 项目优化的方式，用常量来获取
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY, user);
            return new ResponseBodyMessage<>(0, "Login is successful", userLogin);
        }
    }

    // BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @RequestMapping("/login")
    public ResponseBodyMessage<User> login(@RequestParam String username, @RequestParam String password,
                                           HttpServletRequest request) {
//        User userLogin = new User();
//        userLogin.setUsername(username);
//        userLogin.setPassword(password);
//
//        User user = userMapper.login(userLogin);
        User user = userMapper.selectByName(username);

        if(user == null) {
            System.out.println("Login is failed.");
            return new ResponseBodyMessage<>(-1, "Login is failed", user);
        } else {
            boolean flag = bCryptPasswordEncoder.matches(password, user.getPassword());
            // 查询到的用户的密码跟输入的密码是否匹配
            if (!flag) {
                // 不匹配，登录失败
                return new ResponseBodyMessage<>(-1, "Login is failed. Incorrect username or password", user);
            }
            request.getSession().setAttribute(Constant.USERINFO_SESSION_KEY, user);
            return new ResponseBodyMessage<>(0, "Login is successful", user);
        }
    }
}
