package com.sanshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.cms.service.TbContentService;
import com.sanshi.pojo.TbContent;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;

@Controller
@RequestMapping("content")
public class ContentController {
	@Autowired
	TbContentService tbContentService;

	// 根据id查询内容
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGtidResult selectTbContentAll(long categoryId) {
		EasyUIDataGtidResult result = tbContentService.selectTbContentAll(categoryId);
		return result;

	}

	// 添加新的内容
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult addTbContent(TbContent tbContent) {
		TaotaoResult result = tbContentService.addTbContent(tbContent);
		return result;
	}

	// 根据id删除这条内容
	@PostMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteTbContent(@RequestParam("ids") long id) {
		TaotaoResult result = tbContentService.delTbContent(id);
		return result;

	}

}
