package com.forestj.controller;

import com.forestj.pojo.ResponseJson;
import com.forestj.pojo.ResultCode;
import com.forestj.pojo.User;
import com.forestj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public ResponseJson userLogin(String id,
                                  String password){
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(password)){
            return new ResponseJson(ResultCode.EMPTYINFO);
        }
        password = new Md5Hash(password,id,2).toString();
        log.info("传参并加密后："+id+":"+password);
        UsernamePasswordToken upToken = new UsernamePasswordToken(id,password);
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(upToken);
            log.info(id+"登录成功");
            return new ResponseJson(ResultCode.SUCCESS);
        }catch (Exception e){
            log.info(id+"登录失败");
            return new ResponseJson(ResultCode.WRONGINFO);
        }
    }

    @GetMapping("/logout")
    @ApiOperation(value = "注销登录")
    public ResponseJson logout(String id){
        Subject subject = SecurityUtils.getSubject();
        log.info(((User) subject.getPrincipal()).getId()+"退出登录");
        subject.logout();
        return new ResponseJson(ResultCode.SUCCESS);
    }
//    @GetMapping("/test")
//    public String test(){
//        return "success";
//    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取当前用户信息")
    public ResponseJson<User> getUserInfo(String id){
        if(StringUtils.isEmpty(id)){
            return new ResponseJson<>(ResultCode.EMPTYINFO);
        }
        User user = userService.getUserById(id);
        if(user==null){
            return new ResponseJson<>(ResultCode.WRONGINFO);
        }
        return new ResponseJson<>(ResultCode.SUCCESS,user);
    }

    @PostMapping("/updatePwd")
    @ApiOperation(value = "密码修改")
    public ResponseJson updatePwd(String id,
                                  String oldPassword,
                                  String newPassword){
        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(oldPassword)||StringUtils.isEmpty(oldPassword)){
            return new ResponseJson(ResultCode.EMPTYPWD);
        }
        //密码md5加密
        oldPassword = new Md5Hash(oldPassword,id,2).toString();
        newPassword = new Md5Hash(newPassword,id,2).toString();
        User user = userService.getUserById(id);
        if(user==null||!user.getPassword().equals(oldPassword)){
            return new ResponseJson(ResultCode.WRONGPWD);
        }
        user.setPassword(newPassword);
        userService.updatePwd(user);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResponseJson(ResultCode.SUCCESS);
    }

    @PostMapping("/signup")
    @ApiOperation(value = "添加用户")
    public ResponseJson signup(String id,
                               String password,
                               String name,
                               String invitationCode){
        User user = userService.getUserById(id);
        if(user!=null){
            return new ResponseJson(ResultCode.REITERATEDID);
        }
        user = userService.getUserByInvitationCode(invitationCode);
        if(user==null){
            return new ResponseJson(ResultCode.WRONGINVITATIONCODE);
        }
        String newInvitationCode = UUID.randomUUID().toString().substring(0, 8);
        password = new Md5Hash(password,id,2).toString();
        user = new User(id,password,name,newInvitationCode);
        userService.insertUser(user);
        return new ResponseJson(ResultCode.SUCCESS);
    }


}
