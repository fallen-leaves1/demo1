package com.example.demo.service;

import com.example.demo.basic.Result;
import com.example.demo.repository.dao.UserLend;
import com.example.demo.request.Lendreq;
import org.springframework.lang.Nullable;

import javax.annotation.Resource;

public interface LendService {
    Result lend_book(Lendreq lendreq);
    Result back_book(Lendreq lendreq);
    Result lend_info(String username);
}
