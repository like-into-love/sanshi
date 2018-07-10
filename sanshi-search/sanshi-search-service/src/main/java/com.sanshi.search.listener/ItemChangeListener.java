package com.sanshi.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.sanshi.search.service.SolrItemService;

public class ItemChangeListener implements MessageListener{
	
	@Autowired
	SolrItemService solrItemService;

	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			TextMessage age=(TextMessage) message;
			String itemId;
			try {
				itemId = age.getText();
				solrItemService.importByIdItems(Long.valueOf(itemId));
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}

}
