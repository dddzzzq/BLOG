package com.example.databases.mapper;

import com.example.databases.entity.Likes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LikesMapper {

    @Insert(" insert into likes (fid, uid, module) values (#{fid}, #{uid}, #{module})")
    void insert(Likes likes);


    @Select("select * from likes where fid = #{fid} and uid = #{uid} and module = #{module}")
    Likes selectUserLikes(Likes likes);

    @Delete("delete from likes where id=#{id}")
    void deleteById(Integer id);

    @Select("select count(*) from likes where fid=#{fid} and module=#{module}")
    int selectByFidAndModule( Integer fid,String module);
}
