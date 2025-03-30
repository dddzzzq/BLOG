package com.example.databases.controller;

import com.example.databases.Service.CollectService;
import com.example.databases.entity.Collect;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/collect")
public class CollectController {
    @Resource
    CollectService collectService;

    // 收藏和取消（博客）
    @PostMapping("/set")
    public void set(@RequestBody Collect collect) {
        collectService.set(collect);
    }
    // 收藏和取消（活动）
    @PostMapping("/set2")
    public void set2(@RequestBody Collect collect) {
        collectService.set2(collect);
    }
    @GetMapping("s1/{id}")
    public boolean f1(@PathVariable Integer id){
        return collectService. selectUserCollect(id,"博客");
    }
    @GetMapping("s2/{id}")
    public boolean f2(@PathVariable Integer id){
        return collectService. selectUserCollect(id,"活动");
    }
}
