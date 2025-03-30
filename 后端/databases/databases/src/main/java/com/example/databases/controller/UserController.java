package com.example.databases.controller;
import com.example.databases.Common.Result;
import com.example.databases.Service.UserService;
import com.example.databases.entity.user;
import com.example.databases.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    //插入一条数据
    @CrossOrigin
    @PostMapping("/add")
    public Result insert(@RequestBody user user)
    {
        try{
            userService.insert(user);
            return new Result().success("插入成功");
        }catch (Exception e){
            if(e instanceof DuplicateKeyException)
                return  new Result().error("插入失败");
            else
                return new Result().error("系统错误");
        }

    }
    @CrossOrigin
    @GetMapping("/all")
    public Result select()
    {
        return new Result().success(userService.selectall());

    }
    @CrossOrigin
    @PostMapping("/update")
    public Result update(@RequestBody user user)
    {
        userService.update(user);
        return new Result().success("更新成功");
    }
    @CrossOrigin
    @PostMapping("/judge")
    public Result judge(@RequestBody user user)
    {
        return new Result().success(userService.judge(user));
    }

    @CrossOrigin
    @PostMapping("/judge1")
    public Result judge1(@RequestBody user user)
    {
        return new Result().success(userService.judge1(user));
    }

    @CrossOrigin
    @GetMapping("/del/{id}")
    public  Result del(@PathVariable String id)
    {
        userService.del(id);
        return  new Result().success();
    }
    @CrossOrigin
    @GetMapping("/jud2/{id}")
    public  Result jud2(@PathVariable String  id)
    {
        return new Result().success(userService.jud(id));
    }

    // 根据用户名模糊查询
    @CrossOrigin
    @GetMapping("/getUsername/{username}")
    public List<user> getUsername(@PathVariable String username) {
        return userService.getUsername(username);
    }

    @CrossOrigin
    @GetMapping("/getname/{id}")
    public String get(@PathVariable Integer id){
      return userMapper.named(id);
    }

}
