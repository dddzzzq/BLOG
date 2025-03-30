package com.example.databases.Common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.databases.entity.user;
import com.example.databases.exception.ServiceException;
import com.example.databases.mapper.UserMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//构建拦截器
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
        String token=request.getHeader("token");//header里面传来的参数
        if(StrUtil.isBlank(token)){
            token=request.getParameter("token");//url参数
        }
        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException("401","请登录");
        }
        //获取token中的userId
        String userId;
        try{
            userId= JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new ServiceException("401","请登录");
        }
        //根据token中的userId查询数据库
        user user=userMapper.judge2(userId);
        if(user == null)
        {
            throw new ServiceException("401","请登录");
        }
        //用户密码加签验证token
        //通过用户密码加密之后生成一个验证器
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);//验证token
        }catch (JWTVerificationException e){
            throw new ServiceException("401","请登录");
        }
        return true;
    }
}
