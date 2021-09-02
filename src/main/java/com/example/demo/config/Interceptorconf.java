package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//@Component与@Configuration区别:component中的bean是多例模式,而configuration是单例模式
@Component
public class Interceptorconf implements WebMvcConfigurer {
    @Resource
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(myInterceptor);//一个InterceptorRegistration只能注册一个拦截器?
        //通过InterceptorRegistration类不仅能添加拦截路径，也能添加不拦截路径
        registration.addPathPatterns("/**").excludePathPatterns("/dev/**");
    }
}
