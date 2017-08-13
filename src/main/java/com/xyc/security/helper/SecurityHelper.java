package com.xyc.security.helper;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xyc on 2017/8/9 0009.
 */
public class SecurityHelper {
    private final static Logger logger = LoggerFactory.getLogger(SecurityHelper.class);

    /**
     * 解析源码
     *
     * @param mpMap
     * @param className
     * @throws ClassNotFoundException
     */
    public static void analyseClassPathCode(Map<Module, List<Permission>> mpMap, String className) throws ClassNotFoundException {
        Class cls = Class.forName(className);
        if (!cls.isAnnotationPresent(Controller.class) && !cls.isAnnotationPresent(RestController.class)) {
            return;
        }
        if (!cls.isAnnotationPresent(Module.class)) {   //没有权限模块配置
            return;
        }
        Module module = (Module) cls.getAnnotation(Module.class);
        if (mpMap.get(module) != null) {
            return;
        }
        List<Permission> permissionList = new ArrayList<>();
        Method[] methods = cls.getDeclaredMethods();
        if (methods == null || methods.length == 0) {   //没有权限项配置
            mpMap.put(module, permissionList);
            return;
        }
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Permission.class)) {
                continue;
            }
            permissionList.add(method.getAnnotation(Permission.class));
        }
        mpMap.put(module, permissionList);
    }

    /**
     * 扫描源码
     *
     * @param mpMap
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void scanSecurity(Map<Module, List<Permission>> mpMap) throws URISyntaxException, IOException {
        ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();    //获取类加载器
        URL[] urls = ((URLClassLoader) classLoader).getURLs();  //获取项目的所有文件路径URL
        if (urls == null || urls.length == 0) { //如果项目下文件路径为空则返回
            return;
        }
        for (URL url : urls) {
            final Path path = Paths.get(url.toURI());
            if (!path.toFile().isDirectory()) {
                continue;
            }
            //如果是目录则代表是我们自己写的代码，进行解析
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException { //正在访问一个文件时要干啥
                    String fileName = file.getFileName().toString();
                    if (!fileName.endsWith(".class") || fileName.contains("$")) {    //$后面跟数字是匿名类编译出来的/$后面跟文字是内部类编译出来的
                        return FileVisitResult.CONTINUE;    //继续遍历
                    }
                    String classPathName = file.toUri().toString().split(path.toUri().toString())[1]; //class path name com/xyc/permission/web/AController.class
                    String className = classPathName.substring(0, classPathName.lastIndexOf(".")).replace('/', '.');  //class name    com.xyc.permission.web.AController
                    try {
                        SecurityHelper.analyseClassPathCode(mpMap, className);
                    } catch (ClassNotFoundException e) {
                        logger.error("解析权限异常", e);
                    }
                    return FileVisitResult.CONTINUE;    //继续遍历
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException { //访问一个文件失败时要干啥
                    return FileVisitResult.CONTINUE;    //继续遍历
                }
            });
        }
    }
}
