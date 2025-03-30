package com.example.databases.mapper;

import com.example.databases.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment(id,content,uid,pid,rid,time,fid,module) values" +
            "(#{id},#{content},#{uid},#{pid},#{rid},#{time},#{fid},#{module})")
    void insert(Comment comment);

    @Delete("delete from comment where  id = #{id}")
    void deleteById(Integer id);

    @Delete("delete from comment where rid=#{id} ")
    void del(Integer id);

    @Update("update comment set content = #{content},uid = #{uid},pid = #{pid},rid = #{rid},time = #{time},fid = #{fid}, module = #{module} where id=#{id}")
    void updateById(Comment comment);


    @Select("select * from comment where id=#{id}")
    Comment selectById(Integer id);



    @Select("select * from comment ")
    List<Comment> selectAll();

   @Select("select" +
           "    comment.*, user.username as userName,  user.avatar as avatar, u2.username as replyUser" +
           "  from comment" +
           "  left join user" +
           "  on comment.uid = user.id" +
           "   left join comment c2" +
           "   on comment.pid = c2.id" +
           "  left join user u2" +
           "   on c2.uid = u2.id where comment.rid=#{id} and c2.module=#{module} ")
    List<Comment> s1(Integer id,String module);

  @Select("   select" +
            "  comment.*, user.username as userName, user.avatar as avatar" +
            "  from comment" +
            "  left join user on comment.uid = user.id where comment.fid=#{fid} and comment.module=#{module} and pid is null order by id desc ")
    List<Comment> selectForUser(Comment comment);

    @Select("select comment.uid from comment where id=#{id}")
    Integer findid(Integer id);

}
