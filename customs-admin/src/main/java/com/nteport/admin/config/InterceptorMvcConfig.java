package com.nteport.admin.config;

import com.nteport.admin.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class InterceptorMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserArgumentResolver loginUserArgumentResolver;

    /**
     * 将自定义的拦截器放入spring context容器中
     *
     * @return
     */
    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // token 验证拦截器,注册spring 容器中的拦截器对象
        registry.addInterceptor(getRequestInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

}
