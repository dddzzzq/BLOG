package com.example.databases.controller;

import com.example.databases.entity.Category;
import com.example.databases.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cate")
public class CategoryController {
    @Autowired
    private CategoryMapper categoryMapper;

    @CrossOrigin
    @GetMapping("/all")
    public List<Category> all(){
        return  categoryMapper.selectAll();
    }

    @CrossOrigin
    @PostMapping("/ins")
    public void insert(@RequestBody Category category){
        categoryMapper.insert(category);
    }
    @CrossOrigin
    @GetMapping("del/{id}")
    public void del(@PathVariable Integer id){
        categoryMapper.deleteById(id);
    }
    @CrossOrigin
    @GetMapping("/s1/{name}")
    public List<Category> s1(@PathVariable String name){
        return categoryMapper.s1(name);
    }
}
