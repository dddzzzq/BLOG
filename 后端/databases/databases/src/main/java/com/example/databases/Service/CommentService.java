package com.example.databases.Service;

import com.example.databases.entity.Comment;
import com.example.databases.mapper.CommentMapper;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;
    public List<Comment> selectForUser(Comment comment) {

        List<Comment> commentList = commentMapper.selectForUser(comment);  // 查询一级的评论
       for (Comment c : commentList) {  // 查询回复列表
            List<Comment> children = commentMapper.s1(c.getId(),"博客");
            children = children.stream().filter(child -> !child.getId().equals(c.getId())).collect(Collectors.toList());  // 排除当前查询结果里最外层节点
            c.setChildren(children);
        }
        return commentList;
    }

    public List<Comment> selectForUser2(Comment comment) {

        List<Comment> commentList = commentMapper.selectForUser(comment);  // 查询一级的评论
        for (Comment c : commentList) {  // 查询回复列表
            List<Comment> children = commentMapper.s1(c.getId(),"活动");
            children = children.stream().filter(child -> !child.getId().equals(c.getId())).collect(Collectors.toList());  // 排除当前查询结果里最外层节点
            c.setChildren(children);
        }
        return commentList;
    }
}
