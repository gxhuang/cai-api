package com.cai.bean;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{

	private long totalRecord; // 总纪录数
	private int pageSize = 10; // 每页多少条数据
	private long totalPage;

	private int currentPage; // 用户想哪一页
	private int startIndex; // 根据用户想看的页，算出该页在数据库的起始位置

	private long startPage; // jsp页面的起始页码
	private long endPage; // jsp页面的结束页码

	private List<T> records; // 保存页面数据
						// ，这里假如显示的是Book所有的记录，list里面封装的的是很多book数据。相当于getALLBook();

	public Page(int currentPage, long totalRecord) {
		this.totalRecord = totalRecord;
		this.currentPage = currentPage;

		// 构造函数根据用户传递进来的总纪录数和页号，算出总页数，和该页在数据库的起始位置
		// 1.算出总页数
		if (this.totalRecord % this.pageSize == 0) {
			this.totalPage = this.totalRecord / this.pageSize;
		} else {
			this.totalPage = this.totalRecord / this.pageSize + 1;
		}

		// 2.算出页号在数据库的起始位置，不同数据库中不一样，mysql查询出来的结果是这个位置的下一个数据。从这个的下一个开始
		this.startIndex = (this.currentPage - 1) * this.pageSize;

		// 3.算出jsp页面的起始页码和结束页码，小于10个，全显示，大于10个，只显示10个
		if (this.totalPage <= 10) {
			this.startPage = 1;
			this.endPage = this.totalPage;
		} else {

			this.startPage = this.currentPage - 4;
			this.endPage = this.currentPage + 5;

			if (this.startPage < 1) {
				this.startPage = 1;
				this.endPage = 10;
			}
			if (this.endPage > this.totalPage) {
				this.endPage = this.totalPage;
				this.startPage = this.totalPage - 9;
			}
		}
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
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

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public long getStartPage() {
		return startPage;
	}

	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}

	public long getEndPage() {
		return endPage;
	}

	public void setEndPage(long endPage) {
		this.endPage = endPage;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}


	

}
