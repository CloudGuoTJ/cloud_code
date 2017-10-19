package com.yunma.dao.weChart;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.model.VendorHtmlActivBackInfo;
import com.yunma.model.VendorHtmlActivInfo;
import com.yunma.vo.wechatHtml.VendorHtmlActivInfoVO;

/**
 * 微信回调授权控制表
 * @author Administrator
 *
 */

public interface VendorHtmlActivInfoDao extends BaseMapper{
	
	/**
	 * 存入回调首页显示的状态
	 */
	
//	/*    @Insert("insert into tb_user set userName=#{userName},passwd=#{passwd},status=1,createTime=now(),lastUpdateTime=now()," +
//	            "userType=#{userType},vendorId=#{vendorId},vendorName=#{vendorName}")*/
//	@Insert("INSERT INTO" +
//			" tb_vendor_activ_info " +
//			"SET " +
//			"comment=#{comment}," +
//			"vendorId=#{vendorId}," +
//			"htmlUri=#{htmlUri}," +
//			"httpName=#{htmlName}," +
//			"getRedEnv=#{getRedEnv}," +
//			"productInfo=#{productInfo}," +
//			"vendorHttp=#{vendorHttp}," +
//			"weShop=#{weShop}," +
//			"spree=#{spree}," +
//			"securityAndTraceability=#{securityAndTraceability}," +
//			"createTime=now()")
	int saveHtmlInfo(VendorHtmlActivInfo vendorHtmlActivInfo);
	
	/**
	 * 查询回调显示页的信息通过vendorId
	 */
	@Select("SELECT * FROM tb_vendor_activ_info where httpName=#{htmlName}")
	VendorHtmlActivInfo findVendorHtmlActivInfoByHtmlName(@Param("htmlName")String htmlName);
	
	@Select("SELECT * FROM tb_vendor_activ_info where vendorId=#{vendorId} order by actionId desc")
	List<VendorHtmlActivInfoVO> findVendorHtmlActivInfoByVendorId(@Param("vendorId")
			Integer vendorId);
	
	/***
	 * 添加条件 status:0,未启用,1,已启用
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT a.httpName FROM tb_vendor_activ_info a, tb_anti_fake_manager b Where b.orderId=#{orderId} and a.status =1 order by a.createTime desc limit 1;")
	String findUrlByOrderId(@Param("orderId") Integer orderId);
	
	
	@Select("SELECT a.urlName FROM yunma.tb_vendor_activ_info a, yunma.tb_anti_fake_manager b Where a.actionId=b.mode_id And b.orderId=#{orderId} and b.activity_status=1 AND b.funcFlag=#{funcFlag}")
	String findUrlNameByOrderId(@Param("orderId") Integer orderId,@Param("funcFlag")Integer funcFlag);
	/**
	 * 搜索tb_anti_fake_manager
	 * 返回数据信息

	 */
	@Select("SELECT getRedEnv, productInfo, vendorHttp, weShop ,spree ,securityAndTraceability  FROM tb_anti_fake_manager Where orderId=#{orderId} LIMIT 1")
	List<VendorHtmlActivBackInfo> findVendorHtmlActivInfoByOrderId(@Param("orderId") Integer orderId);
	
	
	
	@Select("SELECT * FROM tb_vendor_activ_info where actionId=#{actionId}")
	VendorHtmlActivInfoVO getVendorHtmlActivById(@Param("actionId")Integer actionId);
	/**
	 * 查询回调模板是否已存在
	 * @param vendorId
	 * @param httpName
	 * @return
	 */
	@Select("SELECT * FROM tb_vendor_activ_info where vendorId=#{vendorId} AND httpName=#{httpName}")
	VendorHtmlActivInfo findExistNameInfo(@Param("vendorId")Integer vendorId, @Param("httpName")String httpName);
	/**
	 * 更新模板可变内容
	 * @param actionId
	 * @param storeDate
	 * @return
	 */
	@Update("UPDATE tb_vendor_activ_info SET storeData=#{storeData} where actionId=#{actionId};")
	int updateVendorHtmlActivInfoById(@Param("actionId")Integer actionId,@Param("storeData")String storeData);
	/**
	 * 根据actionId查询模板信息
	 * @param actionId
	 * @return
	 */
	@Select("SELECT * FROM tb_vendor_activ_info where actionId=#{actionId}")
	VendorHtmlActivInfo findVendorHtmlActivInfoByActionId(@Param("actionId")Integer actionId);
	
	
	
	
	

}
