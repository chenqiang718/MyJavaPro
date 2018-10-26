package com.cq.springboot.Model;

import java.util.List;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 13:33
 * @Version 1.0
 * 角色
 */
public class Role {
    private long id;
    private String rolename;
    private String permissions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
