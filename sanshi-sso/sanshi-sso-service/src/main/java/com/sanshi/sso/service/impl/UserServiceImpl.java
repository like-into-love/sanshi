package com.sanshi.sso.service.impl;

import com.sanshi.mapper.TbUserMapper;
import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;
import com.sanshi.sso.jedis.JedisClient;
import com.sanshi.sso.service.UserService;
import com.sanshi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;
    @Autowired
    JedisClient jedisClient;

    @Autowired
    TbUserMapper tbUserMapper;

    @Override
    public TaotaoResult checkData(String param, int type) {
        //根据type查询相应的SQL
        if (type == 1) {
            //查询用户名
            TbUser tbUser = tbUserMapper.getTbUserByName(param);
            //判断是否为空，不为空用户以为注册，
            if (tbUser != null) {
                return TaotaoResult.build(400, "用户名以存在", false);
            }
        } else if (type == 2) {
            TbUser tbUser = tbUserMapper.getTbUserByPhone(param);
            if (tbUser != null) {
                return TaotaoResult.build(400, "手机号码以存在", false);
            }
        } else if (type == 3) {
            TbUser tbUser = tbUserMapper.getTbUserByEmail(param);
            if (tbUser != null) {
                return TaotaoResult.build(400, "邮箱以存在", false);
            }
        } else {
            return TaotaoResult.build(400, "非法参数", false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult addTbUser(TbUser tbUser) {
        TaotaoResult result;
        if (StringUtils.isBlank(tbUser.getUsername())) {
            result = TaotaoResult.build(400, "用户名不能为空", null);
            return result;
        }
        if (StringUtils.isBlank(tbUser.getPassword())) {
            result = TaotaoResult.build(400, "密码不能为空", null);
            return result;
        }
        if (StringUtils.isBlank(tbUser.getPhone())) {
            result = TaotaoResult.build(400, "手机号码不能为空", null);
            return result;
        }
        if (StringUtils.isBlank(tbUser.getEmail())) {
            result = TaotaoResult.build(400, "邮箱不能为空", null);
            return result;
        }
        //如果上面都不成功就查询数据库
        result = checkData(tbUser.getUsername(), 1);
        //判断如果查询的结果是false说明用户名存在
        if (!(Boolean) result.getData()) {
            return result;
        }
        result = checkData(tbUser.getPhone(), 2);
        //判断如果查询的结果是false说明手机号码存在
        if (!(Boolean) result.getData()) {
            return result;
        }
        result = checkData(tbUser.getEmail(), 3);
        //判断如果查询的结果是false说明邮箱存在
        if (!(Boolean) result.getData()) {
            return result;
        }
        //补全信息
        tbUser.setUpdated(new Date());
        tbUser.setCreated(new Date());
        //密码加密Spring框架自带MD5 加密工具类 DigestUtils
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        //如果上面都不成立，就说名数据插入数据库执行SQL
        tbUserMapper.insertTbUser(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password) {
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        TbUser tbUser = tbUserMapper.getTbUser(username, password);
        //判断是否登陆成功
        if (tbUser == null) {
            return TaotaoResult.build(500, "登陆失败");
        }
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        //把密码清空，安全
        tbUser.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY + token, JsonUtils.objectToJson(tbUser));
        //设置过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + token, SSO_SESSION_EXPIRE);
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get("REDIS_USER_SESSION_KEY" + token);
        //如果为空token以过期
        if (StringUtils.isBlank(json)) {
            TaotaoResult.build(400, "此session已过期，请重新登陆");
        }
        //不为空更新存储时间
        jedisClient.expire("REDIS_USER_SESSION_KEY + token", SSO_SESSION_EXPIRE);
        //如果直接返回TaotaoResult.ok(json),json里面的引号会在页面返回是添上引号
        //所有要把它转成对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }


    @Override
    public void showLogout(String token) {
      jedisClient.del("REDIS_USER_SESSION_KEY"+token);
    }

}
