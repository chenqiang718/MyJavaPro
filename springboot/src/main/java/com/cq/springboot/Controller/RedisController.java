package com.cq.springboot.Controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.management.Agent;

/**
 * @Author: 陈强
 * @Date: 2018/9/6 15:19
 * @Version 1.0
 */
@RestController
public class RedisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation(value = "redies接口测试",notes = "测试")
    @RequestMapping("/redis")
    public String redisTest(){
        stringRedisTemplate.opsForValue().set("age", "20");
        return stringRedisTemplate.opsForValue().get("age");
    }
}
