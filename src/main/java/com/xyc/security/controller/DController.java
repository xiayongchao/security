package com.xyc.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xyc on 2017/8/10 0010.
 */
@Controller
@RequestMapping("/d")
public class DController {
    @GetMapping("/test")
    public String test() {
        new String("fadsfas");
        return "test";
    }
}
