package com.blackbread.utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination {
	public List<?> valuesList;
	public long totalCount;
	public int pageNo;
	public int pageSize;
	public int start;
	public int end;
	public int limit;
	public long totalPage;
	public List<Integer> pages;
	public final int maxShowPage = 5;

	public Pagination() {
	}

	public Pagination(int pageNo, int limit) {
		if (pageNo < 1 || limit < 0)
			throw new RuntimeException("分页参数异常");
		this.start = (pageNo - 1) * limit + 1;
		this.limit = limit;
		this.pageNo = pageNo;
		this.pageSize = limit;
		this.end = pageNo * limit;
	}

	public Pagination(String pageNo, String limit) {
		try {
			int pNumber = Integer.valueOf(pageNo);
			int pSize = Integer.valueOf(limit);
			if (pNumber < 1 || pSize < 0)
				throw new RuntimeException("分页参数异常");
			this.start = (pNumber - 1) * pSize + 1;
			this.limit = pSize;
			this.pageNo = pNumber;
			this.pageSize = pSize;
			this.end = pNumber * pSize;
		} catch (NumberFormatException e) {
			throw new RuntimeException("分页参数异常");
		}
	}

	public void instance() {
		if (this.pageNo < 1 || this.pageSize < 0)
			throw new RuntimeException("分页参数异常");
		if(this.pageSize==0)
			this.pageSize=10;
		this.start = (this.pageNo - 1) * this.pageSize + 1;
		this.limit = this.pageSize;
		this.end = pageNo * limit;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<?> getValuesList() {
		return valuesList;
	}

	public void setValuesList(List<?> valuesList) {
		this.valuesList = valuesList;
	}

	public long getTotalCount() {
		return totalCount;
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

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
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
