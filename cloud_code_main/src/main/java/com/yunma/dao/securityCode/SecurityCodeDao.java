package com.yunma.dao.securityCode;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.securityCode.SecurityCode;

/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public interface SecurityCodeDao extends BaseMapper {
	/**
	 * 根据订单生成二维码设定： 追加订单直接通过insert 根据产品订单直接生成二维码
	 * 删除订单，根据orderId直接删除指定order下所有二维码未使用的也将失效
	 * 
	 * @param orderId
	 * @param i
	 * @param num
	 * @return
	 */
	/* 生成二维码表 */
	/* 显示根据商家的订单号生成的二维码列表 设定显示条数 */
	@Select("CELECT * FROM tb_security_code WHERE orderId=#{0} LIMIT #{1}, #{2}")
	List<SecurityCode> findSecurityCodeByOrderId(Integer orderId, Integer i,
			Integer num);

	/* 删除二维码 根据订单信息删除 */
	@Delete("DELETE  FROM tb_security_code WHERE orderId=#{orderId}")
	int deleteSecurityCodeByOrderId(int orderId);

	/**
	 * 分页查询 二维码
	 */
	@Select("SELECT COUNT(*) FROM tb_security_code_#{orderId}")
	int getSecurityCount(@Param("orderId") Integer orderId);

	@Select("SELECT securityCodeId,orderId,productId,productName," +
			"codeFlag,codePrefix,securityCode,codeTailTag,createRowNum,createDate " +
			" FROM tb_security_code_#{orderId} " +
			"ORDER BY " +
			"securityCodeId ASC " +
			"LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<SecurityCode> getSecurityCodeInfo(Map<String, Object> map);


	  @Select("select * from tb_security_code_#{orderId} where securityCode= #{securityCode}")
	    SecurityCode findBySecurityCode(@Param("orderId")Integer orderId, @Param("securityCode")String securityCode);


	/**
	 * 根据订单id查询二维码信息
	 * @param orderId
	 * @return
	 */
	@Select(
			"SELECT s.securityCodeId,s.orderId,s.productId,s.productName,s.codeFlag,s.codePrefix,s.securityCode,s.createDate,s.codeTailTag,s.createRowNum "+
		"FROM tb_security_code_#{orderId} s "
			)
	public List<SecurityCode> getSecurityCodeByOrderId(Integer orderId);
	
	/**
	 * 查询统计已生成的二维码数量
	 */
	@Select("SELECT COUNT(securityCodeId) FROM tb_security_code_#{orderId}")
	int getGanerateCodeCount(@Param("orderId") Integer orderId);
	/**
	 * 获取厂商指定订单的二维码信息
	 * @param orderId
	 * @return
	 */
	@Select("SELECT securityCode from tb_security_code_#{orderId}")
	List<String> getCodeList(@Param("orderId")Integer orderId);
	/**
	 * 查二维码订单downcount
	 */
	@Select("SELECT downLoadCount from tb_product_order WHERE orderId=#{orderId}")
	int findDownCountByOrderId(@Param("orderId")Integer orderId);
	/**
	 * 将导出二维码的订单downloadCount数量加一
	 * @param orderId
	 * @return
	 */
	@Update("UPDATE tb_product_order SET downLoadCount=#{downcount} WHERE orderId=#{orderId}")
	 int updateDownloadCount(@Param("orderId")Integer orderId,@Param("downcount")Integer downcount);
	/**
	 * 追加查询扫码次数tb_security_code_#{orderId}
	 * @param securityCode
	 * @return
	 */
	@Select("SELECT scanFlag from tb_security_code_#{orderId} WHERE securityCode=#{securityCode}")
	int findProductScanCountByCode(@Param("orderId")Integer orderId,@Param("securityCode")String securityCode);
	/**
	 * 追加扫描次数
	 * @param orderId
	 * @param codeScanCount
	 * @return
	 */
	@Update("UPDATE tb_security_code_#{orderId} SET scanFlag=#{codeScanCount} WHERE securityCode=#{securityCode}")
	int updateScanCount(@Param("orderId")Integer orderId,@Param("codeScanCount")Integer codeScanCount,@Param("securityCode")String securityCode);
	/**
	 * 查询NickName
	 * @param openId
	 * @return
	 */
	@Select("SELECT nickName from tb_wx_user WHERE openId=#{openId}")
	String findNickNameByOpenId(@Param("openId")String openId);
	 /**
	  * 
	  * @param openId
	  * @return
	  */
	@Select("SELECT headImgurl from tb_wx_user WHERE openId=#{openId}")
	String findPicUrlByOpenId(@Param("openId")String openId);

	/**
	 * 查询扫码重复数量
	 */
	@Select("SELECT Count(*) from tb_anti_fake_#{vendorId} WHERE securityCode=#{securityCode}")
	Integer findCodeScanCount(@Param("securityCode") String securityCode,
			@Param("vendorId") Integer vendorId);

}
