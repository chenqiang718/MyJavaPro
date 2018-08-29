package com.cq.springboot.Controller;

import com.cq.springboot.properties.StudentPropeties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

@Controller
public class ControllerTest {
    @Autowired
    private StudentPropeties studentPropeties;

    @ResponseBody
    @RequestMapping("/Hello")
    public String SayHello() throws UnsupportedEncodingException {
        String name=studentPropeties.getName();
        System.out.println(name);
        return "你好:"+name;
    }

    @RequestMapping("/hello")
    public String SayHelloHtml(){
        return "Hello";
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class Conclass=Class.forName("com.cq.springboot.Controller.ControllerTest");
        Conclass.getConstructor().newInstance();
    }
}
