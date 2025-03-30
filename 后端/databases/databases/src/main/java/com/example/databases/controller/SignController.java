package com.example.databases.controller;

import com.example.databases.Service.SignService;
import com.example.databases.entity.Sign;
import com.example.databases.mapper.SignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sign")
public class SignController {
    @Resource
    SignService activitySignService;

    @Autowired
    SignMapper signMapper;

    @PostMapping("/add")
    public void add(@RequestBody Sign activitySign) {
        activitySignService.add(activitySign);
    }

    /**
     * 删除
     */
    @GetMapping("/del/{id}")
    public void deleteById(@PathVariable Integer id) {
        activitySignService.deleteById(id);
    }
    @GetMapping("/all")
    public List<Sign> all(){
        return signMapper.selectAll();
    }

    @GetMapping("/f/{actId}")
    public boolean f(@PathVariable Integer actId){
        return activitySignService.f(actId);
    }

    @PostMapping("/set")
    public void set(@RequestBody Sign sign){
        activitySignService.set(sign);
    }


}
