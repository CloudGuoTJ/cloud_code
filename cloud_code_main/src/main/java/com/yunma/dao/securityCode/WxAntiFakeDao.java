package com.yunma.dao.securityCode;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.AntiFake;
import com.yunma.vo.exceptionAntiFake.ExceptionAntiFakeVO;
import com.yunma.vo.mapCount.WeekScanCountEveDayVO;

/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public interface WxAntiFakeDao extends BaseMapper {
	
	/**
	 * 将扫码信息的数据存入表中
	 * 
	 */
	@Insert("INSERT INTO " +
			" tb_anti_fake_count " +
			"SET" +
			" vendorId=#{vendorId}, securityCode=#{securityCode}, openId=#{openId}, orderId=#{orderId}, address=#{address},province=#{province}, city=#{city},district=#{district}, longitude=#{longitude},latitude=#{latitude},scanTime=now() ")

	int addScanInfo(StatAntiFakeCount antiFakeCount);

	
	/**
	 * 新增一周扫码数量统计
	 * @param weekDate
	 * @param weekScan
	 * @return
	 */
		@Select("${sql}") 
		public List<WeekScanCountEveDayVO> getWeekScan(@Param("sql") String sql);
	
	/**
	 * 查询过去一天扫码量
	 * 
	 */
	@Select("SELECT count(*) FROM tb_anti_fake_#{vendorId} WHERE TO_DAYS( NOW( ) ) - TO_DAYS( scanTime) <= 1")
	int findDayScanCount(@Param("vendorId") Integer vendorId);
			
	
/*放弃使用*/	
//	/**
//	 * 统计过去一周扫码数量
//	 */
//	@Select("SELECT i.scanTime,count(*) from tb_anti_fake_#{orderId} i"
//			+ "where"
//			+ " scanTime>=date_sub(now(),interval dayofweek(now()) day) and scanTime <= date_add(now(),interval 7-dayofweek(now()) day) group by i.scanTime")
//	List<WeekScanCountEveDayVO> findWeekScanCount(
//			@Param("orderId") Integer orderId);
	
	
	/**
	 * 统计异常扫码数量
	 * 类型1.同一二维码被多次扫码,记录异常扫码次数
	 */
	
	@Select("select count(*) as count from tb_anti_fake_#{vendorId} group by securityCode having count>1")
	List<String> totalCountExceptionAntiFake(@Param("vendorId") Integer vendorId);
	
	/**
	 * 统计异常扫码数量
	 * 类型1.同一二维码被多次扫码,记录异常二维码和扫码次数
	 */
	
	@Select("select securityCode,count(*) as count from tb_anti_fake_#{vendorId} group by securityCode having count>1;")
	ExceptionAntiFakeVO countExceptionAntiFake(@Param("vendorId") Integer vendorId);
	
	/**
	 * 匹配二维码
	 * 
	 * @param orderId
	 * @param securityCode
	 * @return
	 */
	@Select("SELECT * from tb_anti_fake_#{vendorId} where securityCode=#{securityCode} order by scanTime asc limit 1")
	AntiFake findBySecurityCode(@Param("vendorId") Integer vendorId,
			String securityCode);

	/**
	 * 将扫码信息存入表中
	 * 
	 * @param orderId
	 * @param antiFake
	 * @return
	 */
	@Insert("insert ignore into tb_anti_fake_${vendorId} set "
			+ "securityCodeId=#{antiFake.securityCodeId}, "
			+ "securityCode=#{antiFake.securityCode}, "
			+ "productId=#{antiFake.productId}, "
			+ "productName=#{antiFake.productName}, "
			+ "scanAddress=#{antiFake.scanAddress}, "
			+ "longitude=#{antiFake.longitude}, "
			+ "latitude=#{antiFake.latitude}, " + "userId=#{antiFake.userId}, "
			+ "scanTime=now(), " + "scanCount=#{antiFake.scanCount}, "
			+ "openId=#{antiFake.openId}, " + "province=#{antiFake.province}, "
			+ "city=#{antiFake.city}, " + "district=#{antiFake.district}, "
			+ "productScanCount=#{antiFake.productScanCount}")
	int insertAntiFake(@Param("vendorId") Integer vendorId,
			@Param("antiFake") AntiFake antiFake);

	/**
	 * 结存人数据库
	 * 
	 * @param orderId
	 * @param antiFake
	 * @return
	 */

	@Insert("insert into tb_anti_fake_#{vendorId} set securityCodeId=#{antiFake.securityCodeId},securityCode=#{antiFake.securityCode} ,productId=#{antiFake.productId},"
			+ "productName=#{antiFake.productName},"
			+ "scanTime=now(),scanCount=#{antiFake.scanCount},openId=#{antiFake.openId},province=#{antiFake.province},city=#{antiFake.city}")
	int createAntiFake(@Param("vendorId") Integer vendorId,
			@Param("antiFake") AntiFake antiFake);

	/***
	 * 根据openID查询是否存在已扫码用户
	 * 
	 * @param orderId
	 * @param openId
	 * @return
	 */
	@Select("SELECT * FROM tb_anti_fake_#{vendorId} WHERE openId=#{openId} ORDER BY scanTime DESC LIMIT 1")
	int findAntiFake(@Param("vendorId") Integer vendorId,
			@Param("openId") String openId);

	/**
	 * 根据orderId和openId查询扫码信息
	 * 
	 * @param orderId
	 * @param openId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_anti_fake_#{vendorId} WHERE openId=#{openId} ORDER BY scanTime DESC LIMIT 1")
	AntiFake findAntiFakeInfo(@Param("vendorId") Integer vendorId,
			@Param("openId") String openId);

	/**
	 * 统计同一二维码被扫描次数
	 */
	@Select("SELECT COUNT(*) FROM yunma.tb_anti_fake_#{vendorId} WHERE securityCode=#{securityCode}")
	int findScanCount(@Param("vendorId") Integer vendorId,
			@Param("securityCode") String securityCode);

	/**
	 * 统计同一微信用户扫码次数
	 * 
	 * @param orderId
	 * @param securityCode
	 * @return
	 */
	@Select("SELECT COUNT(*)FROM yunma.tb_anti_fake_#{vendorId} WHERE openId =#{openId}")
	int findIsScan(@Param("vendorId") Integer vendorId,
			@Param("openId") String openId);
	/**
	 * 更新经纬度
	 */
	@Update("UPDATE yunma.tb_anti_fake_#{vendorId} SET longitude=#{longitude},latitude=#{latitude} where openId=#{openId} order by lastUpdateTime desc Limit 1")
	int updateLocation(
			@Param("openId") String openId,
			@Param("vendorId") Integer vendorId,
			@Param("longitude") String longitude,
			@Param("latitude") String latitude);
	
	/**
	 * 统计但各厂商订单扫码数量
	 * @param orderId
	 * @return
	 */
	@Select("SELECT Count(*) from yunma.tb_anti_fake_#{vendorId};")
	int getAntifakeCount(@Param("vendorId") Integer vendorId);
	/**
	 * 查询第一个扫码的人的信息
	 * @param securityCode
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_anti_fake_#{vendorId}  WHERE securityCode=#{securityCode} ORDER BY scanTime LIMIT 1;")
	AntiFake findFirstScanInfo(@Param("securityCode")String securityCode,@Param("vendorId")Integer vendorId);


	/**
	 * 热力图统计
	 * @param vendorId
	 */
	@Select("SELECT province,count(*) count FROM `tb_anti_fake_count` where vendorId=#{vendorId} GROUP BY province")
	public List<StatAntiFakeCount> mapCount(Integer vendorId);

	/**
	 * 商家扫码统计
	 * @param vendorId
	 * @return
	 */
	// @Select("SELECT * FROM tb_anti_fake_count WHERE vendorId=#{vendorId}  group by openId")
	@Select("SELECT * FROM yunma.tb_anti_fake_count WHERE vendorId=#{vendorId} order by scanTime desc LIMIT 20 ")
	List<StatAntiFakeCount> getAntifakeInfo(Integer vendorId);
	/**
	 * 根据二维码查询扫码信息
	 * 应对错误删除订单仍有记录信息
	 * @param securityCode
	 * @return
	 */
	
	@Select("SELECT * FROM yunma.tb_anti_fake_#{vendorId} WHERE securityCode=#{securityCode} limit 1")
	AntiFake findAntiFakeBySecurityCode(@Param("securityCode")String securityCode,@Param("vendorId") Integer vendorId);

	/**
	 * 针对厂商需求,加碘盐未获取扫码信息追加记录
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT * FROM yunma.tb_anti_fake_#{vendorId} group by openId  order by scanTime desc limit 20;")
	List<AntiFake> findAntiFakeListInfoDetail(@Param("vendorId")Integer vendorId);
	
	

		
}
