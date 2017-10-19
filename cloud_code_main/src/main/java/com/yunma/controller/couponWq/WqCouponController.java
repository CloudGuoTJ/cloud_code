package com.yunma.controller.couponWq;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.coupon.wq.WqCouponVendor;
import com.yunma.service.couponWq.WqCouponService;
import com.yunma.utils.HttpRequestUtil;

@Controller
@RequestMapping("/wqcoupon")
public class WqCouponController {
	
	@Resource
	private WqCouponService wqCouponService;
	
	/**
	 * 新增优惠券
	 * @param vendorId
	 * @param wqId
	 * @param getUrl
	 * @return
	 */
	@RequestMapping("/addcoupon.do")
	@ResponseBody
	public JSONObject addCoupon(Integer vendorId,Integer wqId,String getUrl,String productUrl) {
		JSONObject result = new JSONObject();
		
		String sql = "SELECT * FROM ims_ewei_shop_coupon WHERE id = "+wqId;
		sql = sql.replaceAll(" ", "%20");
		
		String result1 = sendWqSql(sql);
		WqCouponVendor coupon = paraseWqCoupon(wqId,vendorId,sql,getUrl,result1,productUrl);
		
		if (coupon != null) {
			int temp1 = wqCouponService.hasCoupon(wqId);
			if (temp1 == 0) {
				int temp = wqCouponService.addCoupon(coupon);
				if (temp > 0) {
					result.put("statuscode", 1);
					result.put("msg", "新增成功！");
				} else {
					result.put("statuscode", -1);
					result.put("msg", "新增失败！");
				}
			} else {
				int temp = wqCouponService.updateCoupon(coupon);
				if (temp > 0 ) {
					result.put("statuscode", 2);
					result.put("msg", "更新成功！");
				} else {
					result.put("statuscode", -4);
					result.put("msg", "更新失败！");
				}
			}
		} else {
			result.put("statuscode", -2);
			result.put("msg", "无效优惠券id");
		}
		
		return result;
	}
	
	/**
	 * 获取最新的产品地址
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getCouponPorudctUrl.do")
	@ResponseBody
	public JSONObject getCouponPorudctUrl(Integer vendorId) {
		JSONObject object = new JSONObject();
		object.put("data", wqCouponService.getCouponPorudctUrl(vendorId));
		
		return object;
	}
	
	/**
	 * 获取该厂商的优惠券
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/getcoupon.do")
	@ResponseBody
	public String getCoupon(Integer vendorId) {

		String sql = "SELECT * FROM ims_ewei_shop_coupon WHERE ";
		
		List<WqCouponVendor> list = wqCouponService.getCouponByVendorId(vendorId);
		for (int i=0 ; i<list.size() ; i++) {
			if (i == 0) {
				sql += " id="+list.get(i).getWqId();
			} else {
				sql += " or id="+list.get(i).getWqId();
			}
		}
		
		sql = sql.replaceAll(" ", "%20");
		return sendWqSql(sql);
	}
	
	/**
	 * 向微擎发送sql语句
	 * @param sql
	 * @return
	 */
	public String sendWqSql(String sql) {
		String url = "http://mp.ym-a.top/api.php?appid=wxf8b4f85f3a794e77&&func=wqSql&sql="+sql;
		return HttpRequestUtil.sendGet(url,null);
	}
	
	/**
	 * 根据id获取微擎优惠券信息并解析
	 * @param wqId
	 * @param vendorId
	 * @param sql
	 * @param getUrl
	 * @return
	 */
	private WqCouponVendor paraseWqCoupon(Integer wqId,Integer vendorId,String sql,String getUrl,String result,String productUrl) {
		
		if (result != null && result.length() > 0) {
			WqCouponVendor coupon = new WqCouponVendor();
			
			JSONArray array = JSONArray.parseArray(result);
			if (array.size() > 0) {
				String objectStr = array.getString(0);
				
				JSONObject object = JSONObject.parseObject(objectStr);
				String couponName = object.getString("couponname");
				String deduct = object.getString("deduct");
				String enough = object.getString("enough");
				String createTime = object.getString("createtime");
				String startTime = object.getString("timestart");
				String endTime = object.getString("timeend");
				String total = object.getString("total");
				String getmax = object.getString("getmax");
				String limitdiscounttype = object.getString("limitdiscounttype");
				
				coupon.setWqId(wqId);
				coupon.setCouponName(unicodeToString(couponName));
				coupon.setReduce(Double.valueOf(deduct));
				coupon.setLeastCost(Double.valueOf(enough));
				coupon.setCreateTimeStr(parseTimeStamp(Long.parseLong(createTime)));
				coupon.setStartTimeStr(parseTimeStamp(Long.parseLong(startTime)));
				coupon.setEndTimeStr(parseTimeStamp(Long.parseLong(endTime)));
				coupon.setTotal(Integer.parseInt(total));
				coupon.setGetmax(Integer.parseInt(getmax));
				coupon.setLimitdiscounttype(limitdiscounttype);
				coupon.setGetUrl(getUrl);
				coupon.setVendorId(vendorId);
				coupon.setProductUrl(productUrl);
				
				return coupon;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}
	
	/**
	 * 将时间戳转换为年月日格式
	 * @param timeStamp
	 * @return
	 */
	public static String parseTimeStamp(final long timeStamp) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long s = timeStamp*1000l;
		Long time = new Long(s);
	    String d = format.format(time);  
	    return d;
	}
	
	/**
	 * Unicode转中文  
	 * @param str
	 * @return
	 */
	public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch+"" );
        }

        return str;
    }

}
