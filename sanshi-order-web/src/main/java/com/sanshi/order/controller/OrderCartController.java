package com.sanshi.order.controller;

import com.sanshi.order.pojo.Order;
import com.sanshi.order.service.OrderService;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbUser;
import com.sanshi.result.TaotaoResult;
import com.sanshi.utils.CookieUtils;
import com.sanshi.utils.JsonUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderCartController {
    @Value("${TT_CART}")
    private String TT_CART;
    @Autowired
    private OrderService orderService;

    /**
     * 获取 购物车cookie取出购物车购物项显示到订单页面，在这之前
     * 1.先判断用户是否登陆，没有登陆进行拦截回到登陆页面
     * http://localhost:8091/order/order-cart.html
     *
     * @param request
     * @return
     */
    @RequestMapping("/order-cart")
    public String showOrderCartItem(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        //判断如果为空就返回一个空的list集合，有值就返回本身
        request.setAttribute("cartList", tbItems == null ? new ArrayList<TbItem>() : tbItems);
        return "order-cart";
    }

    ///order/create.html
    @RequestMapping("/create")
    public String createOrder(Order order, HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("user");
        //补全一些信息
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        TaotaoResult result = orderService.createOrder(order);
        if (result.getStatus() != 200) {
            request.setAttribute("500", "下单失败，请稍后重试");
            return "error/exception";
        }
        //页面上需要的内容
        //订单号
        request.setAttribute("orderId", result.getData().toString());
        //总金额
        request.setAttribute("payment", order.getPayment());
        //估计商品的到达时间设置三天后到期
        request.setAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
        return "success";
    }
}
