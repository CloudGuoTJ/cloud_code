package com.yunma.entity.vendor;

import java.io.Serializable;

public class VendorBrandImgs implements Serializable{
	
	private Integer id;
	
	private String imgUrl;
	
	private String imgName;
	
	private int imgWidth;
	
	private int imgheight;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgheight() {
		return imgheight;
	}

	public void setImgheight(int imgheight) {
		this.imgheight = imgheight;
	}
	


}
