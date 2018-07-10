package com.sanshi.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanshi.pojo.SolrItem;
import com.sanshi.result.TaotaoResult;
import com.sanshi.search.mapper.SolrItemMapper;
import com.sanshi.search.service.SolrItemService;

@Service("solrItemServiceImpl")
public class SolrItemServiceImpl implements SolrItemService {

	@Autowired
	SolrItemMapper solrItemMapper;
	@Autowired
	HttpSolrClient solrServer;

	@Override
	public TaotaoResult importAllItems() {
		List<SolrItem> solrItem = solrItemMapper.getSolrItem();
		//写入索引库
		for (SolrItem item : solrItem) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.setField("id", item.getId());
			doc.setField("item_title", item.getTitle());
			doc.setField("item_sell_point",("".equals(item.getSellPoint())||item.getSellPoint()==null)?"这个没有没卖点":item.getSellPoint());
			doc.setField("item_price", item.getPrice());
			doc.setField("item_image", item.getImage());
			doc.setField("item_category_name", item.getCategoryName());
			doc.setField("item_desc", item.getItemDesc());
			try {
				solrServer.add(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			//提交修改
			solrServer.commit();
		} catch (Exception e) {
			return TaotaoResult.build(500, "导入索引出错");
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult importByIdItems(long itemId) {
		SolrItem item = solrItemMapper.getByIdItems(itemId);

		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", item.getId());
		doc.setField("item_title", item.getTitle());
		doc.setField("item_sell_point", ("".equals(item.getSellPoint())||item.getSellPoint()==null)?"这个没有没卖点":item.getSellPoint());
		doc.setField("item_price", item.getPrice());
		doc.setField("item_image", item.getImage());
		doc.setField("item_category_name", item.getCategoryName());
		doc.setField("item_desc", item.getItemDesc());
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (Exception e) {
			return TaotaoResult.build(500, "导入索引出错");
		}
		return TaotaoResult.ok();
	}

}
