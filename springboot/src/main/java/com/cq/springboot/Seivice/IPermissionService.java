package com.cq.springboot.Seivice;

import com.cq.springboot.Model.Permission;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 14:32
 * @Version 1.0
 */
public interface IPermissionService {
    Integer addPermission(Permission permission);
    Integer updatePermission(Permission permission);
    Integer deletePermission(int id);
    Permission getPermission(int id);
}
