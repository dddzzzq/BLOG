package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;


@Data
public class Likes {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private Integer fid;
    private Integer uid;
    private String module;

}
