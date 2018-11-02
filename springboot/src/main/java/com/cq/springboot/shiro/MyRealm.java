package com.cq.springboot.shiro;

import com.cq.springboot.Model.Role;
import com.cq.springboot.Model.User;
import com.cq.springboot.Seivice.IPermissionService;
import com.cq.springboot.Seivice.IRoleService;
import com.cq.springboot.Seivice.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 11:28
 * @Version 1.0
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user= (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        Role role=roleService.getRole(user.getId());
        for(String id:role.getPermissions().split(",")){
            authorizationInfo.addStringPermission(permissionService.getPermission(Integer.parseInt(id)).getPermission());
        }
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("--------------------------------------");
        System.out.println("Token: "+authenticationToken.toString());
        if(authenticationToken instanceof UsernamePasswordToken){
            System.out.println("+++++++++++++Yes++++++++++++++++");
            String token = (String) authenticationToken.getPrincipal();
            System.out.println("+++++++++++++++++");
            System.out.println("token:"+token);
            User user=userService.getUser(token);
            if(user==null)
                return null;
            SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user , user.getPassword(), getName());
            return authenticationInfo;
        }
        return null;
    }
}
