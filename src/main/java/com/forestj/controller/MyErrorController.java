package com.forestj.controller;

import com.forestj.pojo.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@ApiIgnore
@Slf4j
public class MyErrorController {
    @RequestMapping("/errors")
    public ResponseJson errors(){
        log.info("未登录");
        throw new AuthorizationException();
    }
}
