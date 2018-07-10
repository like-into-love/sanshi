package com.sanshi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.result.EasyUITreeNode;
import com.sanshi.service.TbItemCatService;

@Controller
@RequestMapping("item/cat")
public class ItemCatController {

	@Autowired
	TbItemCatService tbItemCatService;

	
	/**
	 * 商品分类查询 页面的类目
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "0") long parentId) {
	  
		List<EasyUITreeNode> catList = tbItemCatService.getCatList(parentId);
		return catList;
	}

}
