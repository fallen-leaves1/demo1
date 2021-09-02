package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("uri:"+request.getRequestURI());
        if (request.getRequestURI().contains("login")) {
            System.out.println("进入登录操作");
            return true;
        }
        HttpSession httpSession=request.getSession();
        if (httpSession.getAttribute("user")!=null) {
            System.out.println("hello:"+httpSession.getAttribute("user"));
            return true;
        } else {
            System.out.println("你还没登录");
            //request.getRequestDispatcher("/login.html");
            response.sendRedirect("login.html");
        }
        return false;
    }

}
