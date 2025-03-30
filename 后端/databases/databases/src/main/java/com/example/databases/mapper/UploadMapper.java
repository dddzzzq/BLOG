package com.example.databases.mapper;

import com.example.databases.entity.Upload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UploadMapper {

    @Select("select * from upload")
    List<Upload> all();


    @Insert("insert into upload(id,src,username) values(#{id},#{src},#{username})")
    void ins(Upload upload);

    @Delete("delete from upload where id=#{id}")
    void del(Integer id);

    @Select("select * from upload where username like concat('%',#{username},'%')")
    List<Upload> s1(String username);


}
