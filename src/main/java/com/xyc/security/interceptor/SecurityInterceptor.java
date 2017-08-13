package com.xyc.security.interceptor;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;
import com.xyc.security.bo.LoginUserInfo;
import com.xyc.security.common.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限拦截器，用于访问拦截进行权限排查
 * Created by xyc on 2017/8/13 0013.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在请求处理之前（Controller方法调用之前）进行调用
     *
     * @param request
     * @param response
     * @param handler
     * @return 只有返回true才会继续向下执行，返回false取消当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 在之前的博客中我们已经接触过HandlerMethod，接下来我们简单介绍一下HandlerMethod，简单来说HandlerMethod包含的信息包括类、方法和参数的一个信息类，
         * 通过其两个构造函数我们就可以了解其功能，对应着springMVC的Controller来说就是某个url对应的某个Controller执行的方法。
         */
        if (handler.getClass() == HandlerMethod.class) {    //判断是否为Controller请求
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class cls = handlerMethod.getBeanType();
            if (cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(RestController.class)) {   //是否是Controller
                Module module = (Module) cls.getAnnotation(Module.class);
                if (module != null) {   //如果有权限模块注解则进行权限判断
                    LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession(true).getAttribute(Constant.LOGIN_USER_INFO);  //获取登录用户信息
                    if (loginUserInfo == null || loginUserInfo.getMpInfoMap() == null || loginUserInfo.getMpInfoMap().isEmpty()) { //如果登录用户或者用户权限为空则取消当前请求
                        return false;
                    }

                    List<String> permissionList = loginUserInfo.getMpInfoMap().get(module.value());
                    if (permissionList == null || permissionList.isEmpty()) {    //登录用户权限模块的权限列表为空则取消当前请求
                        return false;
                    }
                    if (permissionList.contains(handlerMethod.getMethodAnnotation(Permission.class).value())) { //登录用户拥有此权限则继续向下执行
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
