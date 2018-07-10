package com.sanshi.mapper;

import com.sanshi.pojo.TbItemCat;

import java.util.List;

public interface TbItemCatMapper {
	
	//根据parentId查询节点列表
	public List<TbItemCat> getCatList(long parentId);
	
}