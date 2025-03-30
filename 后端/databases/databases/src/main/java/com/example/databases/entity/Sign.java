package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Sign {
    @TableId(type= IdType.AUTO)
    private  int id;
    private  int activityId;
    private  String activityName;
    private  int userId;
    private  String userName;
    private  String time;

}
