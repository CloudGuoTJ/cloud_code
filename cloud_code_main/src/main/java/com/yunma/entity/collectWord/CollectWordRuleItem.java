package com.yunma.entity.collectWord;

public class CollectWordRuleItem {

	
	private Integer id;
	
	private Integer rule_id; //规则id
	
	private String prize_name; //奖项
	
	private String prize_item; //奖项中具体的字
	
	private int prize_is_sort; //是否有序
	
	private String prize_comment; //奖品描述


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRule_id() {
		return rule_id;
	}

	public void setRule_id(Integer rule_id) {
		this.rule_id = rule_id;
	}

	public String getPrize_name() {
		return prize_name;
	}

	public void setPrize_name(String prize_name) {
		this.prize_name = prize_name;
	}

	public String getPrize_item() {
		return prize_item;
	}

	public void setPrize_item(String prize_item) {
		this.prize_item = prize_item;
	}

	public int getPrize_is_sort() {
		return prize_is_sort;
	}

	public void setPrize_is_sort(int prize_is_sort) {
		this.prize_is_sort = prize_is_sort;
	}

	public String getPrize_comment() {
		return prize_comment;
	}

	public void setPrize_comment(String prize_comment) {
		this.prize_comment = prize_comment;
	}
	
	
}
