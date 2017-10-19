package com.yunma.dao.mapCount;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.vo.mapCount.MapCountVO;

/**
 * 针对扫码首页的统计分析构筑地图的数据层
 * 
 * @author Administrator
 * 
 */
public interface MapCountDao extends BaseMapper {
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param orderId
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and orderId=#{2} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDateAndOrderIdByAdmin(
			String startDate, String endDate, int orderId);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param orderId
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and orderId=#{3} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDateAndOrderId(
			String startDate, String endDate, Integer vendorId, int orderId);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param productName
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and productName=#{2} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDateAndProductNameByAdmin(
			String startDate, String endDate, String productName);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param productName
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and productName=#{3} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDateAndProductName(
			String startDate, String endDate, Integer vendorId,
			String productName);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDateByAdmin(String startDate,
			String endDate);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @return
	 */
	@Select("select province as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and province is not null group by province")
	List<MapCountVO> findAntiFakeCountProvinceByDate(String startDate,
			String endDate, Integer vendorId);

	/**
	 * 
	 * @param areaNo
	 * @return
	 */
	@Select("select area_name as name from t_map_area where parent_no=#{0}")
	List<MapCountVO> findCitynameOrDistrictnameByAreaNo(int areaNo);

	/**
	 * 
	 * @param name
	 * @return
	 */
	@Select("select area_no from tb_map_area where area_name like CONCAT('%',#{0},'%' ) and area_level=1")
	int findProAreaNo(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	@Select("select area_no from tb_map_area where area_name=#{0} and area_level=2")
	int findCityAreaNo(String name);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param orderId
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and orderId=#{2} and city=#{3} and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDateAndOrderIdByAdmin(
			String startDate, String endDate, Integer orderId, String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param orderId
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and orderId=#{3} and city=#{4} and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDateAndOrderId(
			String startDate, String endDate, Integer vendorId,
			Integer orderId, String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param productName
	 * @param province
	 * @return
	 */
	@Select("select city as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and productName=#{2} and province=#{3} and province is not null group by city")
	List<MapCountVO> findAntiFakeCountCityByDateAndProductNameByAdmin(
			String startDate, String endDate, String productName,
			String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param productName
	 * @param province
	 * @return
	 */
	@Select("select city as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and productName=#{3} and province=#{4} and province is not null group by city")
	List<MapCountVO> findAntiFakeCountCityByDateAndProductName(
			String startDate, String endDate, Integer vendorId,
			String productName, String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param province
	 * @return
	 */
	@Select("select city as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and province=#{2}  and province is not null group by city")
	List<MapCountVO> findAntiFakeCountCityByDateByAdmin(String startDate,
			String endDate, String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param province
	 * @return
	 */
	@Select("select city as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and province=#{3}  and province is not null group by city")
	List<MapCountVO> findAntiFakeCountCityByDate(String startDate,
			String endDate, Integer vendorId, String province);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param productName
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and productName=#{3} and city=#{4} and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDateAndProductName(
			String startDate, String endDate, Integer vendorId,
			String productName, String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param productName
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and productName=#{2} and city=#{3} and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDateAndProductNameByAdmin(
			String startDate, String endDate, String productName, String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and city=#{2}  and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDateByAdmin(String startDate,
			String endDate, String city);

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param vendorId
	 * @param city
	 * @return
	 */
	@Select("select district as name,sum(scanCount) as value from tb_anti_fake_count where Date(scanTime)>=#{0} and Date(scanTime)<=#{1} and vendorId=#{2} and city=#{3}  and province is not null group by district")
	List<MapCountVO> findAntiFakeCountDistrictByDate(String startDate,
			String endDate, Integer vendorId, String city);
	
	
	/**
	 * 统计某省中所有城市的扫码数量
	 * @param vendorId
	 * @param province
	 * @return
	 */
	@Select("select city,count(*) count from tb_anti_fake_count where vendorId=#{0} and province=#{1} group by city")
	List<StatAntiFakeCount> mapCountForCity(Integer vendorId,String province);
	
	
	/**
	 * 统计某市各区县的扫码量
	 * @param vendorId
	 * @param district
	 * @return
	 */
	@Select("select district,count(*) count from tb_anti_fake_count where vendorId=#{0} and city=#{1} group by district")
	List<StatAntiFakeCount> getDistrCount(Integer vendorId,String city);
	
	/**
	 * 热力图区县数据统计
	 * @param vendorId
	 * @param district
	 * @return
	 */
	@Select("SELECT longitude,latitude,COUNT(*) COUNT FROM tb_anti_fake_count WHERE vendorId=#{0} AND district=#{1} GROUP BY longitude,latitude ")
	List<StatAntiFakeCount> heatMapForDistr(Integer vendorId,String district);
	
	/**
	 * 判断表是否存在
	 * @param tableName
	 * @return
	 */
	@Select("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='yunma' AND TABLE_NAME=#{tableName}")
	String isExistTable(String tableName);
	
	/**
	 * 查询溯源表中产品数量
	 * @param tableName
	 * @param agentId
	 * @return
	 */
	@Select("select sum(countPro) countPro from tb_product_tracing_agent_scan_#{vendorId} where agentId=#{agentId}")
	Integer getProCount(@Param("vendorId")Integer vendorId,@Param("agentId")Integer agentId);
	
}
