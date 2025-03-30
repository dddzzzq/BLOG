package com.example.databases.mapper;

import com.example.databases.entity.Collect;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CollectMapper {
    @Insert(" insert into collect (fid, uid, module) values (#{fid}, #{uid}, #{module})")
    void insert(Collect collect);
    @Select(" select * from collect where fid = #{fid} and uid = #{uid} and module = #{module}")
    Collect selectUserCollect(Collect collect);

    @Delete(" delete from collect where id = #{id}")

    void deleteById(Integer id);

    @Select(" select count(*) from collect where fid = #{fid} and module = #{module}")
    int selectByFidAndModule(@Param("fid") Integer fid, @Param("module") String module);
}
