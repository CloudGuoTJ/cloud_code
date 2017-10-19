package com.yunma.vo.collectWord;


public class CollectWordRateJson {
	
	private Integer ratgeId; //字id
	
	private Integer ruleId; //规则id
	
	private String word; //字
	
	private Double word_rate; //字的中奖概率
	
	

	public Integer getRatgeId() {
		return ratgeId;
	}


	public void setRatgeId(Integer ratgeId) {
		this.ratgeId = ratgeId;
	}



	public Integer getRuleId() {
		return ruleId;
	}



	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
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
