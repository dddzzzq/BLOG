package com.example.databases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @TableId(type= IdType.AUTO)
    /** ID */
    private Integer id;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 简介 */
    private String descr;
    /** 封面 */
    private String cover;
    /** 标签 */
    private String tags;
    /** 发布人ID */
    private Integer uid;
    /** 发布日期 */
    private String date;
    /** 浏览量 */
    private Integer readCount;
    private Integer likeCount;
    private Integer star;

    private Integer cid;
    private String categoryName;
    private String userName;


}
