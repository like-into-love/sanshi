package com.sanshi.exceptoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义全局异常类
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionReslover.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        //写入日志文件
        logger.error("系统异常", ex);
        mv.addObject("message", "系统异常,请稍后重试。");
        mv.setViewName("error/exception");
        return mv;
    }
}
