package com.xyc.security.annotation;

import java.lang.annotation.*;

/**
 * 权限模块注解
 * Created by xyc on 2017/8/9 0009.
 */
@Documented //作为被标注的程序成员的公共API，可以被例如javadoc此类的工具文档化
@Target(ElementType.TYPE)   //用于描述类、接口(包括注解类型) 或enum声明
@Retention(RetentionPolicy.RUNTIME) //在运行时有效（即运行时保留）
public @interface Module {
    String name();

    String value();
}
