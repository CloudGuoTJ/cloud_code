package com.yunma.service.mapCount.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.redEnvelope.RedEnvDao;
import com.yunma.dao.securityCode.SecurityCodeDao;
import com.yunma.dao.securityCode.WxAntiFakeDao;

import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.AntiFake;
import com.yunma.model.SysMapCountMainPage;
import com.yunma.model.TotalCount;
import com.yunma.model.WeekDays;
import com.yunma.service.mapCount.MapLogicalCountService;
import com.yunma.vo.mapCount.WeekScanCountEveDayVO;

@Service
public class MapLogicalCountServiceImpl extends BaseService implements MapLogicalCountService{
	@Autowired
	private WxAntiFakeDao wxAntiFakeDao;
	@Autowired
	private ProductOrderDao orderDao;
	@Autowired
	private SecurityCodeDao codeDao;
	@Autowired
	private RedEnvDao envDao;

	@Override
	public JSONObject getCount(Integer userId, Integer vendorId) {
		JSONObject result = new JSONObject();		
		/**
		 * 获取商家的扫码量
		 */
		
		
		int dayTotalScanCount = 0;
		int vendorTotalScanCount = 0;
		String tbName = orderDao.selectDBTable("tb_anti_fake_"+vendorId);
		if(tbName == null){
			 dayTotalScanCount = 0 ;
			 vendorTotalScanCount = 0;
			result.put("msg", "您尚未创建订单或二维码!");
		}else{
			
			 dayTotalScanCount = wxAntiFakeDao.findDayScanCount(vendorId);// 单个商家每日扫码量
			 vendorTotalScanCount = wxAntiFakeDao.getAntifakeCount(vendorId);// 商家总的扫码量		
		}
		
		int wxVipCount = 0;
		int wxAddVipCount = 0;
		int totalFuns = wxVipCount;		
		result.put("totalFuns", totalFuns);
		result.put("wxVipCount", wxVipCount);
		result.put("wxAddVipCount", wxAddVipCount);
		result.put("dayTotalScanCount", dayTotalScanCount);
		result.put("vendorTotalScanCount", vendorTotalScanCount);
		result.put(
				"msg",
				"vendorTotalScanCountMsg:商家总扫码量,dayTotalScanCountMsg:前一天扫码量,wxAddVipCount:昨日新增会员数,wxVipCount:会员数,totalFuns:总粉丝数");
		return result;
	}

	public JSONObject getTotalCount(Integer userId) {
		// 管理员统计所有商家的扫码量,扫码表根据生成二维码商家id来建表

		JSONObject result = new JSONObject();

		// 先从缓存中取出数据
		SysMapCountMainPage sysMapCountMainPage = (SysMapCountMainPage) XMemcachedHelper
				.findCache("sysMapCountMainPage_" + userId);
		SysMapCountMainPage sysMapCountMainPagePri = new SysMapCountMainPage();
		if (sysMapCountMainPage != null) {
			result.put("addDayOrder", sysMapCountMainPage.getAddDayOrder());
			result.put("excepScanCount",
					sysMapCountMainPage.getExcepScanCount());
			result.put("sysScanCount", sysMapCountMainPage.getSysScanCount());
			result.put("orderCount", sysMapCountMainPage.getOrderCount());
			result.put(
					"msg",
					"orderCountMsg:订单总数,sysScanCountMsg:总商家扫码数,excepScanCountMsg:异常扫码数,addDayOrderMsg:昨日增加订单数");

			return result;

		} else {
			List<TotalCount> totalCountPri = orderDao
					.findAllVendorIdAndVendorName();
			int excepScanCount = 0;// 异常扫码数据
			int sysScanCount = 0;//
			int totalCount = 0;
			int excepCountPri = 0;
			String tbName = null;
			if (totalCountPri != null) {

				for (int i = 0; i < totalCountPri.size(); i++) {
					TotalCount total = new TotalCount();
					Integer vendorId = totalCountPri.get(i).getVendorId();
					tbName = orderDao.selectDBTable("tb_anti_fake_" + vendorId);
					if (tbName != null) {
						sysScanCount = wxAntiFakeDao.getAntifakeCount(vendorId);
					} else {
						sysScanCount = 0;
						
					}
					totalCount = sysScanCount + totalCount;

				}
			} else {
				sysScanCount = 0;
				
			}
			for (int j = 0; j < totalCountPri.size(); j++) {
				Integer vendorIdPri = totalCountPri.get(j).getVendorId();
				tbName = orderDao.selectDBTable("tb_anti_fake_" + vendorIdPri);
				if (tbName != null) {
					List<String> count = wxAntiFakeDao
							.totalCountExceptionAntiFake(vendorIdPri);
					if (count != null) {
						for (int c = 0; c < count.size(); c++) {
							String expCount = count.get(c);// 可能为空值
							Integer exCount1 = 0;
							if (expCount == null) {
								exCount1 = 0;
							} else {
								exCount1 = Integer.parseInt(expCount);
								
							}
							excepCountPri = exCount1 + excepCountPri;
						}
						excepScanCount = excepCountPri ;
					}
					
				}
				
			}
			int addDayOrder = orderDao.findDayAddOrder();
			int orderCount = orderDao.getTotalScanCount();

			sysMapCountMainPagePri.setAddDayOrder(addDayOrder);
			sysMapCountMainPagePri.setExcepScanCount(excepScanCount);
			sysMapCountMainPagePri.setOrderCount(orderCount);
			sysMapCountMainPagePri.setSysScanCount(totalCount);
			XMemcachedHelper.set("sysMapCountMainPage_" + userId,
					sysMapCountMainPagePri, 30 * 60);
			result.put("addDayOrder", addDayOrder);
			result.put("excepScanCount", excepScanCount);
			result.put("sysScanCount", totalCount);
			result.put("orderCount", orderCount);
			result.put(
					"msg",
					"orderCountMsg:订单总数,sysScanCountMsg:总商家扫码数,excepScanCountMsg:异常扫码数,addDayOrderMsg:昨日增加订单数");

			return result;
		}
	}

	public JSONObject getMapSecurityCount(Integer userId, Integer vendorId) {
		/**
		 * 获取商家的扫码量
		 */
		List<Integer> totalOrderId = orderDao.findTotalOrderId(vendorId);// 商家下总的扫码表
		int totalSecurityCodeCount = 0;// 二维码总量
		int securityCodeScanCount = 0;// 二维码被扫描次数
		int dayTotalScanCount = 0 ;// 单个商家每日扫码量
		int vendorTotalScanCount =0 ;// 商家总的扫码量
		int excepScanCount = 0;// 异常扫码数量
		String tbName = orderDao.selectDBTable("tb_anti_fake_"+vendorId);
		if(tbName == null){
			 dayTotalScanCount = 0 ;// 单个商家每日扫码量
			 vendorTotalScanCount = 0 ;// 商家总的扫码量
			 excepScanCount = 0;// 异常扫码数量
		}else{
			
			dayTotalScanCount = wxAntiFakeDao.findDayScanCount(vendorId);
			vendorTotalScanCount = wxAntiFakeDao.getAntifakeCount(vendorId);
			/**
			 * 异常扫码数量
			 */
			
			List<String> count = wxAntiFakeDao
					.totalCountExceptionAntiFake(vendorId);
			if(count != null ){
				for(int c =0;c < count.size();c++){
					String expCount = count.get(c);// 可能为空值
					Integer exCount1 = 0;
					if (expCount == null) {
						exCount1 = 0;
					} else {
						exCount1 = Integer.parseInt(expCount);
					}
					excepScanCount =excepScanCount+ exCount1;
				}
				
			}
			
		}
		if (totalOrderId != null) {

			for (int j = 0; j < totalOrderId.size(); j++) {								
				totalSecurityCodeCount = totalSecurityCodeCount
						+ codeDao.getSecurityCount(totalOrderId.get(j));
				
			}

		} else {
			
			totalSecurityCodeCount = 0;// 二维码总量
			securityCodeScanCount = 0;// 二维码被扫描次数
		}

		int joinActivCount = 0;// 活动参与数量
		int activTotalCount = 0;// 活动总量
		int daylyJoinActivCount = 0;// 每日参与活动数量
		List<Integer> totalActivOrderId = orderDao.findRedEnvOrderId(vendorId);// 商家下总的扫码表
		if (totalActivOrderId != null) {

			for (int t = 0; t < totalActivOrderId.size(); t++) {
				Integer orderId = totalActivOrderId.get(t);
				joinActivCount = joinActivCount
						+ envDao.getJoinActivCount(orderId);
				activTotalCount = activTotalCount
						+ envDao.getActivTotalCount(orderId);
				daylyJoinActivCount = daylyJoinActivCount
						+ envDao.getDaylyJoinActivCount(orderId);
			}

		} else {
			joinActivCount = 0;// 活动参与数量
			activTotalCount = 0;// 活动总量
			daylyJoinActivCount = 0;
		}
		if(totalSecurityCodeCount != 0){
			
			securityCodeScanCount = totalSecurityCodeCount - excepScanCount + 1;
			
		}else{
			securityCodeScanCount = 0;
		}
		
		JSONObject result = new JSONObject();
		result.put("securityCodeScanCount", securityCodeScanCount);
		result.put("daylyJoinActivCount", daylyJoinActivCount);
		result.put("totalSecurityCodeCount", totalSecurityCodeCount);
		result.put("joinActivCount", joinActivCount);
		result.put("activTotalCount", activTotalCount);
		result.put("dayTotalScanCount", dayTotalScanCount);
		result.put("vendorTotalScanCount", vendorTotalScanCount);
		result.put(
				"msg",
				"securityCodeScanCount:总二维码数量,securityCodeScanCount:已被扫描二维码数量,joinActivCount:活动参与数量,activTotalCount:活动总量,dayTotalScanCount:单日扫码量,daylyJoinActivCount:昨天增加活动参与数量");
		return result;
	}


	@Override
	@Transactional
	public JSONObject getWeekTotalCount(Integer userId, Integer vendorId) {
		/**
		 * 使用DateFormat生成数组与数据库中查询的数据进行匹配
		 * 替换数据库中查询对应天数为null不显示的部分为0
		 * 
		 */
		JSONObject result = new JSONObject();
		//添加缓存信息
		List<WeekScanCountEveDayVO> weekScanCount;
		try
		{
			/*从缓存中能拿到数据*/
			weekScanCount = (List<WeekScanCountEveDayVO>)XMemcachedHelper.findCache("weekScanCount_" + userId);
			
			if(weekScanCount != null)
			{
				logger.info("从Memcached中取得了Order信息");
				result.put("weekScanCount", weekScanCount);
				return result;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			List<String> weekDay = new ArrayList<String>();
			Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
			for(int i=1; i < 8; i++){

				calendar1.add(Calendar.DATE, -1 );
				String times = sdf.format(calendar1.getTime());
				weekDay.add(times);
				}
					
			/**
			 * 获取所有生成二维码的
			 */	
			WeekDays weekdays = new WeekDays();
			weekdays.setDate1( weekDay.get(6));
			weekdays.setDate2( weekDay.get(5));
			weekdays.setDate3( weekDay.get(4));
			weekdays.setDate4( weekDay.get(3));
			weekdays.setDate5( weekDay.get(2));
			weekdays.setDate6( weekDay.get(1));
			weekdays.setDate7( weekDay.get(0));		
			List<String> days = new ArrayList<String>();
			days.add(weekdays.getDate1());
			days.add(weekdays.getDate2());
			days.add(weekdays.getDate3());
			days.add(weekdays.getDate4());
			days.add(weekdays.getDate5());
			days.add(weekdays.getDate6());
			days.add(weekdays.getDate7());
			/**
			 * SELECT A.scanTime, A.coun FROM (SELECT DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') AS SCANTIME, COUNT(1) COUN  " +
					"FROM tb_anti_fake_#{vendorId} DOC " +
					"WHERE " +
					"DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') <='${days.get(6)}'  AND  DATE_FORMAT(DOC.SCANTIME, GROUP BY scanTime '%Y%m%d')>='${days.get(0)}'" +
					" UNION (SELECT '${days.get(0)}', 0) " +
					"UNION (SELECT '${days.get(1)}', 0) " +
					"UNION (SELECT '${days.get(2)}', 0) " +
					"UNION (SELECT '${days.get(3)', 0) " +
					"UNION (SELECT '${days.get(4)}', 0) " +
					"UNION (SELECT '${days.get(5)}', 0)" +
					"UNION (SELECT '${days.get(6)}', 0)) A " +
					"GROUP BY A.scanTime  " +
					"ORDER BY A.scanTime DESC") 
			 */
			
			String sql = "SELECT A.days, A.coun FROM (SELECT DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') AS days, COUNT(rowkey) COUN " +
					"FROM tb_anti_fake_" + vendorId + " DOC " +
					"WHERE DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') <='" + days.get(6) +"' AND  " +
					" DATE_FORMAT(DOC.SCANTIME, '%Y%m%d')>='" + days.get(0) + "' " +
							" GROUP BY days " +
							"UNION (SELECT '"+days.get(0)+"', 0) " +
							"UNION (SELECT '"+days.get(1)+"', 0) " +
							"UNION (SELECT '"+days.get(2)+"', 0) " +
							"UNION (SELECT '"+days.get(3)+"', 0) " +
							"UNION (SELECT '"+days.get(4)+"', 0) " +
							"UNION (SELECT '"+days.get(5)+"', 0) " +
							"UNION (SELECT '"+days.get(6)+"', 0)) A " +
							"GROUP BY A.days " +
							"ORDER BY A.days DESC " ;				
			 weekScanCount = 
					wxAntiFakeDao.getWeekScan(sql);				
			/*从缓存中没有取得数据*/
			if(weekScanCount == null)
			{
				logger.info("没有取得统计信息,用户ID: " + userId);
				result.put("msg","没有取得统计信息!" );
				return result;
			}
			logger.info("已将数据库读取信息存入到Memcached中");	
			XMemcachedHelper.set("weekScanCount_" + userId,weekScanCount, 30 * 60);
			
		}
		catch(Exception e)
		{
			logger.info("根据ID查询统计情况时出现:" + e);
			result.put("msg", "系统异常请重试!");
			return result;
		}
		//返回数据
		result.put("weekScanCount", weekScanCount);
		
		return result;
	}

	@Override
	public int addScanCountInfo(StatAntiFakeCount antiFakeCount) {
		return wxAntiFakeDao.addScanInfo(antiFakeCount);
	}
	/**
	 * 
	 * 桑基图接口
	 * @return
	 */

	@Override
	public List<StatAntiFakeCount> mapCount(Integer vendorId) {
		return wxAntiFakeDao.mapCount(vendorId);
	}
	/**
	 * statisticsSecurityInfo
	 * 获取扫码信息记录
	 * 
	 */

	@Override
	public List<StatAntiFakeCount> getSucurityScanInfo(Integer vendorId) {
		return wxAntiFakeDao.getAntifakeInfo(vendorId);
	}

	@Override
	public JSONObject getAllCountList() {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		int vendorTotalScanCount = 0;
		List<TotalCount> totalCountPri = orderDao.findAllVendorIdAndVendorName();
		List<TotalCount> list = new ArrayList<TotalCount>();	
		int totalCount = 0;
		if (totalCountPri != null) {
			for(int i = 0;i < totalCountPri.size();i++){
				TotalCount total = new TotalCount();
				Integer vendorId = totalCountPri.get(i).getVendorId();
				String tbName = orderDao.selectDBTable("tb_anti_fake_" + vendorId);
				if(tbName != null){
					vendorTotalScanCount = wxAntiFakeDao.getAntifakeCount(vendorId);
				}else{
					vendorTotalScanCount = 0;
				}
				totalCount = vendorTotalScanCount + totalCount;
				// 商家总的扫码量
				total.setAntiCount(vendorTotalScanCount);
				total.setVendorName(totalCountPri.get(i).getVendorName());
				total.setVendorId(vendorId);
				total.setTotalCount(vendorTotalScanCount);
				list.add(total);				
			}							
		} 
		array.add(list);
		result.put("data", array);
		result.put("totalCount", totalCount);
		return result;
	}

	@Override
	public JSONObject getSystemWeekTotalCount(Integer userId, Integer vendorId){
		/**
		 * 使用DateFormat生成数组与数据库中查询的数据进行匹配
		 * 替换数据库中查询对应天数为null不显示的部分为0
		 * 
		 */
		JSONObject result = new JSONObject();
		//添加缓存信息
		List<WeekScanCountEveDayVO> weekScanCount;
		try
		{
			/*从缓存中能拿到数据*/
//			weekScanCount = (List<WeekScanCountEveDayVO>)XMemcachedHelper.findCache("weekScanCount_" + userId);
//			
//			if(weekScanCount != null)
//			{
//				logger.info("从Memcached中取得了Order信息");
//				result.put("weekScanCount", weekScanCount);
//				return result;
//			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			List<String> weekDay = new ArrayList<String>();
			Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
			for(int i=1; i < 8; i++){

				calendar1.add(Calendar.DATE, -1 );
				String times = sdf.format(calendar1.getTime());
				weekDay.add(times);
				}
					
			/**
			 * 获取所有生成二维码的
			 */	
			WeekDays weekdays = new WeekDays();
			weekdays.setDate1( weekDay.get(6));
			weekdays.setDate2( weekDay.get(5));
			weekdays.setDate3( weekDay.get(4));
			weekdays.setDate4( weekDay.get(3));
			weekdays.setDate5( weekDay.get(2));
			weekdays.setDate6( weekDay.get(1));
			weekdays.setDate7( weekDay.get(0));		
			List<String> days = new ArrayList<String>();
			days.add(weekdays.getDate1());
			days.add(weekdays.getDate2());
			days.add(weekdays.getDate3());
			days.add(weekdays.getDate4());
			days.add(weekdays.getDate5());
			days.add(weekdays.getDate6());
			days.add(weekdays.getDate7());
			/**
			 * SELECT A.scanTime, A.coun FROM (SELECT DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') AS SCANTIME, COUNT(1) COUN  " +
					"FROM tb_anti_fake_#{vendorId} DOC " +
					"WHERE " +
					"DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') <='${days.get(6)}'  AND  DATE_FORMAT(DOC.SCANTIME, GROUP BY scanTime '%Y%m%d')>='${days.get(0)}'" +
					" UNION (SELECT '${days.get(0)}', 0) " +
					"UNION (SELECT '${days.get(1)}', 0) " +
					"UNION (SELECT '${days.get(2)}', 0) " +
					"UNION (SELECT '${days.get(3)', 0) " +
					"UNION (SELECT '${days.get(4)}', 0) " +
					"UNION (SELECT '${days.get(5)}', 0)" +
					"UNION (SELECT '${days.get(6)}', 0)) A " +
					"GROUP BY A.scanTime  " +
					"ORDER BY A.scanTime DESC") 
			 */
			
			String sql = "SELECT A.days, A.coun FROM (SELECT DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') AS days, COUNT(id) AS COUN " +
					"FROM yunma.tb_anti_fake_count DOC " +
					"WHERE DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') <='" + days.get(6) +"' AND  " +
					" DATE_FORMAT(DOC.SCANTIME, '%Y%m%d')>='" + days.get(0) + "' " +
							" GROUP BY days " +
							"UNION (SELECT '"+days.get(0)+"', 0) " +
							"UNION (SELECT '"+days.get(1)+"', 0) " +
							"UNION (SELECT '"+days.get(2)+"', 0) " +
							"UNION (SELECT '"+days.get(3)+"', 0) " +
							"UNION (SELECT '"+days.get(4)+"', 0) " +
							"UNION (SELECT '"+days.get(5)+"', 0) " +
							"UNION (SELECT '"+days.get(6)+"', 0)) A " +
							"GROUP BY A.days " +
							"ORDER BY A.days DESC " ;				
			 weekScanCount = 
					wxAntiFakeDao.getWeekScan(sql);	
			 
			/*从缓存中没有取得数据*/
//			if(weekScanCount == null)
//			{
//				logger.info("没有取得统计信息,用户ID: " + userId);
//				result.put("msg","没有取得统计信息!" );
//				return result;
//			}
//			logger.info("已将数据库读取信息存入到Memcached中");	
//			XMemcachedHelper.set("weekScanCount_" + userId,weekScanCount, 30 * 60);
			
		}
		catch(Exception e)
		{
			logger.info("根据ID查询统计情况时出现:" + e);
			result.put("msg", "系统异常请重试!");
			return result;
		}
		//返回数据
		result.put("weekScanCount", weekScanCount);
		logger.debug("weekedScanCount: {}" +weekScanCount );
		
		return result;
	}

	@Override
	public List<AntiFake> findAntiFakeListInfo(Integer vendorId) {
		
		return wxAntiFakeDao.findAntiFakeListInfoDetail(vendorId);
	}

}
