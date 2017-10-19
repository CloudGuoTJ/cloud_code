package com.yunma.model;

import java.io.Serializable;

public class MapDistrictHot implements Serializable {
	private String longitude;
	private String latitude;
	private String scanAddress;
	private String securityCode;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getScanAddress() {
		return scanAddress;
	}

	public void setScanAddress(String scanAddress) {
		this.scanAddress = scanAddress;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
}
