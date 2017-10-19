package com.yunma.controller.mapCount;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.BaiduMapUtils;
import com.common.util.Radix;
import com.yunma.dao.user.UserDao;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.AntiFake;
import com.yunma.model.TotalCount;
import com.yunma.service.antiFakeService.AntiFakeService;
import com.yunma.service.mapCount.MapCountService;
import com.yunma.service.mapCount.MapLogicalCountService;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.vo.mapCount.MapCountUserScanInfo;

@Controller
public class MapAntiFakeCountController extends BaseController {
	@Autowired
	private MapLogicalCountService countService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SecurityCodeService codeService;
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private AntiFakeService antiFakeService;
	@Autowired
	private MapCountService mapCountService;

	/**
	 * 商家扫码统计
	 * 
	 * @param userId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/mapAntiProCount.do")
	@ResponseBody
	public JSONObject getAntiFakeCount(Integer userId, Integer vendorId) {
		JSONObject result = new JSONObject();
		if (userId != null || vendorId != null) {

			return countService.getCount(userId, vendorId);
		} else {
			result.put("msg", "请输入正确参数");
			return result;
		}

	}

	/**
	 * 管理员统计
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/sysMapCount.do")
	@ResponseBody
	public JSONObject getTotalCount(Integer userId) {
		JSONObject result = new JSONObject();
		Integer userType = userDao.findUserType(userId);
		if (userType == 99) {
			return countService.getTotalCount(userId);
		} else {
			result.put("msg", "您不是管理员,请重新登录");
			return result;
		}

	}

	/**
	 * 商家二维码扫码统计
	 * 
	 * @param userId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/mapSecurityCount.do")
	@ResponseBody
	public JSONObject getMapSecurityCount(Integer userId, Integer vendorId) {

		return countService.getMapSecurityCount(userId, vendorId);

	}

	/**
	 * 商家最近七日扫码统计
	 * 
	 * @param userId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/vendorWeekMapCount.do")
	@ResponseBody
	public JSONObject getWeekCount(Integer userId, Integer vendorId) {
		JSONObject result = new JSONObject();
		Integer vendorId1 = userDao.getVendorIdByUserId(userId);
		if (vendorId1 == vendorId) {
			return countService.getWeekTotalCount(userId, vendorId);
		} else {
			result.put("msg", "您不是管理员,请重新登录");
			return result;
		}

	}

	/**
	 * 热力图 统计
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/count.do")
	@ResponseBody
	public JSONObject mapCount(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		BaiduMapUtils util = new BaiduMapUtils();
		List<StatAntiFakeCount> list = countService.mapCount(vendorId);
		if (list != null && list.size() > 0) {
			for (StatAntiFakeCount statAntiFakeCount : list) {
				JSONObject object = new JSONObject();
				object.put("name",
						util.getProvinceNick(statAntiFakeCount.getProvince()));
				object.put("value", statAntiFakeCount.getCount());
				object.put("selected", "true");
				array.add(object);
			}
		}
		result.put("data", array);
		return result;
	}

	/***
	 * 扫码明细统计
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 * 
	 */
	@RequestMapping("/GET/mapCount/countInfo.do")
	@ResponseBody
	public JSONObject statisticsSecurityInfo(Integer vendorId)
			throws UnsupportedEncodingException {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		if(vendorId == 167){
			List<AntiFake> antifakeList = countService.findAntiFakeListInfo(vendorId);
			if (antifakeList != null && antifakeList.size() > 0) {
				for (AntiFake antiFake : antifakeList) {
					JSONObject object = new JSONObject();
					String securityCode = antiFake.getSecurityCode();
					String nickNameDeco = codeService
							.getNickNameByOpenId(antiFake.getOpenId());
					String nickName = URLDecoder.decode(nickNameDeco, "utf-8");
					java.util.Date scanTime = antiFake.getScanTime();
					String address = null;
					int count = codeService.findCodeScanCount(securityCode,
							vendorId);
					String productName = antiFake.getProductName();
					String vendorName = userDao.getVendorNameByVendorId(vendorId);
					String picUrl = codeService.getPicUrlByOpenId(antiFake
							.getOpenId());
					//
					if (count == 1) {
						object.put("errorCode", 1);
					} else {
						object.put("errorCode", -1);
					}
					object.put("securityCode", securityCode);
					object.put("nickName", nickName);
					object.put("scanTime", scanTime);
					object.put("address", address);
					object.put("vendorName", vendorName);
					object.put("productName", productName);
					object.put("picUrl", picUrl);
					object.put("count", count);
					array.add(object);
				}
			}
			result.put("data", array);
			return result;
			
		}
		List<StatAntiFakeCount> list = countService
				.getSucurityScanInfo(vendorId);
		if (list != null && list.size() > 0) {
			for (StatAntiFakeCount statAntiFakeCount : list) {
				String securityCode = statAntiFakeCount.getSecurityCode();
				AntiFake antiFake = antiFakeService.getAntiFake(securityCode,
						vendorId);
				Integer orderId = Radix.convert62To10(securityCode.substring(0,
						4));
				JSONObject object = new JSONObject();
				String nickNameDeco = codeService
						.getNickNameByOpenId(statAntiFakeCount.getOpenId());
				String nickName = URLDecoder.decode(nickNameDeco, "utf-8");
				Date scanTime = statAntiFakeCount.getScanTime();
				String address = statAntiFakeCount.getProvince() + " "
						+ statAntiFakeCount.getCity();
				int count = codeService.findCodeScanCount(securityCode,
						vendorId);
				String productName = antiFake.getProductName();
				String vendorName = userDao.getVendorNameByVendorId(vendorId);
				String picUrl = codeService.getPicUrlByOpenId(statAntiFakeCount
						.getOpenId());
				//
				if (count == 1) {
					object.put("errorCode", 1);
				} else {
					object.put("errorCode", -1);
				}
				object.put("securityCode", securityCode);
				object.put("nickName", nickName);
				object.put("scanTime", scanTime);
				object.put("address", address);
				object.put("vendorName", vendorName);
				object.put("productName", productName);
				object.put("picUrl", picUrl);
				object.put("count", count);
				array.add(object);
			}
		}
		
		result.put("data", array);
		return result;
	}

	@RequestMapping("/GET/mapCount/tracingCountInfo.do")
	@ResponseBody
	public JSONObject tracingCodeScanInfo(Integer vendorId)
			throws UnsupportedEncodingException {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();

		List<StatAntiFakeCount> list = countService
				.getSucurityScanInfo(vendorId);
		if (list != null && list.size() > 0) {
			for (StatAntiFakeCount statAntiFakeCount : list) {
				String securityCode = statAntiFakeCount.getSecurityCode();
				SecurityCode code = codeService.getSecurityCode(securityCode);
				Integer orderId = Radix.convert62To10(securityCode.substring(0,
						4));
				JSONObject object = new JSONObject();
				String nickNameDeco = codeService
						.getNickNameByOpenId(statAntiFakeCount.getOpenId());

				Date scanTime = statAntiFakeCount.getScanTime();
				String address = statAntiFakeCount.getProvince() + " "
						+ statAntiFakeCount.getCity();
				int count = codeService.getScanCountpri(orderId, securityCode);
				String productName = code.getProductName();
				String vendorName = userDao.getVendorNameByVendorId(vendorId);

				object.put("vendorName", vendorName);
				object.put("productName", productName);
				array.add(object);
			}
		}
		result.put("data", array);
		return result;
	}

	/**
	 * 统计某省中所有城市的扫码数量
	 * 
	 * @param vendorId
	 * @param province
	 * @return
	 */
	@RequestMapping("/GET/mapCount/mapCountForCity.do")
	@ResponseBody
	public JSONObject mapCountForCity(HttpServletRequest request,
			Integer vendorId, String province) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();

		logger.debug("某某省：：：：：：：：" + province);
		List<StatAntiFakeCount> list = mapCountService.mapCountForCity(
				vendorId, province);

		if (list != null && list.size() > 0) {

			for (StatAntiFakeCount statAntiFakeCount : list) {
				JSONObject object = new JSONObject();
				object.put("name", statAntiFakeCount.getCity());
				object.put("value", statAntiFakeCount.getCount());
				object.put("selected", "false");
				array.add(object);
			}
		}
		result.put("data", array);

		return result;
	}

	/**
	 * 统计某市各区县的扫码量
	 * 
	 * @param vendorId
	 * @param district
	 * @return
	 */
	@RequestMapping("/GET/mapCount/getDistrCount.do")
	@ResponseBody
	public JSONObject getDistrCount(HttpServletRequest request,
			Integer vendorId, String city) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();

		List<StatAntiFakeCount> list = mapCountService.getDistrCount(vendorId,
				city);

		if (list != null && list.size() > 0) {

			for (StatAntiFakeCount statAntiFakeCount : list) {
				JSONObject object = new JSONObject();
				object.put("name", statAntiFakeCount.getDistrict());
				object.put("value", statAntiFakeCount.getCount());
				object.put("selected", "false");
				array.add(object);
			}
		}
		result.put("data", array);

		return result;
	}

	/**
	 * 热力图区县数据统计
	 * 
	 * @param vendorId
	 * @param district
	 * @return
	 */
	@RequestMapping("/GET/mapCount/heatMapForDistr.do")
	@ResponseBody
	public JSONObject heatMapForDistr(HttpServletRequest request,
			Integer vendorId, String district) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		List<StatAntiFakeCount> list = mapCountService.heatMapForDistr(
				vendorId, district);

		if (list != null && list.size() > 0) {

			for (StatAntiFakeCount statAntiFakeCount : list) {
				JSONObject object = new JSONObject();
				object.put("lng",
						Double.parseDouble(statAntiFakeCount.getLongitude()));
				object.put("lat",
						Double.parseDouble(statAntiFakeCount.getLatitude()));
				object.put("count",
						Integer.parseInt(statAntiFakeCount.getCount()));
				array.add(object);
			}
		}
		result.put("data", array);

		return result;
	}
	/**
	 * 获取所有扫码量
	 * @return
	 */
	@RequestMapping("/GET/mapCount/getAllCount.do")
	@ResponseBody
	public JSONObject getAllCount() {

		return countService.getAllCountList();

	}
	
	

	/**
	 * 系统最近七日扫码统计
	 * 
	 * @param userId
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/mapCount/SystemWeekMapCount.do")
	@ResponseBody
	public JSONObject getSystemWeekCount(Integer userId, Integer vendorId) {		
			return countService.getSystemWeekTotalCount(userId, vendorId);	
	}

	
	

}
