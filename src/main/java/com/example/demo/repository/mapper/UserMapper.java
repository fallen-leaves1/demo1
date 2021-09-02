package com.example.demo.repository.mapper;

import com.example.demo.repository.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    String selectPwd(@Param("username") String username);
}
