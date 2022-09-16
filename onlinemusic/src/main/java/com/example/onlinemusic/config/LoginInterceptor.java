package com.example.onlinemusic.config;

import com.example.onlinemusic.tools.Constant;
import com.example.onlinemusic.tools.ResponseBodyMessage;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// interceptor to prevent loading pages without login first
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null || httpSession.getAttribute(Constant.USERINFO_SESSION_KEY)  != null) {
            System.out.println("Haven't logged in.");
            return true;
        }
        return false;
    }
}
