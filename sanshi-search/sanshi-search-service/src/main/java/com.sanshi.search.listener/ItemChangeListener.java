package com.sanshi.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.sanshi.search.service.SolrItemService;

public class ItemChangeListener implements MessageListener {

    @Autowired
    SolrItemService solrItemService;

    /**
     * 在发送队列消息的时候最好带上一个类型，这样才能判断是新添加，还是更新，删除，分别调用不同的方法更新索引库
     * 如1代表新添加，2，更新，3删除
     *
     * @param message
     */
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage age = (TextMessage) message;
            String itemId;
            try {
                itemId = age.getText();
                String[] split = itemId.split(",");
                //添加新商品索引
                if ("1".equals(split[1])) {
                    solrItemService.importByIdItems(Long.valueOf(split[0]));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

    }

}
