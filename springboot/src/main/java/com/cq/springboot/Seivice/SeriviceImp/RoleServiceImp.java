package com.cq.springboot.Seivice.SeriviceImp;

import com.cq.springboot.Dao.RoleDao;
import com.cq.springboot.Model.Role;
import com.cq.springboot.Seivice.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 14:33
 * @Version 1.0
 */
@Service
public class RoleServiceImp implements IRoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public Integer addRole(Role role) {
        return roleDao.addRole(role);
    }

    @Override
    public Integer updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public Integer deleteRole(int id) {
        return roleDao.deleteRole(id);
    }

    @Override
    public Role getRole(int id) {
        return roleDao.getRole(id);
    }
}
