package com.sanshi.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanshi.result.SearchResult;
import com.sanshi.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;

	@RequestMapping("search")
	public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "60") int rows, Model model) throws Exception {
		/*try {
			System.out.println(1/0);
		} catch (Exception e) {
			throw new Exception();
		}*/
		if (StringUtils.isNotBlank(queryString)) {
			// 回显搜索名字
			model.addAttribute("query", queryString);
			SearchResult search = searchService.search(queryString, page, rows);
			// 总页数
			model.addAttribute("totalPages", search.getPageCount());
			// 页面要显示的数据
			model.addAttribute("itemList", search.getList());
			// 当前页
			model.addAttribute("page", page);
		}
		return "search";
	}

}
