package com.sanshi.mapper;

import com.sanshi.pojo.TbContent;

import java.util.List;

public interface TbContentMapper {
	/**
	 * 
	 * @param categoryId
	 * @return 根据分类id查询内容
	 */
	public List<TbContent> getTbContentAll(long categoryId);

	/**
	 * 插入一条新的内容
	 * 
	 * @param tbContent
	 */
	public void insertTbContent(TbContent tbContent);
	
	/**
	 * 根据id删除这条内容
	 * @param id
	 */
	public void deleteTbContent(long id);

}