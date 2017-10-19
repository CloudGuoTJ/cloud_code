package com.yunma.service.secutrityCode;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.common.util.ResultObject;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.securityCode.WxAntiFakeScan;
import com.yunma.model.Location;
import com.yunma.model.ScanPageConfig;
import com.yunma.model.User;
import com.yunma.model.WxConfig;
import com.yunma.vo.securityCode.WxAntiFakeScanAnalyseVO;

/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public interface WxAntiFakeScanService {
    int createAntiFake(String perfixOrder, WxAntiFakeScan wxAntiFakeScan);

    /**
     * 新增防伪扫码记录
     * @param code 二维码
     * @param openId 微信用户标识
     * @param location 地理位置
     * @param productScanCount 产品扫码次数
     * @param result 结果
     */
    void createAntiFake(SecurityCode code, String openId, Location location, int productScanCount, ResultObject result);

    //插入到防伪表--新模式
    Integer createAntiFakeNewWay(String prefix, WxAntiFakeScan wxAntiFakeScan, String dbName);

    int createOldAntiFake(WxAntiFakeScan wxAntiFakeScan);

    int updateOldAntiFake(WxAntiFakeScan wxAntiFakeScan);

    int updateAntiFake(String perfixOrder, WxAntiFakeScan wxAntiFakeScan);

   WxAntiFakeScan findFirstFakeBySecurityCode(String perfixOrder, String scanResult);

    WxAntiFakeScan findOldFirstFakeBySecurityCode(String scanResult);

    List<WxAntiFakeScanAnalyseVO> findOldAntiFakeList(int scanCount, String productName, User user);

    List<WxAntiFakeScanAnalyseVO> findAntiFakeList(int scanCount, String productName, User user, String perfixOrderNo);

    int antiFakeCount(User sysUser);

    int findScanCountByOrderList(List<ProductOrder> orderList);

    //把该二维码的钱设置为已领取--新模式
    int setAntiFakeMoneyNewWay(String prefix, String securityCode, String dbName);

    List<WxAntiFakeScanAnalyseVO> findAntiFakeListByAnlysis(String openId, User user, String perfixOrderNo);

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
    WxAntiFakeScan getAntiFake(String securityCode);

    /**
     * 扫描内码信息
     * @param wxAntiFakeScan 防伪扫码记录
     * @param location 地理位置
     * @param openId 微信用户标识
     * @param result 结果
     * @return
     */
    void scanInsideCode(WxAntiFakeScan wxAntiFakeScan, Location location, String openId, ResultObject result);

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
     * @returns
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
}
