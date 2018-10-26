package com.cq.springboot.Model;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 13:37
 * @Version 1.0
 * 权限
 */
public class Permission {
    private long id;
    private String permission;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
