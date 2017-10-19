package com.yunma.vo.collectWord;


public class CollectWordVo {
	
	private Integer id;
	
	private Integer order_id; //订单id
	
	private Integer product_id; //产品id
	
	private Integer rule_id; //规则id
	
	private String invalid_time; //失效时间
	
	private String effect_time; //生效时间
	
	private String name; //规则名称
	
	private Integer vendor_id; //厂商id
	
	private String prize_item; //奖项中具体的字
	
	private String create_time; //创建时间
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(Integer vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getPrize_item() {
		return prize_item;
	}

	public void setPrize_item(String prize_item) {
		this.prize_item = prize_item;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}

	public String getInvalid_time() {
		return invalid_time;
	}

	public void setInvalid_time(String invalid_time) {
		this.invalid_time = invalid_time;
	}

	public String getEffect_time() {
		return effect_time;
	}

	public void setEffect_time(String effect_time) {
		this.effect_time = effect_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	

}
