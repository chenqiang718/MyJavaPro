package com.cq.springboot.Controller;

import com.cq.springboot.Model.Result;
import com.cq.springboot.Model.User;
import com.cq.springboot.Seivice.IUserService;
import com.rabbitmq.tools.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping("/getAllUser")
    public Result getAllUser(){
        StringBuffer sb=new StringBuffer();
        List<User> userList=userService.getAllUser();
        for (User user:userList){
            sb.append(user.toString()+"\n");
        }
        logger.info(sb.toString());
        return new Result(true,"成功返回",sb.toString());
    }
}
