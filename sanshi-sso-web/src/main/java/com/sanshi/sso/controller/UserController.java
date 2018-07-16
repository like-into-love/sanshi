package com.sanshi.sso.controller;

import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;
import com.sanshi.sso.service.UserService;
import com.sanshi.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    //校验数据是否可用
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkData(@PathVariable String param, @PathVariable int type) {
        TaotaoResult result = userService.checkData(param, type);
        return result;
    }

    //添加用户
    @PostMapping("/register")
    @ResponseBody
    public TaotaoResult addUser(TbUser tbUser) {
        TaotaoResult taotaoResult = userService.addTbUser(tbUser);
        return taotaoResult;
    }

    //用户登陆
    @PostMapping("/login")
    @ResponseBody
    public TaotaoResult checkData(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
        TaotaoResult result;
        if (StringUtils.isBlank(username)) {
            result = TaotaoResult.build(400, "用户名不能为空", null);
            return result;
        }
        if (StringUtils.isBlank(password)) {
            result = TaotaoResult.build(400, "密码不能为空", null);
            return result;
        }
        TaotaoResult result1;
        try {
            result1 = userService.userLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, "登陆异常");
        }
        //从后返回的结果中取出token
        String token = result1.getData().toString();
        //设置到cookie中
        CookieUtils.setCookie(req, resp, COOKIE_TOKEN_KEY, token);
        return result1;
    }

    //根据token取出用户信息
    @GetMapping("/token/{token}")
    @ResponseBody
    public TaotaoResult getUserByToken(@PathVariable String token) {
        TaotaoResult result = userService.getUserByToken(token);
        return result;
    }
}
