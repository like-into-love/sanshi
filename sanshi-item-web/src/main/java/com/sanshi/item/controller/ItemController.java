package com.sanshi.item.controller;

import com.sanshi.item.pojo.Item;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    TbItemService tbItemService;

    @RequestMapping("item/{itemId}")
    public String getItemAndItemDesc(@PathVariable long itemId, Model model) {
        TbItem tbItem = tbItemService.getTbItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc itemDesc = tbItemService.getTbItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }

}
