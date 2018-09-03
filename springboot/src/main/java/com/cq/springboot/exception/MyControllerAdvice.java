package com.cq.springboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 陈强
 * @Date: 2018/8/30 15:33
 * @Version 1.0
 */
@ControllerAdvice
public class MyControllerAdvice {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String,Object> dealException(Exception err){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("code", -1);
        map.put("msg", err.getMessage());
        return map;
    }
}
