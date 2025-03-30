package com.example.databases.controller;

import com.example.databases.Service.BlogService;
import com.example.databases.entity.Blog;
import com.example.databases.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 博客信息前端操作接口
 **/
@RestController
@RequestMapping("/blog")
public class BlogController {


    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    /**
     * 查询所有
     */
    @CrossOrigin
    @GetMapping("/selectAll")
    public List<Blog> selectAll(){
        return  blogMapper.selectAll();
    }

    /**
     * title
     */
    @CrossOrigin
    @GetMapping("/s1/{title}")
    public List<Blog> s1(@PathVariable String title){
        return blogMapper.select1(title);
    }

    /**
     * tags
     */
    @CrossOrigin
    @GetMapping("/s2/{tags}")
    public List<Blog> s2(@PathVariable String tags){
        return blogMapper.select2(tags);

    }
    /**
     * brief
     */
    @CrossOrigin
    @GetMapping("/s3/{brief}")
    public List<Blog> s3(@PathVariable String brief){
        return blogMapper.select3(brief);

    }

    /**
     * category
     */
    @CrossOrigin
    @GetMapping("/s4/{categoryName}")
    public List<Blog> s4(@PathVariable String categoryName){
        return blogMapper.select4(categoryName);

    }
    /**
     * username
     */
    @CrossOrigin
    @GetMapping("/s5/{username}")
    public List<Blog> s5(@PathVariable String username){
        return blogMapper.select5(username);

    }


    /**
     * 单个删除
     */
    @CrossOrigin
    @GetMapping("/del/{id}")
    public void d(@PathVariable Integer id){
        blogMapper.deleteById(id);
    }

    @CrossOrigin
    @PostMapping("/add")
    public void i(@RequestBody Blog blog){
        blogMapper.insert(blog);
    }

    @CrossOrigin
    @PostMapping("/update")
    public void up(@RequestBody Blog blog){
        blogMapper.updateById(blog);
    }


    /**
     * 热门榜单
     * 根据阅读量进行判断
     */
     @CrossOrigin
     @GetMapping("/selectTop")
     public List<Blog> t(){
         List<Blog> blogList = this.selectAll();
         blogList = blogList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                 .limit(20)
                 .collect(Collectors.toList());
         return blogList;
     }
    /**
     * 根据id查询
     */
    @CrossOrigin
    @GetMapping("s6/{id}")
    public Blog s6(@PathVariable Integer id){
        Blog b=blogMapper.select6(id);
        blogMapper.upread(b.getReadCount()+1,id);
        return b;
    }

    /**
     * 推荐算法
     * 根据标签进行推荐
     */
    @CrossOrigin
    @GetMapping("/selectRecommend/{blogId}")
    public Set selectRecommend(@PathVariable Integer blogId) {
            return blogService.selectRecommend(blogId);

    }

    /**1
     * like修改
     */
    @CrossOrigin
    @GetMapping("/uplike/{id}")
    public void upl(@PathVariable Integer id){
        Integer like=blogMapper.select6(id).getLikeCount()+1;
        blogMapper.uplike(like,id);
    }
    /**
     * 总点赞数量
     */
    @CrossOrigin
    @GetMapping("/sl/{id}")
    public int sumlike(@PathVariable Integer id){
        return blogService.sumlike(id);
    }
    /**
     * 总收藏量
     */
    @CrossOrigin
    @GetMapping("/ss/{id}")
    public int sumstar(@PathVariable Integer id){
        return blogService.sumstar(id);
    }

    /**
     *总发布数
     */
    @CrossOrigin
    @GetMapping("deli/{id}")
    public int deli(@PathVariable Integer id){
        return blogMapper.deliver(id);
    }

    /**
     *我的发布
     */
    @CrossOrigin
    @GetMapping("fuid/{id}")
    public List<Blog> finduid(@PathVariable Integer id){
        return blogMapper.select7(id);
    }
    /**
     * 我的收藏
     */
    @CrossOrigin
    @GetMapping("/myd/{id}")
    public List<Blog> myde(@PathVariable Integer id){
        return blogMapper.select8(id);
    }
    /**
     * 总搜索
     */
    @CrossOrigin
    @GetMapping("/sq/{q}")
    public List<Blog> sq(@PathVariable String  q){
        return blogMapper.selectbyq(q);
    }
}
