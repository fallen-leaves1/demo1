package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lendreq {

    private String username;
    private String bookname;
    private Integer num;

    public boolean hasIllegalField() {
        return !StringUtils.hasLength(username) || !StringUtils.hasLength(bookname) || num==null || num<=0;
    }
}
