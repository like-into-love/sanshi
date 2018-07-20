package com.sanshi.item.controller;

import com.sanshi.item.pojo.Item;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.pojo.TbItemFreemarker;
import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    TbItemService tbItemService;

    @RequestMapping("item/{itemId}")
    //根据搜索页面传来的商品itemId查询商品
    public String getItemAndItemDesc(@PathVariable long itemId, Model model) {
        //先查看此商品是否有静态模版，如果有就重定向该路径
        TbItemFreemarker tbItemFreemarker = tbItemService.getTbItemFreemarkerById(itemId);
        if (tbItemFreemarker != null) {
            String path = tbItemFreemarker.getItemFreemarkerSrc();
            //这样的，model会在地址栏自动拼接参数
           // model.addAttribute("item", itemId);
            return "redirect:" + path;
        } else {
            //如果没有模板野做了redis缓存
            TbItem tbItem = tbItemService.getTbItemById(itemId);
            Item item = new Item(tbItem);
            model.addAttribute("item", item);
            return "item";
        }
    }

    //根据itemId查询商品详情
    @RequestMapping("item/desc/{itemId}")
    @ResponseBody
    public String getTbItemDesc(@PathVariable long itemId) {
        TbItemDesc itemDesc = tbItemService.getTbItemDescById(itemId);
        return itemDesc.getItemDesc();
    }

    //根据商品id查询商品的规格参数
    @RequestMapping("item/param/{itemId}")
    @ResponseBody
    public String getTbItemParam(@PathVariable long itemId) {
        String string = "";
        try {
            string = tbItemService.getTbItemParamItemByid(itemId);
        } catch (Exception e) {
            new Exception();
        }
        return string;
    }

}
