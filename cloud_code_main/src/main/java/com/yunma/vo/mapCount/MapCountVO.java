package com.yunma.vo.mapCount;

import java.io.Serializable;

/**
 * 用于扫码构建首页hotMap 采用json方式存储地理位置信息
 * 
 * @author Administrator
 * 
 */

public class MapCountVO implements Serializable {
	private String name;
	private int choise;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return choise;
	}

	public void setValue(int value) {
		this.choise = value;
	}

}
