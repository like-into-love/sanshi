package com.sanshi.cms.service;

import java.util.List;

import com.sanshi.result.EasyUITreeNode;
import com.sanshi.result.TaotaoResult;

public interface TbContentCategoryService {

	/**
	 * 根据parentId查询内容下面的内容
	 * 
	 * @param parentId
	 * @return
	 */
	List<EasyUITreeNode> findTbContentCategoryById(long parentId);
 
	/**
	 * 添加新的内容
	 * @param parentId
	 * @param name
	 * @return
	 */
	TaotaoResult addTbContentCategory(long parentId, String name);

}
