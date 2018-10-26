package com.cq.springboot.Seivice;

import com.cq.springboot.Model.Role;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 14:33
 * @Version 1.0
 */
public interface IRoleService {
    Integer addRole(Role role);
    Integer updateRole(Role role);
    Integer deleteRole(int id);
    Role getRole(int id);
}
