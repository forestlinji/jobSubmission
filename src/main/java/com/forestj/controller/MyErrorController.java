package com.forestj.controller;

import com.forestj.pojo.ResponseJson;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MyErrorController {
    @RequestMapping("/errors")
    public ResponseJson errors(){
        throw new AuthorizationException();
    }
}
