package com.forestj.config;

import com.forestj.service.UserService;
import com.forestj.pojo.User;
import com.forestj.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    public void getName(String name){
        super.setName("customRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String id = upToken.getUsername();
        String password = new String( upToken.getPassword());
        User userRes = userService.getUserById(id);
        if(userRes!=null&&id!=null&&password!=null&&userRes.getId().equals(id)&&userRes.getPassword().equals(password)){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userRes,userRes.getPassword(),this.getName());
            return info;
        }
        return null;
    }
}
