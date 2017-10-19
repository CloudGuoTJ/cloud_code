package com.yunma.vo.mapCount;

import java.io.Serializable;
public class WeekScanCountEveDayVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String days;
	private Integer coun;
	

	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public Integer getCoun() {
		return coun;
	}
	public void setCoun(Integer coun) {
		this.coun = coun;
	}


}
