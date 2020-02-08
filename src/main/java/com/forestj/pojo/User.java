package com.forestj.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    //账号
    @JsonIgnore
    @TableId
    private String id;
    //密码
    @JsonIgnore
    private String password;
    //用户昵称
    private String name;
    //邀请码
    private String invitationCode;
}
