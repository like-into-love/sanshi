package com.sanshi.sso.controller;

import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;
import com.sanshi.sso.service.UserService;
import com.sanshi.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public TaotaoResult checkData(String userName, String passWord, HttpServletRequest req, HttpServletResponse resp) {
        TaotaoResult result;
        if (StringUtils.isBlank(userName)) {
            result = TaotaoResult.build(400, "用户名不能为空", null);
            return result;
        }
        if (StringUtils.isBlank(passWord)) {
            result = TaotaoResult.build(400, "密码不能为空", null);
            return result;
        }
        TaotaoResult result1 = userService.userLogin(userName, passWord);
        //判断是否登陆成功
        if (result1.getStatus() == 200) {
            //从后返回的结果中取出token
            String token = result1.getData().toString();
            //设置到cookie中前台在cookie中取出用户名
            CookieUtils.setCookie(req, resp, COOKIE_TOKEN_KEY, token);
            return result1;
        }
        return result1;
    }

    //根据token取出用户信息
    @GetMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        MappingJacksonValue jsonp = new MappingJacksonValue(result);
        //返回jsonp函数名
        jsonp.setJsonpFunction(callback);
        return jsonp;
    }

    /**
     * 退出登陆
     *
     * @return
     */
    @RequestMapping("/logout/{token}")
    public String showLogout(@PathVariable String token, HttpServletResponse resp, HttpServletRequest req) {
        //清除cookie
        CookieUtils.deleteCookie(req, resp, COOKIE_TOKEN_KEY);
        //清除redis
        userService.showLogout(token);
        return "login";
    }
}
