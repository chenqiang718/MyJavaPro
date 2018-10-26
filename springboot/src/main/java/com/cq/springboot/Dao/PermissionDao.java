package com.cq.springboot.Dao;

import com.cq.springboot.Model.Permission;
import org.springframework.stereotype.Repository;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 13:46
 * @Version 1.0
 */
@Repository
public interface PermissionDao {
    Integer addPermission(Permission permission);
    Integer updatePermission(Permission permission);
    Integer deletePermission(int id);
    Permission getPermission(int id);
}
