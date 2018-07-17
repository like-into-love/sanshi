package com.sanshi.rest.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *   {
          "u": "/products/11.html",
          "n": "英文原版",
          "i": [
            "/products/12.html|少儿",
            "/products/13.html|商务投资",
            "/products/14.html|英语学习与考试",
            "/products/15.html|小说",
            "/products/16.html|传记",
            "/products/17.html|励志"
          ]
        }
 * 
 * @author wu_lei 创建商品分类的节点类CatNode
 *
 */
public class CatNode {

	@JsonProperty("u") // 装成json的名字
	private String url;
	@JsonProperty("n")
	private String name;
	/**
	 * 子节点。子节点若为叶节点则为String，若非叶节点则为CatNode
	 */
	@JsonProperty("i")
	private List<?> items;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<?> getitems() {
		return items;
	}

	public void setitems(List<?> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CatNode [url=" + url + ", name=" + name + ", items=" + items + "]";
	}

}
