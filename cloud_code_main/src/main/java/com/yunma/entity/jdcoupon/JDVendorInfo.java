package com.yunma.entity.jdcoupon;

public class JDVendorInfo {
	
	private Integer id;
	
	private Integer vendorId;
	
	private String access_token;
	
	private Integer code;
	
	private long expires_in;//有效时间
	
	private String refresh_token;
	
	private String time;//创建时间
	
	private String token_type;
	
	private String uid;//商户id
	
	private String user_nick;//商户昵称
	
	JDVendorInfo() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUser_nick() {
		return user_nick;
	}

	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}

	@Override
	public String toString() {
		return "JDVendorInfo [access_token=" + access_token + ", code=" + code
				+ ", expires_in=" + expires_in + ", refresh_token="
				+ refresh_token + ", time=" + time + ", token_type="
				+ token_type + ", uid=" + uid + ", user_nick=" + user_nick
				+ "]";
	}
	
	
}
