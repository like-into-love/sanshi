package com.sanshi.service;

import java.util.List;

import com.sanshi.result.EasyUITreeNode;

public interface TbItemCatService {
	//根据parentId查询节点列表
		public List<EasyUITreeNode> getCatList(long parentId);

}
