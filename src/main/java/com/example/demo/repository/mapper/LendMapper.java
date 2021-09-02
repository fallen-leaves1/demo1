package com.example.demo.repository.mapper;

import com.example.demo.repository.dao.UserLend;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendMapper {
    List<UserLend> lendinfo(String username);
    int lend_exist(String bookname);
    int lendbook(@Param("userlend") UserLend userLend);
    int backbook(@Param("userlend") UserLend userLend);
}
