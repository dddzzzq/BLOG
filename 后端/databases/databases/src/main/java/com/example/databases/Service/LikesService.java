package com.example.databases.Service;

import com.example.databases.entity.Likes;
import com.example.databases.entity.user;
import com.example.databases.mapper.BlogMapper;
import com.example.databases.mapper.LikesMapper;
import com.example.databases.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LikesService {
    @Resource
    LikesMapper likesMapper;
    @Resource
    BlogMapper blogMapper;

    public void set(Likes likes) {
       user currentUser = TokenUtils.getCurrentUser();
        likes.setUid(currentUser.getId());
        Likes dblLikes = likesMapper.selectUserLikes(likes);
        if (dblLikes == null) {
            likesMapper.insert(likes);
            blogMapper.uplike(blogMapper.select6(likes.getFid()).getLikeCount()+1,likes.getFid());
        } else {
            likesMapper.deleteById(dblLikes.getId());
            blogMapper.uplike(blogMapper.select6(likes.getFid()).getLikeCount()-1,likes.getFid());
        }
    }

    /**
     * 查询当前用户是否点过赞
     */
    public boolean selectUserLikes(Integer fid, String module) {
        user currentUser = TokenUtils.getCurrentUser();
        Likes likes = new Likes();
        likes.setUid(currentUser.getId());
        likes.setFid(fid);
        likes.setModule(module);
        return likesMapper.selectUserLikes(likes)!=null;
    }

    public int selectByFidAndModule(Integer fid, String module) {
        return likesMapper.selectByFidAndModule(fid, module);
    }
}
