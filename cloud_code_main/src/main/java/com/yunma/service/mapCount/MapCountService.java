package com.yunma.service.mapCount;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.sankeydiagram.SankeyDiagram;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.User;
import com.yunma.vo.mapCount.MapCountVO;

public interface MapCountService {

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param orderId
	 * @return
	 */
	List<MapCountVO> findAntiFakeCountProvinceByDateAndOrderId(
			String startDate, String endDate, User user, Integer orderId);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param productName
	 * @return
	 */
	List<MapCountVO> findAntiFakeCountProvinceByDateAndProductName(
			String startDate, String endDate, User user, String productName);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @return
	 */

	List<MapCountVO> findAntiFakeCountProvinceByDate(String startDate,
			String endDate, User user);

	/**
	 * 
	 * @param list
	 * @param name
	 * @param choice
	 * @return
	 */

	public List<MapCountVO> addCityAndDistrictDeaultMap(List<MapCountVO> list,
			String name, int choice);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param orderId
	 * @param province
	 * @return
	 */

	List<MapCountVO> findAntiFakeCountCityByDateAndOrderId(String startDate,
			String endDate, User user, Integer orderId, String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param productName
	 * @param province
	 * @return
	 */
	List<MapCountVO> findAntiFakeCountCityByDateAndProductName(
			String startDate, String endDate, User user, String productName,
			String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param province
	 * @return
	 */
	List<MapCountVO> findAntiFakeCountCityByDate(String startDate,
			String endDate, User user, String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param orderId
	 * @param city
	 * @return
	 */
	List<MapCountVO> findAntiFakeCountDistrictByDateAndOrderId(
			String startDate, String endDate, User user, Integer orderId,
			String city);
/**
 * 
 * @param startDate
 * @param endDate
 * @param user
 * @param productName
 * @param city
 * @return
 */
	List<MapCountVO> findAntiFakeCountDistrictByDateAndProductName(
			String startDate, String endDate, User user, String productName,
			String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param user
	 * @param city
	 * @return
	 */
List<MapCountVO> findAntiFakeCountDistrictByDate(String startDate,
		String endDate, User user, String city);

/**
 * 扫码数据统计
 * @param user
 * @param orderId
 * @param productName					
 * @param startDate
 * @param endDate
 * @return
 */
	String sanKey(User user, Integer orderId, String productName,
			String startDate, String endDate);
	
	/**
	 * 统计某省中所有城市的扫码数量
	 * @param vendorId
	 * @param province
	 * @return
	 */
	List<StatAntiFakeCount> mapCountForCity(Integer vendorId,String province);
	
	/**
	 * 统计某市各区县的扫码量
	 * @param vendorId
	 * @param district
	 * @return
	 */
	List<StatAntiFakeCount> getDistrCount(Integer vendorId,String city);

	/**
	 * 热力图区县数据统计
	 * @param vendorId
	 * @param district
	 * @return
	 */
	List<StatAntiFakeCount> heatMapForDistr(Integer vendorId,String district);
	
	/**
	 * 获取桑基图的数据
	 * @param vendorId
	 * @param agentId
	 * @return
	 */
	SankeyDiagram getSankeyDiagramData(Integer vendorId);

	
}
