package com.cq.springboot.Seivice.SeriviceImp;

import com.cq.springboot.Dao.PermissionDao;
import com.cq.springboot.Model.Permission;
import com.cq.springboot.Seivice.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 14:34
 * @Version 1.0
 */
@Service
public class PermissionServiceImp implements IPermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public Integer addPermission(Permission permission) {
        return permissionDao.addPermission(permission);
    }

    @Override
    public Integer updatePermission(Permission permission) {
        return permissionDao.updatePermission(permission);
    }

    @Override
    public Integer deletePermission(int id) {
        return permissionDao.deletePermission(id);
    }

    @Override
    public Permission getPermission(int id) {
        return permissionDao.getPermission(id);
    }
}
