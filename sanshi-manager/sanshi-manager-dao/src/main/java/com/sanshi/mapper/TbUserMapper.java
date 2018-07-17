package com.sanshi.mapper;


import com.sanshi.pojo.TbUser;
import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {
    /**
     * 根据用户名查询用户是否存在
     *
     * @param userName
     * @return
     */
    TbUser getTbUserByName(String userName);

    /**
     * 根据用户输入的手机号码查询手机号码是否存在
     *
     * @param phone
     * @return
     */
    TbUser getTbUserByPhone(String phone);

    /**
     * 根据用户输入的邮箱查询是否邮箱存在
     *
     * @param email
     * @return
     */
    TbUser getTbUserByEmail(String email);

    /**
     * 添加一个行用户
     *
     * @param tbUser
     */
    void insertTbUser(TbUser tbUser);

    /**
     * 登陆查询
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
    TbUser getTbUser(@Param("username") String username, @Param("password") String password);
}