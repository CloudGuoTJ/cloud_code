package com.yunma.controller.couponWechat;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.wxpay.AnalysisXML;
import com.common.wxpay.MoneyUtils;
import com.yunma.controller.product.ProductController;
import com.yunma.utils.HttpRequestUtil;
import com.yunma.utils.WeChatConfig;
import com.yunma.utils.weChat.PayCommonUtil;

/**
 * 微信支付接口升级代码
 * 用于 开通代金券时，所必需的接口验证
 * @author LUO
 *
 */
@Controller
public class PayCheckAndAcceptControler {
	
	private Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	String Wxkey="ytxg123123ytxg123123ytxg12312388";
	String openid = "osLjmvsMCRHtFFYUtt5mun42D19U";

	/**
	 * 微信支付
     * 1.请求getsignkey接口获取沙箱验证码
     * 2.请求unifiedorder 统一下单 创建订单
     * 3.请求orderquery 查询订单
	 * @return
	 */
    @RequestMapping("/payJogglePromotion.do")
    @ResponseBody
	public String pay() {
    	String total_fee = "551";//支付金额
        //String notify_url = "https://project.ym-b.top/cloud_code/index.jsp";//回调的页面
    	String notify_url = "http://b.ym-a.top";
        String trade_type = "JSAPI";//类型
        //String openid="o_AnMv5ZyDt7Hc8cNq8ogxeCwcY8";//用户openid
        String order_no = "201705031059";//订单号
        String key=getSign();
        String unifieurl = WeChatConfig.SANDBOXNEW_PLACE_ORDER;//统一下单接口

        Map<String,Object> map =new HashMap<String, Object>();
        map.put("appid",WeChatConfig.YMJS_APP_ID);
        map.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        map.put("nonce_str",PayCommonUtil.CreateNoncestr());
        map.put("body","接口升级");
        map.put("total_fee",total_fee);
        map.put("out_trade_no",order_no);
        map.put("spbill_create_ip","192.168.1.103");
        map.put("notify_url",notify_url);
        map.put("trade_type",trade_type);
        map.put("openid",openid);
        map.put("sign", MoneyUtils.createSign(key,map));//使用沙箱验证Key 进行签名
       String result="";
       HttpRequestUtil util = new HttpRequestUtil();
            try {
                result = util.sendPostWx( unifieurl, MoneyUtils.createXML(map));
                System.out.println("result:"+result);
        }catch (Exception e){
        logger.error("统一下单是异常："+e);
        }

        String orderurl = WeChatConfig.SANDBOXNEW_ORDER_QUERY;//查询订单
        Map<String,Object> map1 =new HashMap<String, Object>();
        map1.put("appid",WeChatConfig.YMJS_APP_ID);
        map1.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        map1.put("nonce_str",PayCommonUtil.CreateNoncestr());
        map1.put("out_trade_no",order_no);

        map1.put("sign", MoneyUtils.createSign(key,map1));//使用沙箱验证Key 进行签名
        String result2="";
        try {
            result2 = util.sendPostWx( orderurl, MoneyUtils.createXML(map1));
            System.out.println("result2:"+result2);
        }catch (Exception e){
            logger.error("查询订单异常："+e);
        }

        //微信查询订单接口 接口升级

        return result2;
    }
    
    /**
     * 下载对账单接口
     * @return
     */
    @RequestMapping("/orderquery.json")
    @ResponseBody
    public  String orderquery(){

        String key=getSign();
        String url1 = WeChatConfig.SANDBOXNEW_DOWNLOAD_BILL;
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("appid",WeChatConfig.YMJS_APP_ID);
        map.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        map.put("nonce_str",PayCommonUtil.CreateNoncestr());
        map.put("bill_date","20170710");//查询 某天的订单信息
        map.put("bill_type","ALL");//查询类型

        map.put("sign",MoneyUtils.createSign(key,map));
        String result="";
        try {
        	HttpRequestUtil util = new HttpRequestUtil();
            result = util.sendPostWx( url1, MoneyUtils.createXML(map));

        }catch (Exception e){
        logger.error(e+"");
        }

        return result;
    }
    
    /**
     * 订单退款
     * @return
     */
    @RequestMapping("/refundquery.json")
    @ResponseBody
    public String  refundquery(){
            String total_fee = "552";//退款金额
            String notify_url = "http://"+WeChatConfig.DOMAIN_NAME+"/"+WeChatConfig.PROJECT_NAME+"/index.jsp";
            String trade_type = "JSAPI";
            String order_no = "201705031050";//订单号
            //String openid="o_AnMv5ZyDt7Hc8cNq8ogxeCwcY8";
        //获取沙箱验证码
         String key=getSign();
        //创建订单
            String unifieurl = WeChatConfig.SANDBOXNEW_PLACE_ORDER;
            Map<String,Object> map =new HashMap<String, Object>();
            map.put("appid",WeChatConfig.YMJS_APP_ID);
            map.put("mch_id",WeChatConfig.YMJS_MCH_ID);
            map.put("nonce_str",PayCommonUtil.CreateNoncestr());
            map.put("body","接口升级");
            map.put("total_fee",total_fee);
            map.put("out_trade_no",order_no);
            map.put("spbill_create_ip","192.168.1.103");
            map.put("notify_url",notify_url);
            map.put("trade_type",trade_type);
            map.put("openid",openid);
            map.put("sign", MoneyUtils.createSign(key,map));//改
            //WxConfig wxConfig = wxConfigService.getWxConfigByVendorId(-1);
            HttpRequestUtil util = new HttpRequestUtil();
            String result="";
            try {
                String tes= MoneyUtils.createXML(map);
                result = util.sendPostWx( unifieurl, MoneyUtils.createXML(map));

            } catch (Exception e) {
                logger.error("统一下单是异常："+e);
            }

        //查询订单信息
            String orderurl = WeChatConfig.SANDBOXNEW_ORDER_QUERY;
            Map<String,Object> map1 =new HashMap<String, Object>();
            map1.put("appid",WeChatConfig.YMJS_APP_ID);
            map1.put("mch_id",WeChatConfig.YMJS_MCH_ID);
            map1.put("nonce_str",PayCommonUtil.CreateNoncestr());
            map1.put("out_trade_no",order_no);
            map1.put("sign", MoneyUtils.createSign(key,map1));
            String result2="";
            try {
             String tes= MoneyUtils.createXML(map1);
             result2 = util.sendPostWx( orderurl, MoneyUtils.createXML(map1));
            }catch (Exception e){
                logger.error("查询订单信息时异常："+e);
            }
        //调用退款API
        String refundUrl = WeChatConfig.SANDBOXNEW_REFUND;
        Map<String,Object> refundMap =new HashMap<String, Object>();
        refundMap.put("appid",WeChatConfig.YMJS_APP_ID);
        refundMap.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        refundMap.put("nonce_str",PayCommonUtil.CreateNoncestr());
        refundMap.put("out_trade_no",order_no);
        refundMap.put("total_fee","552");
        refundMap.put("refund_fee","552");
        refundMap.put("out_refund_no","	1217752501201407033233368018");
        refundMap.put("sign",MoneyUtils.createSign(key,refundMap));
        try {
            String tes= MoneyUtils.createXML(map1);
            result2 = util.sendPostWx( refundUrl, MoneyUtils.createXML(refundMap));

        }catch (Exception e){
            logger.error("退款时异常："+e);
        }
        //查询退款信息
        String refundqueryUrl= WeChatConfig.SANDBOXNEW_REFUND_QUERY;
        Map<String,Object> refundqueryMap =new HashMap<String, Object>();
        refundqueryMap.put("appid",WeChatConfig.YMJS_APP_ID);
        refundqueryMap.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        refundqueryMap.put("nonce_str",PayCommonUtil.CreateNoncestr());
        refundqueryMap.put("out_trade_no",order_no);
        refundqueryMap.put("out_refund_no","1217752501201407033233368018");
        refundqueryMap.put("sign",MoneyUtils.createSign(key,refundqueryMap));
        try {
            String tes= MoneyUtils.createXML(refundqueryMap);
            result2 = util.sendPostWx( refundqueryUrl, MoneyUtils.createXML(refundqueryMap));

        }catch (Exception e){
            logger.error("查询退款信息时异常："+e);
        }
        return result2;
    }
    
    
    private String getSign() {
    	Map<String,Object> map=new HashMap<String, Object>();
        map.put("mch_id",WeChatConfig.YMJS_MCH_ID);
        map.put("nonce_str",PayCommonUtil.CreateNoncestr());
        String sign="";
        map.put("sign",MoneyUtils.createSign(WeChatConfig.YMJS_API_KEY,map));
        
        HttpRequestUtil util = new HttpRequestUtil();
        String  result1 = util.sendPostWx(WeChatConfig.SANDBOXNEW_SIGN_KEY, MoneyUtils.createXML(map));
        sign = AnalysisXML.parseCoupnXMLsignke(result1);
        System.out.println(result1);
        return sign;
    }
    
    public static void main(String[] args) {
    	PayCheckAndAcceptControler pay = new PayCheckAndAcceptControler();
    	pay.refundquery();
	}
}
