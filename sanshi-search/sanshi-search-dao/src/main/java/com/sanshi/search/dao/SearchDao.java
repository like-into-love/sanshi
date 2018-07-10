package com.sanshi.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.sanshi.result.SearchResult;
 

//不能与放在mapper包下面因为scanner扫mapper包会加入mybatis里面
//会包org.apache.ibatis.binding.BindingException: Invalid bound statement
//自定义不属于mapper接口
public interface SearchDao {
	/**
	 * 根据添加查询Solr索引库
	 * 
	 * @param query
	 *            业务层封装的条件
	 * @return SearchResult
	 */
	public SearchResult search(SolrQuery query);

}
