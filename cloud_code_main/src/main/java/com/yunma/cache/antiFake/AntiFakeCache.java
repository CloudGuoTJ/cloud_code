package com.yunma.cache.antiFake;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.common.util.Radix;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.securityCode.WxAntiFakeDao;
import com.yunma.model.AntiFake;

@Service
public class AntiFakeCache extends BaseService{
	 @Autowired
	 private WxAntiFakeDao antiFakeDao;
	 
	 /**
	     * 设置防伪扫码信息缓存
	     * @param antiFake 扫码记录
	     * @return
	     */
	    public int set(AntiFake antiFake) {
	        if(antiFake == null || StringUtils.isBlank(antiFake.getSecurityCode())){
	            return 0;
	        }

	        try {
	           XMemcachedHelper.set("AntiFake_SecurityCode_" + antiFake.getSecurityCode(), antiFake, CommonConstants.TIME_MINUTE_30);
	        } catch (Exception e) {
	            logger.error("设置防伪扫码信息缓存时：", e);
	            return -2;
	        }

	        logger.info("获取AntiFake对象信息在Memcached中缓存");
	        return 1;
	    }

	    /**
	     * 根据二维码查询防伪扫码信息
	     * @param securityCode 二维码
	     * @return
	     */
	    public AntiFake findBySecurityCode(String securityCode) {
	        if(StringUtils.isBlank(securityCode) || securityCode.length() < 8){
	            logger.info("二维码异常");
	            return null;
	        }

	        AntiFake antiFake;
	        try {
	            antiFake = (AntiFake) XMemcachedHelper.findCache("AntiFake_SecurityCode_" + securityCode);
	            if(antiFake != null){
	                logger.info("从Memcached中获取到了AntiFake信息");
	                return antiFake;
	            }
	           Integer orderId = Radix.convert62To10( (securityCode.subSequence(0, 4)).toString());
	            antiFake = antiFakeDao.findBySecurityCode(  orderId, securityCode);
	            if(antiFake == null){
	                logger.info("数据库无防伪扫码记录");
	                return null;
	            }

	            XMemcachedHelper.set("AntiFake_SecurityCode_" + securityCode, antiFake, 30 * 60);
	        } catch (Exception e) {
	            logger.error("根据二维码查询防伪扫码时：", e);
	            return null;
	        }

	        logger.info("读取数据库AntiFake信息缓存在Memcached中");
	        return antiFake;
	    }

	    /**
	     * 清除防伪扫码缓存信息
	     * @param securityCode 二维码
	     * @return
	     */
	    public int clear(String securityCode) {
	        if(StringUtils.isBlank(securityCode) || securityCode.length() < 8){
	            logger.info("二维码异常");
	            return 0;
	        }

	        try {
	            XMemcachedHelper.deleteCache("AntiFake_SecurityCode_" + securityCode);
	        } catch (Exception e) {
	            logger.error("清除防伪扫码缓存信息时：", e);
	            return -2;
	        }

	        logger.info("清除在Memcached中缓存的AntiFake信息");
	        return 1;
	    }

	    public int clearAntiFakeLog(String securityCode){
	        if(StringUtils.isBlank(securityCode) || securityCode.length() < 8){
	            logger.info("二维码异常");
	            return -1;
	        }

	        AntiFake antiFake;
	        try {
	            antiFake = (AntiFake) XMemcachedHelper.findCache("AntiFake_SecurityCode_" + securityCode);
	            if(antiFake != null){
	                logger.info("从Memcached中获取到了AntiFake信息");
	                XMemcachedHelper.deleteCache("AntiFake_SecurityCode_" + securityCode);
	                return 1;
	            }
	        } catch (Exception e) {
	            logger.error("刷新防伪扫码信息时：", e);
	            return -2;
	        }

	        logger.info("清除Memcached中缓存的AntiFake信息");
	        return 0;
	    }

}
