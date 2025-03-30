package com.example.databases.Service;

import com.example.databases.entity.Collect;
import com.example.databases.entity.user;
import com.example.databases.mapper.BlogMapper;
import com.example.databases.mapper.CollectMapper;
import com.example.databases.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CollectService {
    @Resource
    CollectMapper collectMapper;
    @Resource
    BlogMapper blogMapper;

    public void set(Collect collect) {
        user currentUser = TokenUtils.getCurrentUser();
        collect.setUid(currentUser.getId());
        Collect dblCollect = collectMapper.selectUserCollect(collect);
        if (dblCollect == null) {
            collectMapper.insert(collect);
            blogMapper.upstar(blogMapper.select6(collect.getFid()).getStar()+1,collect.getFid());
        } else {
            collectMapper.deleteById(dblCollect.getId());
            blogMapper.upstar(blogMapper.select6(collect.getFid()).getStar()-1,collect.getFid());
        }
    }

    public void set2(Collect collect) {
        user currentUser = TokenUtils.getCurrentUser();
        collect.setUid(currentUser.getId());
        Collect dblCollect = collectMapper.selectUserCollect(collect);
        if (dblCollect == null) {
            collectMapper.insert(collect);
        } else {
            collectMapper.deleteById(dblCollect.getId());
        }
    }

    /**
     * 查询当前用户是否收藏过
     */
    public boolean selectUserCollect(Integer fid, String module) {
        user currentUser = TokenUtils.getCurrentUser();
        Collect collect = new Collect();
        collect.setUid(currentUser.getId());
        collect.setFid(fid);
        collect.setModule(module);
        return collectMapper.selectUserCollect(collect)!=null;
    }


}
