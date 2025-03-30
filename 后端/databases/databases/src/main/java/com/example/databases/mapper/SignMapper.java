package com.example.databases.mapper;

import com.example.databases.entity.Sign;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignMapper {
    @Insert("insert into sign (id, activityId, userId, time ) values (#{id}, #{activityId}, #{userId}, #{time})")
    void insert(Sign sign);

    @Select("select * from sign where activityId = #{activityId} and userId = #{userId}")
    Sign selectByActivityIdAndUserId(@Param("activityId") Integer actId, @Param("userId") Integer userId);

    @Select("select sign.*, activity.name as activityName, user.name as userName from sign " +
            "left join activity on sign.activityId = activity.id " +
            "left join user on sign.userId = user.id " +
            "order by id desc")
    List<Sign> selectAll();

    @Delete("delete from sign where id = #{id}")
    void deleteById(Integer id);
    @Delete("delete from sign where userId=#{userId} and activityId=#{activityId}")
    void del(Integer userId,Integer activityId);
}
