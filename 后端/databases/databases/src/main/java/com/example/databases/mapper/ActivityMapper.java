package com.example.databases.mapper;

import com.example.databases.entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Insert("insert into activity(id,name,descr,content,start,end,form,address,host,cover) values" +
            "(#{id},#{name},#{descr},#{content},#{start},#{end},#{form},#{address},#{host},#{cover})")
    int insert(Activity activity);

    @Delete("delete from activity where id=#{id}")
    int deleteById(Integer id);


   @Update("update activity set name=#{name},descr=#{descr},content=#{content},start=#{start},end=#{end}," +
           "form=#{form},address=#{address},host=#{host},cover=#{cover} where id=#{id} ")
    void up(Activity activity);

    @Select("select * from activity")
    List<Activity> selectAll();

    @Select("select * from activity where id=#{id}")
    Activity select1(Integer id);
}
