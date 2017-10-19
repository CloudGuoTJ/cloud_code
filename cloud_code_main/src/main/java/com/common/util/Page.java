package com.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 * @param <T>
 */
public class Page<T> {
	private int page = 1; // 当前页码
	private int size = 30; // 页面大小，设置为“-1”表示不进行分页（分页无效）
	private long count;// 总记录数，设置为“-1”表示不查询总数
	private String field;//需要查询的字段
	private String conditions;//查询条件
	private List<T> list = new ArrayList<T>();

	public Page(){}

	public Page(int page, int size) {
		this.page = page;
		this.size = size;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page <= 0 ? 10 : page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
		if (size >= count){
			page = 1;
		}
	}

	/**
	 * 获取页面总数
	 * @return
	 */
	public int getTotalPage() {
		return count > size ? (int) count / size : 1;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getConditions() {

		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
