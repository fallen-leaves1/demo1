package com.example.demo.basic;

public enum Errors {
    INVALID_PARAMS(1, "传入参数不合法"),
    REPEAT_DATA(2, "数据重复"),
    RECORD_NOT_EXISTS(3, "记录不存在"),
    RECORD_LIMITED(4, "库存不足"),
    SPECIAL_ERROR(5,"该记录不可删除"),
    SERVER_ERROR(500, "服务器发生错误");

    private int code;
    private String message;

    Errors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
