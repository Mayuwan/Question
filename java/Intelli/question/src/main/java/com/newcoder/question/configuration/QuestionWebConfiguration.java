package com.newcoder.question.configuration;

import com.newcoder.question.interceptor.LoginRequiredInterceptor;
import com.newcoder.question.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class QuestionWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");//当访问/user/*页面时访问该拦截器
        super.addInterceptors(registry);
    }
}
