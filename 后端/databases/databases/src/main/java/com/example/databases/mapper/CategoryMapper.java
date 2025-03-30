package com.example.databases.mapper;

import com.example.databases.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {


    @Insert("insert into `category`(id,name) values (#{id},#{name})")
    int insert(Category category);


    @Delete("delete from `category` where id = #{id}")
    void deleteById(Integer id);


    @Select("select * from category")
    List<Category> selectAll();

    @Select("select * from category where name like concat('%',#{name},'%')")
    List<Category> s1(String name);
}
