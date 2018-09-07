package com.cq.provider;

import com.cq.api.UserServerBo;
import org.springframework.stereotype.Service;

/**
 * @Author: 陈强
 * @Date: 2018/9/7 15:37
 * @Version 1.0
 */
public class UserServiceImpl implements UserServerBo {

    public String sayHello(String name) {
        return name;
    }
}
