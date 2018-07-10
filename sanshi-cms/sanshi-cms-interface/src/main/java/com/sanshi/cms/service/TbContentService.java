package com.sanshi.cms.service;

import java.util.List;

import com.sanshi.pojo.TbContent;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;

public interface TbContentService {
	
	/**
	 * 
	 * @param categoryId 分类id
	 * @return 页面需要EasyUIDataGtidResult对象里面的 row,total
	 */
	public EasyUIDataGtidResult selectTbContentAll(long categoryId);
	/**
	 * 添加新的内容
	 * @param tbContent 
	 * @return 返回TaotaoResult
	 */
	public TaotaoResult addTbContent(TbContent tbContent);
	
	/**
	 * 前台根据categoryId查询内容
	 */
	public List<TbContent> getIndexAdList(long categoryId);
	
	/**
	 * 根据id删除这条内容
	 * @param id
	 */
	public TaotaoResult delTbContent(long id);
 
}
