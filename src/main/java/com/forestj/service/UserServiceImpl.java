package com.forestj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.forestj.mapper.UserMapper;
import com.forestj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByInvitationCode(String invitationCode) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("invitation_code",invitationCode);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public void updatePwd(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }
}
