package com.example.demo.service;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.User;

public interface UserService {
    Result login(User user);
}
