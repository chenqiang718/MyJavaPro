package com.cq.springboot.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {
    @RequestMapping("/Hello")
    public String SayHello(){
        return "你好";
    }
}
