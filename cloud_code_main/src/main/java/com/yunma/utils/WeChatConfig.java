package com.yunma.utils;
/**
 * 微信配置信息文件
 * @author 
 *
 */
public class WeChatConfig {
	//public static final String APP_ID = "wx4e8cf1716ea239ef";//云码互联物流扫的App_Id
	
	public static final String APP_ID = "wx2247791bde684872";//云码技术平台 appId
	public static final String SECRET = "7aee8a2fea17dd54dbad4235d5680090";//云码技术平台 spring配置文件中 也有配置 修改时请注意
	public static final String TOKEN = "xiyanggzh";//云码技术平台的Token
	public static final String MCH_ID = "1446334302";	//微信支付商户号
	public static final String API_KEY = "0u0Y0m2J3ymkiyfb24v38yuc45gsjQAQ";//微信支付 签名密钥
	//oauth2授权接口(GET)
	public static final String WX_HTTp = "https://open.weixin.qq.com/connect/oauth2/authorize";
	public static final String DOMAIN_NAME = "ym-a.top";//域名
	public static final String PROJECT_NAME ="cloud_code";//项目名
	
	//微信回调统一跳转错误显示页面
	public static final String  ERROR_HTML = "redirect:https://"+DOMAIN_NAME+"/wx/error.html";
	
	//微信支付发送红包统一接口(POST)
	public final static String SEND_RED_PACKETS_URL="https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	//微信红包发送调用接口的服务器Ip地址
	public final static String CLIENT_IP = "120.77.154.187";
	
	//https证书位置
	public final static String CERTIFICATE_LOCATION = "/root/credentials/apiclient_cert.p12";
	
	//用户上传证书存放地址
	public final static String USER_CREDENTIALS_LOCATION = "/credentials";
	
	//客服接口 - 发消息
	public final static String CUSTOM_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={ACCESS_TOKEN}";
	
	/*------------------------------------------微信开放平台配置文件open.weixin.qq.com-------------------------------------------------------------------------------*/
	
	//开放平台appid(云码互联数字运营平台)
	public final static String OPEN_APPID = "wxb41e9be2f174588e";
	
	//开放平台密钥(云码互联数字运营平台)
	public final static String OPEN_APPSECRET = "16490589b152f7841b3461022c5d200d";
	
	//公众号消息加解密Key(云码互联数字运营平台)
	public final static String OPEN_ENCODINGAESKEY = "rwSplPWn3hLlcKb1mRupCudQDJnsSeyxNujGDHQzrsw";
	
	/* ----------------------------------------微信开发平台配置文件（云码互联公众号管理平台）---------------------------------------------------------------------------------*/
	//开发平台appid
	public final static String open_appid_ = "wx639e8bfda5be58eb";
	
	//开发平台密钥
	public final static String open_AppSecret_ = "5f13322aeba784aa8530e6e27884f087";
	
	/*------------------------------------------云码技术配置文件-------------------------------------------------------------------------------*/
	//云码技术 appid
	public final static String YMJS_APP_ID = "wx2247791bde684872";
	
	//云码技术 密钥
	public final static String YMJS_SECRET = "7aee8a2fea17dd54dbad4235d5680090";
	
	//云码技术token
	public final static String YMJS_TOKEN = "xiyanggzh";

	//云码技术 app商户号
	public final static String YMJS_MCH_ID = "1446334302";
	
	//云码技术 api key
	public final static String YMJS_API_KEY = "0u0Y0m2J3ymkiyfb24v38yuc45gsjQAQ";
	
	//消息加解密密钥
	public final static String YMJSS_ENCODING_AES_KEY = "rwSplPWn3hLlcKb1mRupCudQDJnsSeyxNujGDHQzrsw";
	
	
	/*------------------------------------------微店配置文件-------------------------------------------------------------------------------*/
	//微店appkey
	public final static String WD_APPKEY = "682394";
	
	// 微店secret
	public final static String WD_SECRET = "8e3e6f48625f2dac99718b5b89e3bb44";
	
	//微店api接口
	public final static String WD_API = "https://api.vdian.com/api";

	//微店授权 获取accessToken接口
	public final static String WD_ACCESS_TOKEN = "https://oauth.open.weidian.com/token?grant_type=client_credential&appkey={appkey}&secret={secret}";

	
	/*------------------------------------------微信支付接口(沙盒)升级配置文件-------------------------------------------------------------------------------*/
	
	//沙盒验证签名key
	public final static String SANDBOXNEW_SIGN_KEY = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
	
	//统一下单接口
	public final static String SANDBOXNEW_PLACE_ORDER = "https://api.mch.weixin.qq.com/sandboxnew/pay/unifiedorder";

	//查询订单
	public final static String SANDBOXNEW_ORDER_QUERY = "https://api.mch.weixin.qq.com/sandboxnew/pay/orderquery";

	//退款
	public final static String SANDBOXNEW_REFUND = "https://api.mch.weixin.qq.com/sandboxnew/pay/refund";

	//查询退款信息
	public final static String SANDBOXNEW_REFUND_QUERY = "https://api.mch.weixin.qq.com/sandboxnew/pay/refundquery";

	//下载对账单
	public final static String SANDBOXNEW_DOWNLOAD_BILL = "https://api.mch.weixin.qq.com/sandboxnew/pay/downloadbill";


	/*------------------------------------------代金券(微信)配置文件-------------------------------------------------------------------------------*/
	
	//查询代金券批次
	public final static String WX_COUPON_STOCK_QUERY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/query_coupon_stock";

	//发放代金券
	public final static String WX_SEND_COUPON = "https://api.mch.weixin.qq.com/mmpaymkttransfers/send_coupon";
	
	
	/*------------------------------------------微信开放平台接口-------------------------------------------------------------------------------*/

	//获取第三方平台component_access_token   http请求方式: POST（请使用https协议） 
	public final static String OPEN_COMPONENT_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
	
	//获取预授权码pre_auth_code
	public final static String OPEN_PRE_AUTH_CODE = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token={component_access_token}";
	
	//通过code换取access_token
	public final static String OPEN_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid={APPID}&code={CODE}&grant_type=authorization_code&component_appid={COMPONENT_APPID}&component_access_token={COMPONENT_ACCESS_TOKEN}";
	
	//用户授权地址
	public final static String OPEN_AUTH = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid={component_appid}&pre_auth_code={pre_auth_code}&redirect_uri={redirect_uri}";

	//根据授权码 换取authorizer_access_token
	public final static String OPEN_AUTHORIZER_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={component_access_token}";

	//使用授权码换取公众号或小程序的接口调用凭据和授权信息
	public final static String OPEN_API_QUERY_AUTH = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token={component_access_token}";

	//获取授权方的帐号基本信息
	public final static String OPEN_GET_AUTHORIZER_INFO = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token={component_access_token}";

	//为授权的小程序帐号上传小程序代码
	public final static String OPEN_WXA_COMMIT = "https://api.weixin.qq.com/wxa/commit?access_token={access_token}";
	
	//获取体验小程序的体验二维码
	public final static String OPEN_WXA_GET_QRCODE = "https://api.weixin.qq.com/wxa/get_qrcode?access_token={access_token}";

	//获取授权小程序帐号的可选类目
	public final static String OPEN_WXA_GET_CATEGORY = "https://api.weixin.qq.com/wxa/get_category?access_token={access_token}";

	//获取小程序的第三方提交代码的页面配置（仅供第三方开发者代小程序调用）
	public final static String OPEN_WXA_GET_PAGE = "https://api.weixin.qq.com/wxa/get_page?access_token={access_token}";

	//将第三方提交的代码包提交审核（仅供第三方开发者代小程序调用）
	public final static String OPEN_WXA_SUBMIT_AUDIT = "https://api.weixin.qq.com/wxa/submit_audit?access_token={access_token}";
}