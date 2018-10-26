package com.cq.springboot.Seivice.SeriviceImp;

import com.cq.springboot.Dao.UserDao;
import com.cq.springboot.Model.User;
import com.cq.springboot.Seivice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 陈强
 * @Date: 2018/8/24 14:36
 * @Version 1.0
 */
@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public User getUser(String name) {
        return userDao.getUser(name);
    }
}
