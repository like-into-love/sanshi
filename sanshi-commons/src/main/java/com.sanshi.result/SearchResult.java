package com.sanshi.result;

import java.io.Serializable;
import java.util.List;

import com.sanshi.pojo.SolrItem;

/**
 * 前台查询的工具类，总记录数，总页数，商品集合，当前页
 * 
 * @author wu_lei
 *
 */
public class SearchResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 查询结果列表
	private List<SolrItem> list;
	// 总记录数
	private long recordCount;
	// 总页数
	private long pageCount;
	// 当前页
	private long curPage;

	public List<SolrItem> getList() {
		return list;
	}

	public void setList(List<SolrItem> list) {
		this.list = list;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public long getCurPage() {
		return curPage;
	}

	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

	@Override
	public String toString() {
		return "SearchResult [list=" + list + ", recordCount=" + recordCount + ", pageCount=" + pageCount + ", curPage="
				+ curPage + "]";
	}

}
