package com.forestj.controller;

import com.forestj.pojo.ResponseJson;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@ApiIgnore
public class MyErrorController {
    @RequestMapping("/errors")
    public ResponseJson errors(){
        throw new AuthorizationException();
    }
}
