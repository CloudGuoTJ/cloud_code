package com.yunma.service.antiFakeService;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.common.util.ResultObject;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.AntiFake;
import com.yunma.model.Location;
import com.yunma.model.ScanPageConfig;
import com.yunma.model.User;
import com.yunma.model.WxConfig;
import com.yunma.vo.antiFake.AntiFakeAnalyseVO;
import com.yunma.vo.antiFake.AntiFakeVO;

public  interface AntiFakeService {
	

	    /**
	     * 新增防伪扫码记录
	     * @param code 二维码
	     * @param openId 微信用户标识
	     * @param location 地理位置
	     * @param productScanCount 产品扫码次数
	     * @param result 结果
	     */
	    void createAntiFake(SecurityCode code, String openId, Location location, int productScanCount, ResultObject result);
	    /**
	     * 将antiFake信息存入扫码表中
	     * @param orderId
	     * @param antiFake
	     * @return
	     */
	    int createOldAntiFake(Integer orderId,AntiFake antiFake);
	    /**
	     * 更新扫码表
	     * @param perfixOrder
	     * @param antiFake
	     * @return
	     */
	    int updateAntiFake(Integer OrderId, AntiFake antiFake);

	    AntiFake findFirstFakeBySecurityCode(String perfixOrder, String scanResult);

	    AntiFake findOldFirstFakeBySecurityCode(String scanResult);

	    List<AntiFakeVO> findOldAntiFakeList(int scanCount, String productName, User user);

	    List<AntiFakeVO> findAntiFakeList(int scanCount, String productName, User user, String perfixOrderNo);

	    int antiFakeCount(User user);

	    int findScanCountByOrderList(List<ProductOrder> orderList);

	    //把该二维码的钱设置为已领取--新模式
//	    int setAntiFakeMoneyNewWay(String prefix, String securityCode, String dbName);

	    List<AntiFakeAnalyseVO> findAntiFakeListByAnlysis(String openId, User user, String perfixOrderNo);

	    /**
	     * 从配置文件中获取二维码
	     * @param shortCode 短码
	     * @param shortUrl 域名
	     * @return
	     */
	    String getSecurityCodeByConfig(String shortCode, String shortUrl);

	    /**
	     * 微信WEB回调
	     * @param securityCode
	     * @param wxConfig 微信配置
	     * @param isGrant 是否授权
	     * @return
	     */
	    ModelAndView webCallBack(String securityCode, WxConfig wxConfig, boolean isGrant);

	    /**
	     * * 微信WEB回调执行
	     * @param code 微信返回来的code值，可用来获取openId
	     * @param wxConfig 微信配置
	     * @param isGrant 是否授权
	     * @return
	     */
	    String getOpenId(String code, WxConfig wxConfig, boolean isGrant);

	    /**
	     * 获取第一次扫码记录信息
	     * @param securityCode 二维码
	     * @return
	     */
	    AntiFake getAntiFake(String securityCode,Integer vendorId);

	    /**
	     * 扫描内码信息
	     * @param antiFake 防伪扫码记录
	     * @param location 地理位置
	     * @param openId 微信用户标识
	     * @param result 结果
	     * @return
	     */
	    void scanInsideCode(AntiFake antiFake, Location location, String openId, ResultObject result);

	    /**
	     * 扫描外码信息
	     * @param codeBean 二维码信息
	     * @param result 结果
	     * @return
	     */
	    void scanOutsideCode(SecurityCode codeBean, ResultObject result);

	    /**
	     * 获取地理位置信息
	     * @param longitude 经度
	     * @param latitude 纬度
	     * @return
	     */
	    Location getLocation(String longitude, String latitude);

	    /**
	     * 获取微信扫码页面配置信息
	     * @param productId 产品ID
	     * @return
	     */
	    ScanPageConfig getScanPageImageByProductId(long productId);

	     /**
	     * 修改红包领取状态
	     * @param securityCode 二维码
	     * @param moreMoney 领取金额
	     * @return
	     */
	    int updateMoreMoney(String securityCode, double moreMoney);

	    /**
	     * 判断红包是否已被领取（解决并发问题）
	     * @param securityCode 二维码
	     * @param moreMoney 领取金额
	     * @param result 结果
	     * @return
	     */
	    void parallelRedEnvelope(String securityCode, double moreMoney, ResultObject result);

	    /**
	     * 判断红包是否被同一个人反复扫（解决并发问题）
	     * @param openId 微信用户标识
	     * @return
	     */
	    boolean parallelRedEnvelopeByOpenId(String openId);

	    int antiFakeCountToday(User sysUser);

	    Integer findScanCountByCalendar(List<ProductOrder> orderList);
	    
	    
	    /***
	     * 判断是否存已扫码用户
	     * @param openid
	     * @return
	     */
		int hasWeChatScanInfo(String openid,String securityCode);
		/**
		 * 获取厂商启用的扫码回调页面地址
		 * @param vendorId
		 * @return
		 */
		public String getVendorUrl(Integer orderId,Integer funcFlag);
}
