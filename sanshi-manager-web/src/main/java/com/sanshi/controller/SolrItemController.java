package com.sanshi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanshi.result.TaotaoResult;
import com.sanshi.search.service.SolrItemService;

@Controller
public class SolrItemController {
	@Autowired
	SolrItemService solrItemService;
	
	@PostMapping("search/manager/importall")
	@ResponseBody
	public TaotaoResult ImportAllIndex(){
		TaotaoResult result = solrItemService.importAllItems();
		return result;
		
	}

}
