package com.cq.springboot.Controller;

import com.cq.springboot.Model.Result;
import com.cq.springboot.properties.StudentPropeties;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;

@Controller
public class ControllerTest {

    @Autowired
    private StudentPropeties studentPropeties;

    @ApiOperation(value = "Hello测试接口",notes = "测试")
    @ResponseBody
    @RequestMapping("/Hello")
    public Result SayHello() throws UnsupportedEncodingException {
        String name=studentPropeties.getName();
        System.out.println(name);
        return new Result("出错了:"+name);
    }

    @ApiOperation(value = "hello的测试接口",notes = "测试")
    @RequestMapping("/hello")
    public Result SayHelloHtml(){
        int i=1/0;
        return new Result(true,"Hello");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class Conclass=Class.forName("com.cq.springboot.Controller.ControllerTest");
        Conclass.getConstructor().newInstance();
    }
}
