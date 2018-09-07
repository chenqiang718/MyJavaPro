package com.cq.springboot.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author mr
 * @Date: 2018/9/5 11:09
 * @Version 1.0
 * 作为web返回的Json结果集的同一接口
 */
public class Result {
    private boolean success;
    private String message;
    private Object data;
    @JsonFormat(pattern = "yyyy年MM月dd日 hh点mm分ss秒 a",locale = "zh",timezone = "GTM+8")
    private Date now;

    public Result(boolean success,Object data){
        this(success,null,data);
    }

    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.now=new Date();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
