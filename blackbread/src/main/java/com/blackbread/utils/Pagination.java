package com.blackbread.utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	private List<?> items;
	private long totalCount;
	private int pageNo;
	private int pageSize;
	private long totalPage;
	private List<Integer> pages;
	private int maxShowPage = 5;

	public Pagination() {
	}

	public Pagination(int pageNo, int pageSize) {
		if (pageNo < 1 || pageSize < 1)
			throw new RuntimeException("分页参数异常");
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Pagination(int pageNo, int pageSize, int showPages) {
		if (pageNo < 1 || pageSize < 1 || showPages < 1)
			throw new RuntimeException("分页参数异常");
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.maxShowPage = showPages;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize
				: (totalCount / pageSize + 1);
		int startPage = (this.pageNo / this.maxShowPage) * this.maxShowPage + 1;
		int endPage = (this.pageNo / this.maxShowPage + 1) * this.maxShowPage;
		pages = new ArrayList<Integer>();
		for (int i = startPage; i <= endPage; i++) {
			pages.add(i);
			if (i >= this.totalPage)
				break;
		}
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

	public int getMaxShowPage() {
		return maxShowPage;
	}

	public void setMaxShowPage(int maxShowPage) {
		this.maxShowPage = maxShowPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

}
