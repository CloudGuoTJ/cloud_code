package com.yunma.vo.image;

import java.io.Serializable;

public class ImageVo implements Serializable {

	private Integer id;
	private Integer vendorId;
	private String imgType;
	private String imgUrl;
	private String imgName;
	private Integer imgWidth;
	private Integer imgHeight;
	private Integer rowId;
	private String rowName;
	private String keyword;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
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
	public Integer getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(Integer imgWidth) {
		this.imgWidth = imgWidth;
	}
	public Integer getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(Integer imgHeight) {
		this.imgHeight = imgHeight;
	}
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
