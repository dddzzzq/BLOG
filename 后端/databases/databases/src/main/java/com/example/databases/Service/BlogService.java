package com.example.databases.Service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.databases.entity.Blog;
import com.example.databases.mapper.BlogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Resource
    private BlogMapper blogMapper;

   //
    public Set<Blog> selectRecommend(Integer blogId) {
        Blog blog = blogMapper.select6(blogId);
        String tags = blog.getTags();
        Set<Blog> blogSet = new HashSet<>();
        if (ObjectUtil.isNotEmpty(tags)) {
            List<Blog> blogList = blogMapper.selectAll();
            JSONArray tagsArr = JSONUtil.parseArray(tags);
           for (Object tag1 : tagsArr) {
                Set<Blog> collect = blogList.stream().filter(b -> b.getTags().contains(tag1.toString()) && !blogId.equals(b.getId()))
                        .collect(Collectors.toSet());
                blogSet.addAll(collect);
            }
        }

        return blogSet;
    }

    public int sumlike(Integer id){
        int res=0;
        List<Integer> like=blogMapper.sumlike(id);
        for(Integer a:like)
        {
            res+=a;
        }
        return res;
    }
    public int sumstar(Integer id){
        int res=0;
        List<Integer> like=blogMapper.sumstar(id);
        for(Integer a:like)
        {
            res+=a;
        }
        return res;
    }
}
