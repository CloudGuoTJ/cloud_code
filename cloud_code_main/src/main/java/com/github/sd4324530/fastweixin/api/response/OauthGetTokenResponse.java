package com.github.sd4324530.fastweixin.api.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Oauth授权获取token接口响应对象
 * @author peiyu
 */
public class OauthGetTokenResponse extends GetTokenResponse {

    @JSONField(name = "refresh_token")
    private String refreshToken;
    
    @JSONField(name = "access_token")
    private String accessToken;

    private String openid;

    private String scope;

    private String unionid;
    
    @Override
	public String toString() {
		return "OauthGetTokenResponse [refreshToken=" + refreshToken
				+ ", accessToken=" + accessToken + ", openid=" + openid
				+ ", scope=" + scope + ", unionid=" + unionid + "]";
	}

	public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
