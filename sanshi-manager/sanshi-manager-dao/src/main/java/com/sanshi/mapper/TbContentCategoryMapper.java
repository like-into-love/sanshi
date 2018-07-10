package com.sanshi.mapper;

import com.sanshi.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryMapper {

	/**
	 * 根据parentId查询内容下面的内容
	 * @param parentId
	 * @return
	 */
	public List<TbContentCategory> findTbContentCategoryById(long parentId);


	/**
	 * 添加新的内容
	 * @param
	 * @param
	 * @return
	 */
	public void insetTbContentCategory(TbContentCategory tbContentCategory);
	
	public TbContentCategory selectByPrimaryKey(long parentId);
	
	public void updateByPrimaryKey(TbContentCategory tbContentCategory);
	

}