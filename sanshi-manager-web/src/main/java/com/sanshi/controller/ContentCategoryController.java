package com.sanshi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.cms.service.TbContentCategoryService;
import com.sanshi.result.EasyUITreeNode;
import com.sanshi.result.TaotaoResult;

@Controller
@RequestMapping("content/category")
public class ContentCategoryController {

	@Autowired
	TbContentCategoryService tbContentCategoryService;

	/**
	 * 查询内容分类
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getTbContentCategory(@RequestParam(value = "id", defaultValue = "0") long parentId) {
		List<EasyUITreeNode> result = tbContentCategoryService.findTbContentCategoryById(parentId);
		return result;
	}

	/**
	 * 
	 * @param parentId
	 * @param name
	 * @return 添加新的内容
	 */
	@PostMapping("/create")
	@ResponseBody
	public TaotaoResult addTbContentCategory(long parentId, String name) {
		TaotaoResult result = tbContentCategoryService.addTbContentCategory(parentId, name);
		return result;

	}

}
