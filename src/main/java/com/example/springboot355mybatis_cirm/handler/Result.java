package com.example.springboot355mybatis_cirm.handler;

import lombok.Data;

@Data
public class Result <T> {
    // 响应业务状态
    private int code;
    // 响应消息
    private String message;
    // 响应中的数据
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(T data) {
        this.code = 200;
        this.message = "ok";
        this.data = data;
    }

    public static <T> Result<T> fail(int code, String message){
        return new Result<T>(code,message,null);
    }

    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static <T> Result<T> error(int code ,T data){
        return new Result<T>(code,"--error--",data);
    }
}