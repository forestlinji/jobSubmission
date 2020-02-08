package com.forestj.service;

import com.forestj.pojo.User;

public interface UserService {
    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     * 根据邀请码获取用户信息
     * @param invitationCode
     * @return
     */
    public User getUserByInvitationCode(String invitationCode);

    /**
     * 更改密码
     * @param user
     */
    public void updatePwd(User user);

    /**
     * 添加用户
     * @param user
     */
    public void insertUser(User user);
}
