package com.yunma.controller.wechat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.BaiduMapUtils;
import com.common.util.Radix;
import com.yunma.dao.securityCode.WxAntiFakeDao;
import com.yunma.dao.weChart.VendorHtmlActivInfoDao;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.AntiFake;
import com.yunma.model.Location;
import com.yunma.model.User;
import com.yunma.model.VendorHtmlActivBackInfo;
import com.yunma.service.mapCount.MapLogicalCountService;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.service.user.UserService;

@Controller
public class WeChatAntiFakeHomeController extends BaseController {
	@Autowired
	private WxAntiFakeDao antiFakeDao;
	@Autowired
	private VendorHtmlActivInfoDao activInfoDao;
	@Autowired
	private SecurityCodeService codeService;
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private MapLogicalCountService mapService;
	@Autowired
	private UserService userService;

	/* 追加订单 */
	@RequestMapping("/POST/weChat/antiFakeCodeHome.do")
	@ResponseBody
	public JSONObject getAntiFakeInfo(@RequestParam("openId") String openId,
			@RequestParam("securityCode") String securityCode,
			@RequestParam(required = false) String longitude,
			@RequestParam(required = false) String latitude) {
		logger.info("传递扫码参数:");
		/**
		 * 从二维码中获取厂商相关信息
		 */
		StatAntiFakeCount antiFakeCount = new StatAntiFakeCount();
		JSONObject result = new JSONObject();
		Integer vendorId = Radix.convert62To10(securityCode.substring(4, 6));// 从二维码中转化vendorId
		Integer orderId = Radix.convert62To10(securityCode.substring(0, 4));// 从二维码中转化orderId
		SecurityCode code = codeService.getSecurityCode(securityCode);// 查询二维码是否正确
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);//获取订单信息
		AntiFake antiFake = new AntiFake();
		antiFake = antiFakeDao.findAntiFakeInfo(vendorId, openId);// 获取最新扫码记录
		int scanCount = antiFakeDao.findScanCount(vendorId, securityCode);// 同一二维码被扫描多少次
		result.put("productName", antiFake.getProductName());// 向前端传送产品名
		logger.debug("更新地理位置信息:");
		Location location = new Location();
		int b = 0;
		
		/**
		 * 保存扫码信息数据
		 */
		if (longitude != null && latitude != null) {
			b += antiFakeDao.updateLocation(openId, vendorId, longitude, latitude);// 更新地理位置信息
			Location location1 = BaiduMapUtils.getLocation(latitude, longitude);
			antiFakeCount.setAddress(location1.getAddress());
			antiFakeCount.setCity(location1.getCity());
			antiFakeCount.setDistrict(location1.getDistrict());
			antiFakeCount.setProvince(location1.getProvince());	
			
			antiFakeCount.setVendorId(vendorId);
			antiFakeCount.setLatitude(latitude);
			antiFakeCount.setLongitude(longitude);
			antiFakeCount.setSecurityCode(securityCode);
			
			antiFakeCount.setOpenId(openId);
			
			antiFakeCount.setOrderId(orderId);
			mapService.addScanCountInfo(antiFakeCount);
		}	
		
		User user = userService.findUserByVendorId(vendorId);
		String vendorName  = user.getVendorName();
				
		if (scanCount > 1) {
			result.put("msg", "您的二维码已被扫描次数");
			result.put("countTotal", scanCount);
		}
		AntiFake antiFake1 = antiFakeDao.findFirstScanInfo(securityCode,
				vendorId);// 获取第一个扫码人信息

		String a = antiFake1.getLongitude();
		logger.debug("errorMsg:{}"+a);
		if(!"".equals(a)  && a != null ){
			if (openId.equals(antiFake1.getOpenId()) || scanCount == 1) {
				result.put("scanFlag", "true");
				result.put("msg", "恭喜您扫码成功");
				location = BaiduMapUtils.getLocation(antiFake1.getLatitude(), antiFake1.getLongitude());
			} else {
				result.put("scanFlag", "false");
				logger.debug("经度："+antiFake1.getLongitude()+"纬度："+antiFake1.getLatitude());
				location = BaiduMapUtils.getLocation(antiFake1.getLatitude(), antiFake1.getLongitude());
				if(location == null){
					result.put("errorMsg", "第一次扫码未获取地理位置信息,无法显示!");
				}
			}
		}else{
			result.put("errorMsg", "第一次扫码未获取地理位置信息,无法显示!");
			result.put("scanFlag", "false");
			location =null;
		}

		/**
		 * 返回扫码验证信息
		 */
		if (code != null) {
			result.put("resultMsg", "您扫描的是正品");
			result.put("reultCode", 0);
		}

		List<VendorHtmlActivBackInfo> activInfo = activInfoDao
				.findVendorHtmlActivInfoByOrderId(orderId);

		result.put("scanTime", antiFake1.getScanTime());
		result.put("msg", "更新个人信息成功!");
		result.put("errorCode", b);
		result.put("vendorId", vendorId);
		result.put("vendorName", vendorName);
		result.put("activInfo", activInfo);
		result.put("location", location);
		result.put("expireTime", order.getExpiryDate());
		return result;

	}
	

}
