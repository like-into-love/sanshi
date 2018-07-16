package com.sanshi.item.listener;

import com.sanshi.item.pojo.Item;
import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.service.TbItemService;
import com.sun.javafx.collections.MappingChange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ItemAddListener implements MessageListener {
    @Autowired
    TbItemService tbItemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void onMessage(Message message) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            if (message instanceof TextMessage) {
                TextMessage age = (TextMessage) message;
                String itemId = age.getText();
                //商品
                TbItem tbItem = tbItemService.getTbItemById(Long.valueOf(itemId));
                TbItemDesc tbItemDesc = tbItemService.getTbItemDescById(Long.valueOf(itemId));
                String paramItem = tbItemService.getTbItemParamItemByid(Long.valueOf(itemId));
                Map map = new HashMap();
                map.put("item", new Item(tbItem));
                map.put("itemDesc", tbItemDesc.getItemDesc());
                map.put("itemParam", paramItem);
                BufferedWriter bf = new BufferedWriter(new FileWriter("E:\\test\\" + itemId + ".html"));
                template.process(map, bf);
                bf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
