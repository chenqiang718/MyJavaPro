package cq.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 陈强
 * @Date: 2018/8/30 10:16
 * @Version 1.0
 */
@Controller
public class UserController {
    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        return "测试";
    }
}
