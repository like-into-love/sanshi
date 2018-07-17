package com.sanshi.sso.service;

import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;

public interface UserService {
    /**
     * 定义一个接口根据传来的参数查询这些用户输入的注册信息是否可用
     *
     * @param param 传来的参数值
     * @param type  1,代表查询用户名 2，代表查询手机号码 3，代表查询邮件
     * @return
     */
    TaotaoResult checkData(String param, int type);

    /**
     * 添加一个行用户
     *
     * @param tbUser
     */
    TaotaoResult addTbUser(TbUser tbUser);

    /**
     * 用户登陆
     *
     * @param username 账号
     * @param password 密码
     * @return
     */

    TaotaoResult userLogin(String username, String password);

    /**
     * 接收token，根据token到redis中取用户信息
     *
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 用户注销要删除cookie和redis
     * @param token
     */
    void showLogout(String token);


}
