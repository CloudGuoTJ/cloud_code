package com.yunma.vo.exceptionAntiFake;

import java.io.Serializable;

public class ExceptionAntiFakeVO implements Serializable{
	private String securityCode;
	private String scanCount;
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getScanCount() {
		return scanCount;
	}
	public void setScanCount(String scanCount) {
		this.scanCount = scanCount;
	}
	

}
