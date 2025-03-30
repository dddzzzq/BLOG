package com.example.databases.mapper;

import com.example.databases.entity.user;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
  //插入一条SQL语句
  @Insert("insert into `user` (id,username, password, name, phone,avatar,authority,time,sex,info,school,place) " +
          "values (#{id},#{username}, #{password}, #{name}, #{phone},#{avatar},#{authority},#{time},#{sex},#{info},#{school},#{place})")
    void insert(user user);
  //查询所有SQL语句
  @Select("select * from user ")
  public List<user> selectall();

  @Update("update `user` set name=#{name},phone=#{phone},sex=#{sex},info=#{info}, avatar=#{avatar},authority=#{authority},time=#{time},username=#{username}, password = #{password} ,school=#{school},place=#{place} where username=#{username}")
  public void update(user user);

  @Select("select * from `user` where  username=#{username} and password=#{password} ")
  public user judge(user user);

  @Select("select * from `user` where  username=#{username}  ")
  public user judge1(user user);
  @Select("select * from `user` where  id=#{id} ")
  public user judge2(String id);

  @Delete("delete from `user` where id = #{id}")
  void del(String id);

  @Select("select * from `user` where username like concat('%',#{username},'%')")
  List<user> getUsername(String username);

  @Select("select user.username from user where id=#{id}")
  String named(Integer id);



}
