package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @TableId(type= IdType.AUTO)
    /** ID */
    private Integer id;
    /** 活动名称 */
    private String name;
    /** 活动简介 */
    private String descr;
    /** 开始时间 */
    private String start;
    /** 结束时间 */
    private String end;
    /** 活动形式 */
    private String form;
    /** 活动地址 */
    private String address;
    /** 主办方 */
    private String host;
    /** 浏览量 */
    private Integer read;
    private String content;
    private String cover;
}
