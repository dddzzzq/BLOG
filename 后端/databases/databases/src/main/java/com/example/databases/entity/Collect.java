package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Collect {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer fid;
    private Integer uid;
    private String module;
}
