package com.example.databases.mapper;

import com.example.databases.entity.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {



    @Insert("insert into `blog`(id,title,content,descr,cover,tags,date,uid,cid) values (#{id},#{title},#{content},#{descr},#{cover},#{tags},#{date},#{uid},#{cid})")
    int insert(Blog blog);

    @Delete("delete from `blog` where id = #{id}")
    int deleteById(Integer id);


    @Update("update `blog` set title=#{title},content=#{content},descr=#{descr},cover=#{cover},cid=#{cid},tags=#{tags} where id=#{id}")
    void updateById(Blog blog);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id " )
    List<Blog> selectAll();


    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where blog.title like concat('%',#{title},'%')" )
    List<Blog> select1(String title);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where blog.tags like concat('%',#{tags},'%')" )
    List<Blog> select2(String tags);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where blog.descr like concat('%',#{brief},'%')" )
    List<Blog> select3(String brief);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where category.name like concat('%',#{categoryName},'%')" )
    List<Blog> select4(String categoryName);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where user.username like concat('%',#{username},'%')" )
    List<Blog> select5(String username);


    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where blog.id =#{id}" )
    Blog select6(Integer id);

    @Update("update blog set likeCount=#{like} where id=#{id}")
    void uplike(Integer like,Integer id);
    @Update("update blog set readCount=#{readCount} where id=#{id}")
    void upread(Integer readCount,Integer id);

    @Update("update blog set star=#{star} where id=#{id}")
    void upstar(Integer star,Integer id);

    //查询总点赞数
    @Select("select likeCount from blog where uid=#{id}")
    List<Integer> sumlike(Integer id);

    //查询总收藏数
    @Select("select star from blog where uid=#{id}")
    List<Integer> sumstar(Integer id);
    @Select("select count(*) from blog where uid=#{id}")
    Integer deliver(Integer id);

    @Select("select " +
            "blog.*, category.name as categoryName ,user.username as userName " +
            "from blog  left join category on  " +
            " blog.cid = category.id" +
            " left join user on blog.uid=user.id where blog.uid =#{id}" )
    List<Blog> select7(Integer id);

    @Select("select" +
            "       blog.* from blog left join collect on" +
            "       collect.fid=blog.id where collect.uid=#{id} and collect.module='博客'")
    List<Blog> select8(Integer id);

    @Select("select " +
            "  blog.*, category.name as categoryName ,user.username as userName " +
            "  from blog  left join category on  " +
            "  blog.cid = category.id" +
            "  left join user on blog.uid=user.id where title like concat('%',#{q},'%')" +
            "  or content like concat('%',#{q},'%') or descr like concat('%',#{q},'%') " +
            "  or tags  like concat('%',#{q},'%')  " )
    List<Blog> selectbyq(String q);

}
