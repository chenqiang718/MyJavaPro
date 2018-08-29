package com.cq.springboot.Seivice;

import com.cq.springboot.Model.User;

import java.util.List;

/**
 * @Author: 陈强
 * @Date: 2018/8/24 14:35
 * @Version 1.0
 */
public interface IUserService {
    List<User> getAllUser();
}
