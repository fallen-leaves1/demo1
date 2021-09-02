package com.example.demo.basic;

import lombok.Data;

@Data
public class Result {

    private int  code;
    private String message;
    private Object data;

    public static Result OK=new Result(0,null,null);

    private Result(int code,String message,Object data) {
        this.code=code;
        this.message=message;
        this.data=data;
    }

    public static Result ok(Object data) {
        return new Result(0, null, data);
    }

    public static Result fail(Errors errors) {
        return new Result(errors.getCode(), errors.getMessage(), null);
    }

    public static Result fail(Errors errors, String message) {
        return new Result(errors.getCode(), message, null);
    }
}
