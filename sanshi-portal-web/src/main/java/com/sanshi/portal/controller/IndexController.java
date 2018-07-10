package com.sanshi.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanshi.cms.service.TbContentService;
import com.sanshi.pojo.TbContent;
import com.sanshi.portal.pojo.IndexAd;
import com.sanshi.utils.JsonUtils;

@Controller
public class IndexController {

	@Value("${HEIGHT}")
	private Integer HEIGHT;
	@Value("${HEIGHT_B}")
	private Integer HEIGHT_B;
	@Value("${WIDTH}")
	private Integer WIDTH;
	@Value("${WIDTH_B}")
	private Integer WIDTH_B;
	@Value("${CATEGORY_ID}")
	private Long CATEGORY_ID;

	@Autowired
	private TbContentService tbContentService;

	@RequestMapping("index")
	public String showIndex(Model model){

		List<TbContent> indexAdList = tbContentService.getIndexAdList(CATEGORY_ID);
		List<IndexAd> result = new ArrayList<IndexAd>();
		for (TbContent tbContent : indexAdList) {
			IndexAd ad = new IndexAd();
			// 大屏幕尺寸
			ad.setHeight(HEIGHT);
			ad.setWidth(WIDTH);
			ad.setAlt(tbContent.getTitleDesc());
			ad.setSrc(tbContent.getPic());
			ad.setHref(tbContent.getUrl());
			// 小屏幕尺寸
			ad.setHeightB(HEIGHT_B);
			ad.setWidthB(WIDTH_B);
			ad.setSrcB(tbContent.getPic2());
			result.add(ad);
		}
		// 返回给页面json数据，把集合装换成字符串
		model.addAttribute("ad1", JsonUtils.objectToJson(result));

		return "index";
	}

}
