package com.example.onlinemusic.controller;

import com.example.onlinemusic.model.User;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hhhh")
public class TestController {

    @RequestMapping("/test1")
    public ResponseBodyMessage<User> login(@RequestParam String username,
                                           @RequestParam String password) {
        System.out.println("username" + username);
        System.out.println("password" + password);

        User user = new User();

        if(username.equals("bit") && password.equals("123456")) {
            user.setUsername("bit");
            user.setPassword("123456");

            return new ResponseBodyMessage<>(0, "Successful login.", user);
        }else{
            return new ResponseBodyMessage<>(-1, "Failed to login.", null);
        }
    }
}
