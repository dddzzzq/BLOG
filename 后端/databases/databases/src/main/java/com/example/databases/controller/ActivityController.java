package com.example.databases.controller;

import com.example.databases.entity.Activity;
import com.example.databases.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/act")
public class ActivityController {
    @Autowired
    private ActivityMapper activityMapper;

    @CrossOrigin
    @GetMapping("/all")
    public List<Activity> all(){
       return activityMapper.selectAll();
    }
    @CrossOrigin
    @PostMapping("/ins")
    public void insert(@RequestBody Activity activity){
        activityMapper.insert(activity);
    }
    @CrossOrigin
    @GetMapping("del/{id}")
    public void del(@PathVariable Integer id){
        activityMapper.deleteById(id);
    }
    @CrossOrigin
    @PostMapping("/up")
    public void up(@RequestBody Activity activity){
        activityMapper.up(activity);
    }

    @CrossOrigin
    @GetMapping("/selectTop")
    public List<Activity> t(){
        List<Activity> list1=activityMapper.selectAll();
        list1 = list1.stream().sorted((b1, b2) -> b2.getRead().compareTo(b1.getRead()))
                .limit(5)
                .collect(Collectors.toList());
        return list1;
    }
    @CrossOrigin
    @GetMapping("/s1/{id}")
    public Activity s1(@PathVariable Integer id){
        return activityMapper.select1(id);
    }


}
