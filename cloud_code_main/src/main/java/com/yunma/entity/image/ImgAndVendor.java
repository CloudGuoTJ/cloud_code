package com.yunma.entity.image;

import java.io.Serializable;

public class ImgAndVendor implements Serializable{
	
	private Integer id;
	
	private Integer img_id;
	
	private Integer vendor_detail_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getImg_id() {
		return img_id;
	}

	public void setImg_id(Integer img_id) {
		this.img_id = img_id;
	}

	public Integer getVendor_detail_id() {
		return vendor_detail_id;
	}

	public void setVendor_detail_id(Integer vendor_detail_id) {
		this.vendor_detail_id = vendor_detail_id;
	}
	
	

}
