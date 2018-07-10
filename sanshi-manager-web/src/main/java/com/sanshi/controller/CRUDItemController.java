package com.sanshi.controller;

import com.sanshi.result.TaotaoResult;
import com.sanshi.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping("rest/item")
public class CRUDItemController {
	@Autowired
	private TbItemService tbItemService;

	// 商品下架
	@PostMapping("/instock")
	@ResponseBody
	public TaotaoResult itemRemove(String ids) {
		if (ids != null) {
			String[] split = ids.split(",");
			List<Long> id = new ArrayList<Long>();
			for (String string : split) {
				id.add(Long.parseLong(string));
			}
			// 商品状态，1-正常，2-下架，3-删除'
			tbItemService.updateItemStatus(2, id);
			return TaotaoResult.resultStatus(200);
		}
		return TaotaoResult.resultStatus(100);
	}

	// 商品上架
	@PostMapping("/reshelf")
	@ResponseBody
	public TaotaoResult itemReshelf(String ids) {
		if (ids != null) {
			String[] split = ids.split(",");
			List<Long> id = new ArrayList<Long>();
			for (String string : split) {
				id.add(Long.parseLong(string));
			}
			// 商品状态，1-正常，2-下架，3-删除'
			tbItemService.updateItemStatus(1, id);
			return TaotaoResult.resultStatus(200);
		}
		return TaotaoResult.resultStatus(100);
	}

	// 商品刪除
	@PostMapping("/delete")
	@ResponseBody
	public TaotaoResult itemDelete(String ids) {
		if (ids != null) {
			String[] split = ids.split(",");
			List<Long> id = new ArrayList<Long>();
			for (String string : split) {
				id.add(Long.parseLong(string));
			}
			// 商品状态，1-正常，2-下架，3-删除'
			tbItemService.updateItemStatus(3, id);
			return TaotaoResult.resultStatus(200);
		}
		return TaotaoResult.resultStatus(100);
	}
}
