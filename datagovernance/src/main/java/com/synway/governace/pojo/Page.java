package com.synway.governace.pojo;

import java.util.List;


public class Page {
	private int dataCount;//总数据量
	private int pageSize;//每页的大小
	private int pageNum;//页码
	private int pageCount;//页总数
	private List<?> dataList;
	
	public Page(){
		System.out.println("------------------------------------");
	}
	
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	@Override
	public String toString() {
		return "Page [dataCount=" + dataCount + ", pageSize=" + pageSize
				+ ", pageNum=" + pageNum + ", pageCount=" + pageCount
				+ ", dataList=" + dataList + "]";
	}

	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
}
