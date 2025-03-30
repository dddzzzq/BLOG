package com.example.databases.controller;

import com.example.databases.Service.CommentService;
import com.example.databases.entity.Comment;
import com.example.databases.entity.user;
import com.example.databases.mapper.CommentMapper;
import com.example.databases.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/com")
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @CrossOrigin
    @GetMapping("/all")
    public List<Comment> all(){
        return commentMapper.selectAll();
    }
    @CrossOrigin
    @GetMapping("/del/{id}")
    public void d(@PathVariable Integer id){
        commentMapper.del(id);
        commentMapper.deleteById(id);
    }

    @PostMapping("/selectForUser")
    public List<Comment> selectForUser(@RequestBody  Comment comment) {
        return commentService.selectForUser(comment);
    }
    @PostMapping("/selectForUser2")
    public List<Comment> selectForUser2(@RequestBody  Comment comment) {
        return commentService.selectForUser2(comment);
    }

    @PostMapping("/ins")
    public void insert(@RequestBody Comment comment){
        user currentUser = TokenUtils.getCurrentUser();
        comment.setUid(currentUser.getId());
        if(comment.getRid()==null){
            comment.setRid(comment.getPid());
        }
        commentMapper.insert(comment);
    }

    @GetMapping("/fid/{id}")
    public Integer fid(@PathVariable Integer id){
        return commentMapper.findid(id);
    }
}
