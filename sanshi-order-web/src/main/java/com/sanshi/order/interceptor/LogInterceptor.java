package com.sanshi.order.interceptor;

import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;
import com.sanshi.sso.service.UserService;
import com.sanshi.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;
    @Value("${LOGIN_BASE_URL}")
    private String LOGIN_BASE_URL;
    @Autowired
    private UserService userService;

    /**
     * 在执行handle之前进行拦截判断是否登陆没有登陆跳转到登陆页面
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, "COOKIE_TOKEN_KEY");
        //过去当前请求的全路径，登陆成功返回到前路径；
        StringBuffer requestURL = request.getRequestURL();
        //如果为空跳转登陆页面不放行
        if (StringUtils.isBlank(token)) {
            response.sendRedirect(LOGIN_BASE_URL + "?redirectUrl=" + requestURL);
            return false;
        }
        //不为空,调用getUserByToken查看token是否过期，没有过期返回200，过期400,跳转登陆页面
        TaotaoResult result = userService.getUserByToken(token);
        if (result.getStatus() == 400) {
            response.sendRedirect(LOGIN_BASE_URL + "?redirectUrl=" + requestURL);
            return false;
        }
        //放行，执行订单的controller
        if (result.getStatus() == 200) {
            TbUser user = (TbUser) result.getData();
            //因为拦截的是/order/** 当经过这个拦截的方法都会有request域，主要是生成订单时候关联用户
            request.setAttribute("user", user);
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //返回modelAndView之前拦截，添加一些全局的内容返回回去
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //返回modelAndView之后拦截一般统计用户什么时候登陆，什么时候退出 也可以保存日志

    }
}
