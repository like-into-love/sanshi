package com.sanshi.cart.controller;

import com.sanshi.cart.pojo.CartItem;
import com.sanshi.pojo.TbItem;
import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemService;
import com.sanshi.utils.CookieUtils;
import com.sanshi.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
public class ItemCartController {
    @Value("${TT_CART}")
    private String TT_CART;
    @Value("${TT_ITEM}")
    private Integer TT_ITEM;
    @Autowired
    TbItemService tbItemService;

    @RequestMapping("/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(value = "num", defaultValue = "1") Integer num,
                              HttpServletRequest req, HttpServletResponse resp) {

        //也可以自己封装一个CartItem  实体类显示购物车需要的信息
        List<TbItem> items = getCartItemList(req);
        //循环，判断是否添加的商品已经在购物车中，在购物车中重新赋值数量
        boolean flay = false;

        for (TbItem tbItem : items) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItem.setNum(tbItem.getNum() + num);
                flay = true;

                break;
            }
        }
        //如果购物车中没有此商品，根据itemId查询商品信息
        if (!flay) {
            TbItem tbItem = tbItemService.getTbItemById(itemId);
            tbItem.setNum(num);
            tbItem.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
            items.add(tbItem);
        }
        //因为购物车中有中文设置true
        CookieUtils.setCookie(req, resp, TT_CART, JsonUtils.objectToJson(items), true);

        return "cartSuccess";
    }

    private List<TbItem> getCartItemList(HttpServletRequest req) {
        //根据cookie名字查询cookie购物车商品json 数据
        String json = CookieUtils.getCookieValue(req, TT_CART, true);
        if (StringUtils.isBlank(json)) {
            return new ArrayList();
        }
        List<TbItem> tbItems = JsonUtils.jsonToList(json, TbItem.class);
        return tbItems;
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @RequestMapping("/cart")
    public String showCartItem(HttpServletRequest request) {
        String json = CookieUtils.getCookieValue(request, TT_CART, true);
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        request.setAttribute("cartList", list);
        return "cart";
    }

    /**
     * 对商品数量的++和--操作 查看cart.js
     * cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val()
     *
     * @param itemId   商品id
     * @param num      前台操作后的数量
     * @param req
     * @param response
     * @return
     */
    @PostMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updataNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest req, HttpServletResponse response) {
        List<TbItem> tbItems = getCartItemList(req);
        for (TbItem tbItem : tbItems) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItem.setNum(num);
            }
        }
        //修改后重新保存到cookie
        CookieUtils.setCookie(req, response, TT_CART, JsonUtils.objectToJson(tbItems), true);
        return TaotaoResult.ok();
    }
    //"/cart/service/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val()

    /**
     * 直接在输入框中修改数量
     *
     * @param itemId   商品id
     * @param num      修改后的数量
     * @param req
     * @param response
     * @return
     */
    @PostMapping("/service/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult serviceupdataNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest req, HttpServletResponse response) {
        List<TbItem> tbItems = getCartItemList(req);
        for (TbItem tbItem : tbItems) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItem.setNum(num);
                break;
            }
        }
        //修改后重新保存到cookie
        CookieUtils.setCookie(req, response, TT_CART, JsonUtils.objectToJson(tbItems), true);
        return TaotaoResult.ok();
    }

    /**
     * 从购物车中删除此商品
     * cart/delete/${cart.id}.html
     *
     * @param itemId   商品id
     * @param req
     * @param response
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest req, HttpServletResponse response) {
        List<TbItem> tbItems = getCartItemList(req);
        for (TbItem tbItem : tbItems) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItems.remove(tbItem);
                break;
            }
        }
        //修改后重新保存到cookie
        CookieUtils.setCookie(req, response, TT_CART, JsonUtils.objectToJson(tbItems), true);
        //重定向去求情查看购物车请求
        return "redirect:/cart/cart.html";
    }
}
