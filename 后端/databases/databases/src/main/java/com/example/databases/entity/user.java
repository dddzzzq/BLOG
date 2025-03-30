package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("user")
public class user {
    @TableId(type= IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String info;
    private String sex;
    private String avatar;
    private String authority;
    private String time;
    private String school;
    private String place;

    private String token;
}
