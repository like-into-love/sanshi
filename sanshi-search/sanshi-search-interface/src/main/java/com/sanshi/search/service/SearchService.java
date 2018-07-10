package com.sanshi.search.service;

import com.sanshi.result.SearchResult;

public interface SearchService {
	/**
	 * 
	 * @param queryString 前台传来的搜索条件
	 * @param page 总共多少页
	 * @param rows 每页显示的条数
	 * @return
	 */
	public SearchResult search(String queryString, int page, int rows);
}
