package com.cq.springboot.Controller;

import com.cq.springboot.Model.User;
import com.cq.springboot.Seivice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 陈强
 * @Date: 2018/8/24 14:50
 * @Version 1.0
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/getAllUser")
    public String getAllUser(){
        StringBuffer sb=new StringBuffer();
        List<User> userList=userService.getAllUser();
        for (User user:userList){
            sb.append(user.toString()+"\n");
        }
        return sb.toString();
    }
}
