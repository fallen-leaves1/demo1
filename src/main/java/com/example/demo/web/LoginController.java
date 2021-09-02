package com.example.demo.web;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Resource
    UserService userService;

    //登录功能
    @ResponseBody
    @PostMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestParam("username") String username, @RequestParam("password") String password)  {
        HttpSession session=request.getSession();
        Result result = userService.login(new User(username,password));
        if (result.getData()!=null) {
            session.setAttribute("user",result.getData());
        }
        return result;
    }

    //注销
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        if (session.getAttribute("user")!=null) {
            session.removeAttribute("user");
            System.out.println("用户已退出");
        }
        response.sendRedirect("login.html");
    }
}
