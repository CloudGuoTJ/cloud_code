package com.yunma.controller.couponWechat;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.common.wxpay.AnalysisXML;
import com.common.wxpay.MoneyUtils;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.yunma.controller.openweixin.OpenWeiXinController;
import com.yunma.controller.product.ProductController;
import com.yunma.entity.coupon.wechat.WeChatCouponConfig;
import com.yunma.entity.coupon.wechat.WeChatCouponReceiveRecord;
import com.yunma.entity.coupon.wechat.WxCoupon;
import com.yunma.service.antiFakeService.AntiFakeService;
import com.yunma.service.couponWechat.WeChatCouponConfigService;
import com.yunma.service.couponWechat.WeChatCouponService;
import com.yunma.service.wxConfig.WxConfigService;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;
import com.yunma.utils.weChat.PayCommonUtil;
import com.yunma.vo.wxConfig.WxConfigGzhVo;

/**
 * 微信小店优惠券
 * @author LUO
 *
 */
@Controller
@RequestMapping("/weChatCoupon")
public class WeChatCouponController {
	
	@Resource
	private ApiConfig apiConfig;
	
	@Resource
	private WeChatCouponService weChatCouponService;
	
	@Resource
	private WxConfigService wxConfigService;
	
	@Resource
	private OpenWeiXinController openweixinController;
	
	@Autowired
    private AntiFakeService antiFakeService;
	
	@Resource
	private WeChatCouponConfigService weChatCouponConfigService;
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	
	/**
	 * 创建代金券
	 * @param couponStockId
	 * @return
	 */
	@RequestMapping("/create.do")
	@ResponseBody
	public JSONObject createCoupon(Integer vendorId,String couponStockId) {
		JSONObject result = new JSONObject();
		
		//WxConfigGzhVo wxConfig = wxConfigService.getWxGzhInfo(vendorId);
		WeChatCouponConfig  wxConfig = weChatCouponConfigService.getWeChatCouponConfig(vendorId);
		if (wxConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "请填写微信小店配置信息");
			return result;
		} else if ("".equals(wxConfig.getAppId()) || "".equals(wxConfig.getMchId())) {
			result.put("statuscode", -2);
			result.put("msg", "微信小店配置信息填写不完整");
			return result;
		}
		
		String url = WeChatConfig.WX_COUPON_STOCK_QUERY;//查询代金券批次
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("coupon_stock_id",couponStockId);
        map.put("appid",wxConfig.getAppId());
        map.put("mch_id",wxConfig.getMchId());
        map.put("nonce_str",MoneyUtils.buildRandom());
        map.put("sign", MoneyUtils.createSign(wxConfig.getApiKey(), map));
		
        String xml = MoneyUtils.createXML(map);//转换为xml
        HttpRequestUtil util = new HttpRequestUtil();
        String result1 = util.sendPostWx(url, xml);//发送请求
        if (result1.contains("err_code_des")) {
        	result.put("statuscode", -3);
			result.put("msg", "该优惠券Id无效");
        } else {
        	WxCoupon wxCoupon = AnalysisXML.parseCoupnXML(result1);//解析xml
        	wxCoupon.setVendor_id(vendorId);
    		int resultNum = weChatCouponService.createCoupon(wxCoupon);
    		if (resultNum > 0) {
    			result.put("statuscode", 1);
    			result.put("msg", "成功");
    		} else {
    			result.put("statuscode", -4);
    			result.put("msg", "添加失败");
    		}
        }
        
        return result;
	}
	
	/**
	 * 发放优惠券(作废)
	 * @param url 微信网页授权地址
	 * @param vendorId 厂商id
	 * @param couponStockId 2537007 优惠券id
	 * @return
	 */
	@RequestMapping("/grantCoupon.do")
	@ResponseBody
	public JSONObject grantCoupon(String url, Integer vendorId,String couponStockId) {
		JSONObject result = new JSONObject();
		
		HttpRequestUtil util = new HttpRequestUtil();
		String resultStr = util.sendGet(url, null);//获取别人公众号的网页授权
		JSONObject resultJSON = JSONObject.parseObject(resultStr);
		String openId = resultJSON.get("openid")+"";//别人公众号的openId
		
		WxConfigGzhVo wxConfig = wxConfigService.getWxGzhInfo(vendorId);
		if (wxConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "请填写微信配置信息");
			return result;
		} else if ("".equals(wxConfig.getAppid()) || "".equals(wxConfig.getMchId())) {
			result.put("statuscode", -2);
			result.put("msg", "微信配置信息填写不完整");
			return result;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("coupon_stock_id",couponStockId);
        map.put("openid_count","1");
        map.put("partner_trade_no",wxConfig.getMchId()+MoneyUtils.buildRandom());
        map.put("openid",openId);
        map.put("appid",wxConfig.getAppid());
        map.put("mch_id",wxConfig.getMchId());//商户号
        map.put("nonce_str",MoneyUtils.buildRandom());
        map.put("sign", MoneyUtils.createSign(wxConfig.getWxKey(),map));
		
        String result1 = "";
        
        try {
        	result1 = MoneyUtils.doSendMoney(wxConfig.getCertName(), wxConfig.getMchId(), WeChatConfig.WX_SEND_COUPON , MoneyUtils.createXML(map));
        	//System.out.println(SendRedPacketsUtil.postCertificate(WeChatConfig.WX_SEND_COUPON, MoneyUtils.createXML(map)));
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
	}
	
	/**
	 * 发放优惠券 （通过第三方平台open.weixin.qq.com网页授权回调此接口）
	 * @param openId 用户的openId
	 * @param vendorId 厂商id
	 * @param couponStockId 优惠券id
	 * @return
	 */
	@RequestMapping("/callbackCoupon.do")
	@ResponseBody
	public JSONObject sendCoupon(String openId,String vendorId,String couponStockId) {
		log.debug("couponStockId:"+couponStockId);
		log.debug("vendorId:"+vendorId);
		JSONObject result = new JSONObject();
		
		//log.debug("code:"+code);
		//第三方平台 网页授权 获取别人公众号的openId
		/*String url = WeChatConfig.OPEN_ACCESS_TOKEN;
		url = url.replace("{APPID}", appid);
		url = url.replace("{CODE}", code);
		url = url.replace("{COMPONENT_APPID}", WeChatConfig.OPEN_APPID);
		HttpRequestUtil util = new HttpRequestUtil();
		String cat = util.sendGet("http://www.ym-b.top/web/index.php?c=account&a=auth&do=componentAccessToken", null);
		log.debug("微擎componentAccessToken："+cat);
		url = url.replace("{COMPONENT_ACCESS_TOKEN}", cat);
		log.debug("url:"+url);
		//HttpRequestUtil util = new HttpRequestUtil();
		String resultStr = util.sendGet(url, null);
		log.debug("result:"+resultStr);
		
		JSONObject resultJSON = JSONObject.parseObject(resultStr);
		String openId = resultJSON.getString("openid");*/
		//String openId = "o_AnMv5ZyDt7Hc8cNq8ogxeCwcY8";
		log.debug("openId:"+openId);
		
		if (openId == null) {
			log.debug("openid为空");
			result.put("statuscode", -3);
			result.put("msg", "未获取到openid");
			return result;
		}
		
		//WxConfigGzhVo wxConfig = wxConfigService.getWxGzhInfo(Integer.parseInt(vendorId));
		WeChatCouponConfig  wxConfig = weChatCouponConfigService.getWeChatCouponConfig(Integer.parseInt(vendorId));
		if (wxConfig == null) {
			result.put("statuscode", -1);
			result.put("msg", "请填写微信配置信息");
			return result;
		} else if ("".equals(wxConfig.getAppId()) || "".equals(wxConfig.getMchId())) {
			result.put("statuscode", -2);
			result.put("msg", "微信配置信息填写不完整");
			return result;
		}
		
		SortedMap<String,Object> map = new TreeMap<String,Object>();
		//Map<String,Object> map = new HashMap<String,Object>();
		map.put("coupon_stock_id",couponStockId);
        map.put("openid_count","1");
        map.put("partner_trade_no",wxConfig.getMchId()+MoneyUtils.buildRandom());
        map.put("openid",openId);
        map.put("appid",wxConfig.getAppId());
        map.put("mch_id",wxConfig.getMchId());//商户号
        map.put("nonce_str",MoneyUtils.buildRandom());
        String sign = PayCommonUtil.createSignV3("UTF-8",wxConfig.getApiKey(),map);
        log.debug("sign:"+sign);
        map.put("sign", sign);
		
        String result1 = "";
        
        try {
        	result1 = MoneyUtils.doSendMoney(wxConfig.getCredentialsLocation(), wxConfig.getMchId(), WeChatConfig.WX_SEND_COUPON , MoneyUtils.createXML(map));
        	//result1 = MoneyUtils.doSendMoney(wxConfig.getCertName(), wxConfig.getMchId(), WeChatConfig.WX_SEND_COUPON , PayCommonUtil.getRequestXmlV2(map));
        	//result1=SendRedPacketsUtil.postCertificateV2(WeChatConfig.WX_SEND_COUPON,wxConfig.getMchId(), PayCommonUtil.getRequestXmlV2(map),wxConfig.getCertName()).toString();
        	log.debug("result1:"+result1);
        	if (result1.contains("err_code_des")) {
        		WxCoupon wxCoupn= wxCoupn= AnalysisXML.parseCoupnXMLerr(result1);
        		result.put("statuscode", -3);
        		result.put("msg", wxCoupn.getErr());
        		return result;
        	} else {
        		
        		WxCoupon wxCoupn = AnalysisXML.parseCoupnXML(result1);
        		
        		WeChatCouponReceiveRecord record = new WeChatCouponReceiveRecord();
        		record.setVendorId(vendorId);
        		record.setCouponId(wxCoupn.getCoupon_id());
        		record.setOpenid(openId);
        		record.setCouponStockId(couponStockId);
        		
        		weChatCouponService.addReceiveRecord(record);
        		
        		result.put("statuscode", 1);
        		result.put("msg", "成功");
        	}
        	//System.out.println(SendRedPacketsUtil.postCertificate(WeChatConfig.WX_SEND_COUPON, MoneyUtils.createXML(map)));
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
	}
	
	public static void main(String[] args) throws DOMException, ParseException {
		HttpRequestUtil util = new HttpRequestUtil();
		SortedMap<String,Object> map = new TreeMap<String,Object>();
		//Map<String,Object> map = new HashMap<String,Object>();
		map.put("coupon_id","1861195172");
        map.put("appid","wx620ee71a7e75ffe9");
        map.put("openid","o_AnMv1lDyuqtQPIac0ShjpicGRM");
        map.put("mch_id","1459749002");
        map.put("stock_id","2795200");//商户号
        map.put("nonce_str",MoneyUtils.buildRandom());
        String sign = PayCommonUtil.createSignV3("UTF-8","QAQ0U0OoO666Zzr1le26924fnqv3g6yh",map);
        map.put("sign", sign);
        String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/querycouponsinfo";
		String result = util.sendPostWx(url, MoneyUtils.createXML(map));
		System.out.println(result);
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(1503729761); 
		Date date = c.getTime();
		System.out.println(sdf.format(date));*/
	}
	

}
