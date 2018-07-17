package com.sanshi.rest.pojo;

import java.util.List;

/**
 * 商品类目查询结果CatResult
 * 
 * @author wu_lei
 *
 */
public class CatResult {
	private List<?> data;

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CatResult [data=" + data + "]";
	}

}
