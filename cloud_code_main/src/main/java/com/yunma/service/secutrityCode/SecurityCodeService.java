package com.yunma.service.secutrityCode;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.utils.PageBean;
public interface SecurityCodeService {
	/**
	 * 匹配二维码实现防伪
	 * @param orderId
	 * @return
	 */
	SecurityCode getSecurityCode(String securityCode);
	/**
	 * 分页查询二维码
	 * @param map
	 * @return
	 */
	PageBean getSecurityInfo(PageBean pageBean, SecurityCode code,
			Integer orderId);
	
	/**
	 * 根据订单id查询二维码信息
	 * @param orderId
	 * @return
	 */
	public List<SecurityCode> getSecurityCodeByOrderId(Integer orderId);
	
	
	/**
	 * 厂商导出已生成二维码
	 * @param response
	 * @param orderId
	 */
	public void exportBatchCodeTxt(HttpServletResponse response, Integer orderId);
	
	/**
	 * 查询订单下的二维码下载次数
	 * 
	 */
	public int getDownloadCount(Integer orderId);
	
	/**
	 * 追加功能,已扫描二维码追加1
	 * @param securityCode
	 * @return
	 */
	int getScanCountpri(Integer orderId,String securityCode);
	
	/**
	 * 追加二维码表扫描次数
	 * @param orderId
	 * @param codeScanCount
	 * @return
	 */
	int updateCodeScanCount(Integer orderId, Integer codeScanCount,String securityCode);
	/**
	 * 获取NickName
	 * 
	 */
	String getNickNameByOpenId(String openId);
	
	/**
	 * 获取头像
	 */
	String getPicUrlByOpenId(String openId);
	
	/**
	 * 
	 */
	 Integer findCodeScanCount( String securityCode,Integer orderId);
	
	
	
}
