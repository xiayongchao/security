package com.xyc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by xyc on 2017/8/12 0012.
 */
@Controller
@RequestMapping("/jsp")
public class JspController {
    @GetMapping("/test")
    public String test(Map<String, Object> model) {
        model.put("text", "hello jsp");
        model.put("list", Arrays.asList(1, 2, 3, 4, 5));
        return "test";
    }
}
