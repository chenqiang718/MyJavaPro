package com.cq.springboot.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class ControllerTest {
    @Value("${student.name}")
    private String name;
    @RequestMapping("/Hello")
    public String SayHello() throws UnsupportedEncodingException {
        System.out.println(name);
        return "你好:"+name;
    }
}
