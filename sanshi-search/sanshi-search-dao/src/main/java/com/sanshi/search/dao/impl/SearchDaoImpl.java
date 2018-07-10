package com.sanshi.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sanshi.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanshi.pojo.SolrItem;
import com.sanshi.result.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	HttpSolrClient solrServer;

	public SearchResult search(SolrQuery query) {
		SearchResult search = new SearchResult();
		try {
			// 执行查询
			QueryResponse queryResponse = solrServer.query(query);
			// 获取查询结果
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			// 获取高亮显示
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<SolrItem> list = new ArrayList<SolrItem>();
			for (SolrDocument solrDocument : solrDocumentList) {
				SolrItem item = new SolrItem();
				item.setId(solrDocument.get("id").toString());

				/**
				 * "highlighting":{ "6c593940-f952-4ec1-971d-ea44efdd5ed9":{
				 * "field_name":["家天下天使<span>钻石</span>心钥匙扣"]},
				 */
				// 高亮显示标题
				List<String> list2 = highlighting.get(solrDocument.get("id")).get("item_title");
				if (list2 != null && list2.size() > 0) {
					item.setTitle(list2.get(0));
				} else {
					item.setTitle(solrDocument.get("item_title").toString());
				}
				item.setCategoryName(solrDocument.get("item_category_name").toString());
				item.setImage(solrDocument.get("item_image").toString());
				item.setPrice((Double) solrDocument.get("item_price"));
				item.setSellPoint(solrDocument.get("item_sell_point").toString());
				list.add(item);
			}
			search.setList(list);
			// 总记录数
			search.setRecordCount(solrDocumentList.getNumFound());
			return search;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
