package com.xyc.security.controller;

import com.xyc.security.annotation.Module;
import com.xyc.security.annotation.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xyc on 2017/8/9 0009.
 */
@RestController
@RequestMapping("/c")
@Module(name = "模块C", value = "C")
public class CController {
    @GetMapping("/add")
    @Permission(name = "添加", value = "add")
    public String add() {
        return "添加C";
    }

    @GetMapping("/delete")
    @Permission(name = "删除", value = "delete")
    public String delete() {
        return "删除C";
    }

    @GetMapping("/update")
    @Permission(name = "修改", value = "update")
    public String update() {
        return "修改C";
    }

    @GetMapping("/query")
    @Permission(name = "查询", value = "query")
    public String query() {
        return "查询C";
    }
}
