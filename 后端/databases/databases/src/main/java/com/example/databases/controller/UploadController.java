package com.example.databases.controller;

import com.example.databases.entity.Upload;
import com.example.databases.mapper.UploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadMapper uploadMapper;

    @CrossOrigin
    @GetMapping("/all")
    public List<Upload> all(){
        return uploadMapper.all();
    }
    @CrossOrigin
    @GetMapping("/del/{id}")
    public void del(@PathVariable Integer id){
        uploadMapper.del(id);
    }

    @CrossOrigin
    @PostMapping("/ins")
    public void ins(@RequestBody Upload upload){
      uploadMapper.ins(upload);
    }
    @CrossOrigin
    @GetMapping("/s1/{username}")
    public List<Upload> s1(@PathVariable String username){
        return uploadMapper.s1(username);
    }
}
