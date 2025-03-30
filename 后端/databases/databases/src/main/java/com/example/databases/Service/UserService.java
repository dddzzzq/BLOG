package com.example.databases.Service;

import com.example.databases.entity.user;
import com.example.databases.exception.ServiceException;
import com.example.databases.mapper.UserMapper;
import com.example.databases.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    //注入Mapper层
    @Autowired
    private UserMapper userMapper;
    //插入一条SQL语句
    public void insert(user user)
    {
        userMapper.insert(user);
    }
    //查询所有SQL语句
    public List<user> selectall()
    {

        return userMapper.selectall();
    }
    public void update(user user){
        userMapper.update(user);
    }

    //登录时使用
    public user judge(user user)
    {
        user user1= userMapper.judge(user);
        if (user1==null)
            throw new ServiceException("用户名或密码错误！");
        //生成Token
        String Token=   TokenUtils.genToken(String.valueOf(user1.getId()),user1.getPassword());
        user1.setToken(Token);
        return user1;
    }
    //个人中心使用
    public user judge1(user user)
    {
        return userMapper.judge1(user);
    }


    public void  del(String id){
        userMapper.del(id);
    }
    public  user jud(String id){
        return userMapper.judge2(id);
    }

    public List<user> getUsername(String username) {
        return userMapper.getUsername(username);
    }




}
