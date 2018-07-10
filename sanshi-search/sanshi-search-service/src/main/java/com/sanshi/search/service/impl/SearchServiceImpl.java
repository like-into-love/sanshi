package com.sanshi.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanshi.result.SearchResult;
import com.sanshi.search.dao.SearchDao;
import com.sanshi.search.service.SearchService;

@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {

	@Autowired
	SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) {
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		query.setQuery(queryString);
		// 设置分页查询
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 设置查询的默认搜索域
		query.set("df", "item_all");

		// 开启高亮
		query.setHighlight(true);
		// 前缀
		query.setHighlightSimplePre("<span style=\"color:red;font-size:16px\">");
		query.setHighlightSimplePost("</span>");
		// 设置高亮域
		query.addHighlightField("item_title");
		// 执行查询
		SearchResult search = searchDao.search(query);
		// 补全信息
		// 当前页
		search.setCurPage(page);
		// 总页数
		search.setPageCount((search.getRecordCount() + rows - 1) / rows);
		return search;
	}

}
