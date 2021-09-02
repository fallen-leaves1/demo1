package com.example.demo.request;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class Bookreq {

    private String bookname;
    private String author;
    private Integer num;

    public boolean hasIllegalField() {
        return !StringUtils.hasLength(bookname) || !StringUtils.hasLength(author) || num==null || num<=0;
    }
}
