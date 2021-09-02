package com.example.demo.service.impl;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.User;
import com.example.demo.repository.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import static com.example.demo.basic.Errors.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Result login(User user) {
        if(user.getUsername()==""||user.getPassword()=="") {return Result.fail(INVALID_PARAMS);}
        String pwd=userMapper.selectPwd(user.getUsername());
        if (pwd!=null&&pwd.equals(user.getPassword())) {
            System.out.println("账号密码正确");
            return Result.ok(user.getUsername());//返回的data中含有用户姓名
        }
        System.out.println("用户不存在或者密码错误");
        return Result.fail(RECORD_NOT_EXISTS);
    }
}
