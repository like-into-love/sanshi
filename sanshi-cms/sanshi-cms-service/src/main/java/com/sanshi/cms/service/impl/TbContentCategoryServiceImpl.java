package com.sanshi.cms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanshi.cms.service.TbContentCategoryService;
import com.sanshi.mapper.TbContentCategoryMapper;
import com.sanshi.pojo.TbContentCategory;
import com.sanshi.result.EasyUITreeNode;
import com.sanshi.result.TaotaoResult;

@Service("tbContentCategoryServiceImpl")
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

	@Autowired
	TbContentCategoryMapper tbContentCategoryMapper;

	// 根据parentId查询内容下面的内容
	@Override
	public List<EasyUITreeNode> findTbContentCategoryById(long parentId) {

		List<TbContentCategory> findTbContentCategorys = tbContentCategoryMapper.findTbContentCategoryById(parentId);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory tbContentCategory : findTbContentCategorys) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			result.add(node);
		}
		return result;
	}

	// 添加新的商品内容
	@Override
	public TaotaoResult addTbContentCategory(long parentId, String name) {
		// 补全商品信息
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setName(name);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setSortOrder(1);
		// 状态值：1，正常；2，删除。用于逻辑删除，暂时不用
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		tbContentCategoryMapper.insetTbContentCategory(tbContentCategory);
		
		//查看父节点，如果父节点是叶节点，则改为非叶节点
		TbContentCategory selectByPrimaryKey = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		//如果为假，则改为非叶节点
		if(!selectByPrimaryKey.getIsParent()){
			selectByPrimaryKey.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
          }
		//上面的逻辑走完，把tbContentCategory返回个页面
		return TaotaoResult.ok(tbContentCategory);
	}

}
