package com.xyc.security.controller;

import com.xyc.security.bo.LoginUserInfo;
import com.xyc.security.common.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xyc on 2017/8/13 0013.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/login")
    public String login(String module, String permission, HttpSession session) {
        if (module != null && !module.isEmpty() && permission != null && !permission.isEmpty()) {
            session.setAttribute(Constant.LOGIN_USER_INFO, new LoginUserInfo(new HashMap<String, List<String>>() {    //将权限放入session中
                {
                    put(module, Arrays.asList(permission));
                }
            }));
        }
        return "index";
    }
}
