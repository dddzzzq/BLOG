package com.example.databases.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /** ID */
    private Integer id;
    /** 分类名称 */
    private String name;
}
