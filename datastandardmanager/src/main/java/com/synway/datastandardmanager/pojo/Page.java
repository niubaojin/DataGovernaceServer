package com.synway.datastandardmanager.pojo;


public class Page {
	private Integer page=1;
	private Integer pageSize=15;
	private Integer todelRows;//��ҳ��
	private Integer total;//����
	private Integer state;//����״̬
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getTodelRows() {
		return todelRows;
	}
	public void setTodelRows(Integer todelRows) {
		this.todelRows = todelRows;
	}
	public Integer getBegin(){
		return (page-1)*pageSize;
	}
	public Integer getEnd(){
		return page*pageSize+1;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
