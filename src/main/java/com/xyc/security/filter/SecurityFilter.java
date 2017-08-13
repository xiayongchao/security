package com.xyc.security.filter;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;
import com.xyc.security.helper.SecurityHelper;
import com.xyc.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 过滤器，进行权限初始化
 * Created by xyc on 2017/8/9 0009.
 */
@WebFilter
public class SecurityFilter implements Filter {
    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Resource
    private SecurityService securityService;

    /**
     * init方法在项目启动时会执行一次，所以在这里使用过滤器的init方法来进行权限的初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   //启动服务器时加载过滤器的实例，并调用init()方法来初始化实例
        Thread thread = new Thread(() -> {  //使用多线程执行，不影响主线程工作
            Map<Module, List<Permission>> mpMap = new HashMap<>();
            try {
                logger.info("开始解析权限");
                SecurityHelper.scanSecurity(mpMap);
                logger.info("开始保存权限");
                this.securityService.saveSecurity(mpMap);
            } catch (URISyntaxException | IOException e) {
                logger.error("加载权限信息异常", e);
                throw new RuntimeException(e);
            }
        });
        thread.setDaemon(true); //守护线程
        thread.start();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
