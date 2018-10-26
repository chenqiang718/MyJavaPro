package com.cq.springboot.Dao;

import com.cq.springboot.Model.Role;
import org.springframework.stereotype.Repository;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 13:41
 * @Version 1.0
 */
@Repository
public interface RoleDao {
    Integer addRole(Role role);
    Integer updateRole(Role role);
    Integer deleteRole(int id);
    Role getRole(int id);
}
