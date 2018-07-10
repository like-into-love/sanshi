package com.sanshi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanshi.mapper.TbItemCatMapper;
import com.sanshi.pojo.TbItemCat;
import com.sanshi.result.EasyUITreeNode;
import com.sanshi.service.TbItemCatService;

@Service("tbItemCatServiceImpl")
public class TbItemCatServiceImpl implements TbItemCatService {

	@Autowired
	TbItemCatMapper tbItemCatMapper;
	
	@Override
	//查询商品分类并以树形结构展示
	public List<EasyUITreeNode> getCatList(long parentId) {
		List<TbItemCat> catList = tbItemCatMapper.getCatList(parentId);
		List<EasyUITreeNode> list=new ArrayList<EasyUITreeNode>();
		for(TbItemCat tbItemCat :catList){
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			list.add(node);
		}
		return list;
	}

}
