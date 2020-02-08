package com.forestj;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Test
    public void test01(){
        String s = new Md5Hash("admin", "admin", 2).toString();
        System.out.println(s);
    }
    @Test
    public void testLogin(){
        UsernamePasswordToken upToken = new UsernamePasswordToken("admin","admin");
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
        System.out.println("success");
    }
}
