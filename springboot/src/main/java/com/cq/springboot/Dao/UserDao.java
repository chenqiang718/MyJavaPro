package com.cq.springboot.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cq.springboot.Model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 陈强
 * @Date: 2018/8/24 14:16
 * @Version 1.0
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    List<User> getAllUser();

    User getUser(@Param("name") String name);
}
