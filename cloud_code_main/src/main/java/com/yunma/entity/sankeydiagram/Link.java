package com.yunma.entity.sankeydiagram;

public class Link {
	
	private Integer agentFid;
	
	private String source;
	
	private Integer agentId;
	
	private String target;
	
	private double value;
	
	private int currentProCount;
	
	private int sourceProCount;
	

	
	public int getSourceProCount() {
		return sourceProCount;
	}

	public void setSourceProCount(int sourceProCount) {
		this.sourceProCount = sourceProCount;
	}

	public int getCurrentProCount() {
		return currentProCount;
	}

	public void setCurrentProCount(int currentProCount) {
		this.currentProCount = currentProCount;
	}

	public Integer getAgentFid() {
		return agentFid;
	}

	public void setAgentFid(Integer agentFid) {
		this.agentFid = agentFid;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	
}
