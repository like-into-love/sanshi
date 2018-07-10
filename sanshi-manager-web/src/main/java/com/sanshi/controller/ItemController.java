package com.sanshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.pojo.TbItem;
import com.sanshi.pojo.TbItemDesc;
import com.sanshi.result.EasyUIDataGtidResult;
import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemService;

@Controller
@RequestMapping("item")
public class ItemController {

	@Autowired
	private TbItemService tbItemService;

	/*
	 * @RequestMapping(value="/item/{id}")
	 * 
	 * @ResponseBody
	 * //http://localhost:8080/taotao-manager-web/item/536563直接输入id值不用书id=536563
	 * public TbItem findTbItem(@PathVariable("id") long id) {
	 * System.out.println("--------------------------会不会"+id); TbItem findTbItem
	 * = tbItemService.findTbItem(id); return findTbItem;
	 * 
	 * }
	 */
	/**
	 * 分页展示
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGtidResult findTbItem(@RequestParam("page") int page, @RequestParam("rows") int rows) {
		System.out.println("--------------------------会不会" + page + "===" + rows);
		EasyUIDataGtidResult findAllTbItem = tbItemService.findAllTbItem(page, rows);
		return findAllTbItem;
	}

	/**
	 * 添加商品
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc,String itemParams) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		TaotaoResult addTbItem = tbItemService.addTbItem(item, itemDesc,itemParams);
		System.out.println("状态==============》" + addTbItem.getStatus());
		return addTbItem;

	}

}
