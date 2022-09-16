package com.example.onlinemusic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * add interceptor (LoginInterceptor class)
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder getBcrypPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 配置拦截规则，需要登录以后，才能访问其他页面
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")                 // 拦截所有url，只有登录后，才允许访问
                .excludePathPatterns("/css/**.css")     // 但排除以下文件夹下面的文件，没有登录，是可以访问的页面
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**.js")
                .excludePathPatterns("/player/**")
                .excludePathPatterns("/login.html")     // 排除login.html，这个页面是可以访问的
                .excludePathPatterns("/user/login");    // 排除登录接口
    }
}
