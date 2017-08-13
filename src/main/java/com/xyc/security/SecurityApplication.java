package com.xyc.security;

import com.xyc.security.interceptor.SecurityInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.xyc.security.filter")/*扫描过滤器*/
public class SecurityApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { //注册权限拦截器
        registry.addInterceptor(new SecurityInterceptor());
    }
}
