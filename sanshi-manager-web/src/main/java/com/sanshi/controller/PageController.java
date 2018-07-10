package com.sanshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class PageController {
	//访问直接跳转到首页
	@RequestMapping("/")
	public String Index() {
		return "index";
	}

	@RequestMapping("/{location}")
	// 点击首页相应的菜单，返回相应的页面
	public String PageLocation(@PathVariable("location") String location) {
		System.out.println(location);
		return location;

	}

}
