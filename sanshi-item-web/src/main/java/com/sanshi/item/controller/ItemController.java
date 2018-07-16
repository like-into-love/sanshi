package com.sanshi.item.controller;

import com.sanshi.item.pojo.Item;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
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
    public String getItemAndItemDesc(@PathVariable long itemId, Model model) {
        TbItem tbItem = tbItemService.getTbItemById(itemId);
        Item item = new Item(tbItem);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping("item/desc/{itemId}")
    @ResponseBody
    public String getTbItemDesc(@PathVariable long itemId) {
        TbItemDesc itemDesc = tbItemService.getTbItemDescById(itemId);
        return itemDesc.getItemDesc();
    }

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
