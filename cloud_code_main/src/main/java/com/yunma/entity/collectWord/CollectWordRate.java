package com.yunma.entity.collectWord;

public class CollectWordRate {

	private Integer id;
	
	private String word; //字
	
	private Integer rule_id; //规则id
	
	private Integer rule_item_id; //奖项id
	
	private Double word_rate; //字的中奖概率

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

	public Integer getRule_item_id() {
		return rule_item_id;
	}

	public void setRule_item_id(Integer rule_item_id) {
		this.rule_item_id = rule_item_id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Double getWord_rate() {
		return word_rate;
	}

	public void setWord_rate(Double word_rate) {
		this.word_rate = word_rate;
	}

	
}
