package com.sanshi.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("page")
public class PageController {

    /**
     * 点击注册按钮跳转到注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

    /**
     * 点击登陆按钮跳转登陆页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }


}
