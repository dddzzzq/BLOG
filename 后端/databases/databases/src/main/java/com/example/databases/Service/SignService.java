package com.example.databases.Service;

import cn.hutool.core.date.DateUtil;
import com.example.databases.entity.Likes;
import com.example.databases.entity.Sign;
import com.example.databases.entity.user;
import com.example.databases.mapper.SignMapper;
import com.example.databases.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SignService {
    @Resource
    SignMapper activitySignMapper;

    public void add(Sign activitySign) {
        user currentUser = TokenUtils.getCurrentUser();
        Sign as = this.selectByActivityIdAndUserId(activitySign.getActivityId(), currentUser.getId());  // 查看用户是否已经报名

        activitySign.setUserId(currentUser.getId());

        activitySignMapper.insert(activitySign);
    }

    public Sign selectByActivityIdAndUserId(Integer actId, Integer userId) {
        return activitySignMapper.selectByActivityIdAndUserId(actId, userId);
    }


    public void deleteById(Integer id) {
        activitySignMapper.deleteById(id);
    }

    public void set(Sign sign) {
        user currentUser = TokenUtils.getCurrentUser();
        sign.setUserId(currentUser.getId());
        Sign s=this.selectByActivityIdAndUserId(sign.getActivityId(),currentUser.getId());
        if (s == null) {
            activitySignMapper.insert(sign);
        } else {
            activitySignMapper.del(currentUser.getId(),sign.getActivityId());
        }
    }
    public boolean f(Integer actId){
        user currentUser = TokenUtils.getCurrentUser();
        Sign s=this.selectByActivityIdAndUserId(actId,currentUser.getId());
        return s!=null;
    }



}
