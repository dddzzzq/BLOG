package com.example.databases.Common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//拦截器配置规则
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    protected void addInterceptors(InterceptorRegistry registry){
/*    registry.addInterceptor(jwtInterceptor())//配置jwt拦截规则
                .addPathPatterns("/**")
                .excludePathPatterns("/judge")
               .excludePathPatterns("/file/download/*");*/
                                                //请求拦截所有的请求路径

        super.addInterceptors(registry);
    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
