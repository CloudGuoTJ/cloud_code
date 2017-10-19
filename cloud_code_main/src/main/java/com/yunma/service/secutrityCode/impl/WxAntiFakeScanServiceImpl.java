package com.yunma.service.secutrityCode.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import org.apache.commons.lang.StringUtils;

import com.common.util.ResultObject;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.securityCode.WxAntiFakeScan;
import com.yunma.model.Location;
import com.yunma.model.ScanPageConfig;
import com.yunma.model.User;
import com.yunma.model.WxConfig;
import com.yunma.service.secutrityCode.WxAntiFakeScanService;
import com.yunma.vo.securityCode.WxAntiFakeScanAnalyseVO;


/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 * 
 */
@Service
public class WxAntiFakeScanServiceImpl implements WxAntiFakeScanService {

    public int createAntiFake(String perfixOrder, WxAntiFakeScan wxAntiFakeScan) {
        return 0;
    }

    public void createAntiFake(SecurityCode code, String openId, Location location, int productScanCount, ResultObject result) {

    }

    public Integer createAntiFakeNewWay(String prefix, WxAntiFakeScan wxAntiFakeScan, String dbName) {
        return null;
    }

    public int createOldAntiFake(WxAntiFakeScan wxAntiFakeScan) {
        return 0;
    }

    public int updateOldAntiFake(WxAntiFakeScan wxAntiFakeScan) {
        return 0;
    }

    public int updateAntiFake(String perfixOrder, WxAntiFakeScan wxAntiFakeScan) {
        return 0;
    }

    public WxAntiFakeScan findFirstFakeBySecurityCode(String perfixOrder, String scanResult) {
        return null;
    }

    public WxAntiFakeScan findOldFirstFakeBySecurityCode(String scanResult) {
        return null;
    }

    public List<WxAntiFakeScanAnalyseVO> findOldAntiFakeList(int scanCount, String productName, User user) {
        return null;
    }

    public List<WxAntiFakeScanAnalyseVO> findAntiFakeList(int scanCount, String productName, User user, String perfixOrderNo) {
        return null;
    }

    public int antiFakeCount(User sysUser) {
        return 0;
    }

    public int findScanCountByOrderList(List<ProductOrder> orderList) {
        return 0;
    }

    public int setAntiFakeMoneyNewWay(String prefix, String securityCode, String dbName) {
        return 0;
    }

    public List<WxAntiFakeScanAnalyseVO> findAntiFakeListByAnlysis(String openId, User user, String perfixOrderNo) {
        return null;
    }

    public String getSecurityCodeByConfig(String shortCode, String shortUrl) {
        return null;
    }

    public ModelAndView webCallBack(String securityCode, WxConfig wxConfig, boolean isGrant) {
        return null;
    }

    public String getOpenId(String code, WxConfig wxConfig, boolean isGrant) {
        return null;
    }

    public WxAntiFakeScan getAntiFake(String securityCode) {
        return null;
    }

    public void scanInsideCode(WxAntiFakeScan wxAntiFakeScan, Location location, String openId, ResultObject result) {

    }

    public void scanOutsideCode(SecurityCode codeBean, ResultObject result) {

    }

    public Location getLocation(String longitude, String latitude) {//获取扫码位置 经度/纬度
        Location location = new Location();
        if(StringUtils.isBlank(longitude)|| StringUtils.isBlank(latitude))
        {
            return  location;
        }
        /**
         * 首先考虑从缓存队列中获取地理位置
         */
//        location = LocationCache;
        return null;
    }

    public ScanPageConfig getScanPageImageByProductId(long productId) {
        return null;
    }

    public int updateMoreMoney(String securityCode, double moreMoney) {
        return 0;
    }

    public void parallelRedEnvelope(String securityCode, double moreMoney, ResultObject result) {

    }

    public boolean parallelRedEnvelopeByOpenId(String openId) {
        return false;
    }

    public int antiFakeCountToday(User user) {
        WxAntiFakeScan wxAntiFakeScan = new WxAntiFakeScan();
        int  id = user.getUserId();
        int count =  wxAntiFakeScan.getScanCount();


        return 0;
    }

    public Integer findScanCountByCalendar(List<ProductOrder> orderList) {
        return null;
    }
}
