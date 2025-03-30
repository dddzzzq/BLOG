package com.example.databases.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.databases.entity.user;
import com.example.databases.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

//生成token
@Component
public class TokenUtils {
    private static UserMapper staticUserMapper;
    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService(){
        staticUserMapper=userMapper;
    }
    /**
     * 生成Token
     * @aurhor Iscream
     */
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时后Token自动过期
                .sign(Algorithm.HMAC256(sign)); //以sign作为秘钥
    }

    /**
     *获取当前登录用户的信息
     * @author Iscream
     */
    public static user getCurrentUser(){
        try{
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("token");
            if(StrUtil.isNotBlank(token)){
                String userId=JWT.decode(token).getAudience().get(0);
                return staticUserMapper.judge2(userId);
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}
