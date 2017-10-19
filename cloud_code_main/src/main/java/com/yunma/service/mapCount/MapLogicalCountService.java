package com.yunma.service.mapCount;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.AntiFake;
import com.yunma.model.TotalCount;

/**
 * 统计数据Service
 * @author Administrator
 *
 */
public interface MapLogicalCountService {
	/**
	 * 商家扫码统计
	 * @param userId
	 * @param vendorId
	 * @return
	 */
	public JSONObject getCount(Integer userId, Integer vendorId);
	
	/**
	 * 管理员统计
	 * @param userId
	 * @return
	 */
	public JSONObject getTotalCount(Integer userId);
	/**
	 * 商家二维码页面统计
	 * 	
	 */
	public JSONObject getMapSecurityCount(Integer userId, Integer vendorId);
	
	/**
	 * 处理七天数据库返回数据信息
	 * @param userId
	 * @param vendorId
	 * @return
	 */

	public JSONObject getWeekTotalCount(Integer userId, Integer vendorId);
	
	/**
	 * 添加扫码统计信息
	 * 
	 */
	int addScanCountInfo(StatAntiFakeCount antiFakeCount);

	/**
	 * 热力图统计
	 * @param vendorId
	 * @return 
	 */
	public List<StatAntiFakeCount> mapCount(Integer vendorId);
	
	/**
	 * 获取扫码信息
	 * 
	 */
	public List<StatAntiFakeCount> getSucurityScanInfo(Integer vendorId);
/***
 * 获取统计数据
 * @return
 */
	public JSONObject getAllCountList();
/**
 * 查询企业七天扫码量
 * @param userId
 * @param vendorId
 * @return
 */
public JSONObject getSystemWeekTotalCount(Integer userId, Integer vendorId);

/**
 * 针对加碘盐商家扫码信息的记录
 * @param vendorId
 * @return
 */
public List<AntiFake> findAntiFakeListInfo(Integer vendorId);
}
