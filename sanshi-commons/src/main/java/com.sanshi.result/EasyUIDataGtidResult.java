package com.sanshi.result;

import java.io.Serializable;
import java.util.List;
/**
 * 页面显示多少条数据的工具类
 * @author wu_lei
 *
 */
public class EasyUIDataGtidResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long page;
	private List<?> rows;

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "ResultItem [page=" + page + ", rows=" + rows + "]";
	}

	public EasyUIDataGtidResult() {
		super();
	}

}
