package com.example.databases.controller;

import com.example.databases.Service.LikesService;
import com.example.databases.entity.Likes;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikesController {
    @Resource
    LikesService likesService;

    /**
     * 点赞或取消
     */
    @PostMapping("/set")
    public void set(@RequestBody Likes likes) {
        likesService.set(likes);
    }
    @GetMapping("s1/{id}")
    public boolean f1(@PathVariable Integer id){
        return likesService. selectUserLikes(id,"博客");
    }
}
