package com.sanshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemParamService;

@Controller
@RequestMapping("item/param")
public class ItemParamController {

	@Autowired
	TbItemParamService tbItemParamService;

	@RequestMapping("/query/itemcatid/{cid}")
	@ResponseBody
	// 查询该类商品模版是否存在，存在则不创建，不存在则创建
	public TaotaoResult getItemParamByCid(@PathVariable long cid) {
		TaotaoResult taotaoResult = tbItemParamService.selectTbItemParam(cid);
		return taotaoResult;
	}

	// 保存创建模板 接收商品分类id和模板参数json，插入的商品规则参数表
	@RequestMapping(value = "/save/{cid}", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable long cid, String paramData) {
		TaotaoResult taotaoResult = tbItemParamService.insertItemParam(cid, paramData);
		return taotaoResult;

	}

}
