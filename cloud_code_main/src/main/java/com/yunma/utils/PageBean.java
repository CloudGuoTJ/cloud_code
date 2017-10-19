package com.yunma.utils;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 分页实体
 */
public class PageBean {

	private Integer totalRecords;	//总记录数
	private Integer totalPages;		//总页数
	private Integer pageSize;		//每页条数
	private Integer index;			//每页对应索引
	private Integer currentPage;	//当前页
	private List<?> list;			//数据集
	private JSONObject result;		//数据集
	
	public PageBean() {
		this.pageSize = 10;
		this.currentPage = 1;
		this.index = 0;
	}
	
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
		
		if (this.pageSize != null && totalRecords != null) {
			this.totalPages = (int)Math.ceil(this.totalRecords/(double)this.pageSize);
		}
		
		if (currentPage < 1) {
			currentPage = 1;
		} else if(this.totalPages!=null) {
			if (currentPage >= this.totalPages) {
					currentPage = this.totalPages;
			}
		} else {
			this.currentPage = currentPage;
		}
		
		
		if (currentPage != null && currentPage != 0) {
			index = (currentPage-1)*pageSize;
		}
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	private void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		
		if (this.pageSize != null && totalPages != null) {
			this.totalPages = (int)Math.ceil(this.totalRecords/(double)this.pageSize);
		}
		
		if (currentPage != null && currentPage != 0) {
			index = (currentPage-1)*pageSize;
		}
		
	}
	public Integer getIndex() {
		return index;
	}

	private void setIndex(Integer index) {
		this.index = index;
	}

	public Integer getCurrentPage() {
		currentPage = currentPage<1?1:currentPage;
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		
		if (currentPage < 1) {
			currentPage = 1;
		}else if(this.totalPages!=null){
			 if (currentPage > this.totalPages) {
					currentPage = this.totalPages;
				}
		} else {
			this.currentPage = currentPage;
		}
		
		if (pageSize != null) {
			index = (currentPage-1)*pageSize;
		}
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	
}
