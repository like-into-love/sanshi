package com.sanshi.search.mapper;

import java.util.List;

import com.sanshi.pojo.SolrItem;

public interface SolrItemMapper {
	/**
	 * 查询出solr需要的索引字段
	 * 
	 * @return SolrItem 对象
	 */
	public List<SolrItem> getSolrItem();

	/**
	 * 索引同步，根据消息队列接收的itemId条件，查询新添加的商品，加入到索引库
	 *
	 * @return
	 */
	public SolrItem getByIdItems(long itemId);
}
