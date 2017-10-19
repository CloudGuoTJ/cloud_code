package com.yunma.entity.product;

/**
 * 商品规格
 */
public class ProductRule {

	private Integer id;			//
	private Integer iteId;		//字典项id
	private Integer productId;	//产品id
	private Float price;		//价格
	private Integer totle;		//库存
	private Integer number;		//销量
	private String code;		//商品编码
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIteId() {
		return iteId;
	}
	public void setIteId(Integer iteId) {
		this.iteId = iteId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getTotle() {
		return totle;
	}
	public void setTotle(Integer totle) {
		this.totle = totle;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
