package com.yunma.cache.seccurityCode;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.service.BaseService;
import com.common.util.Radix;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.securityCode.SecurityCodeDao;
import com.yunma.entity.securityCode.SecurityCode;

@Service
public class SecurityCodeCache extends BaseService{
	@Autowired
	private SecurityCodeDao codeDao ; 
	
	public SecurityCode findBySecurityCode(String securityCode) {
        if(StringUtils.isBlank(securityCode) || securityCode.length() < 8){
            logger.info("二维码匹配失败，谨防假冒！异常二维码：" + securityCode);
            return null;
        }

        SecurityCode codeBean;
        try {
            codeBean = (SecurityCode) XMemcachedHelper.findCache("SecurityCode_" + securityCode);
            if(codeBean != null){
                logger.info("从memcached中获取到了SecurityCode信息");
                return codeBean;
            }
            String orderCode = securityCode.substring(0, 4);
            Integer orderId = Radix.convert62To10(orderCode);
            codeBean = codeDao.findBySecurityCode(orderId, securityCode );
            if(codeBean == null){
                logger.info("数据库中该码不存在，异常二维码：" + securityCode);
                return null;
            }

            XMemcachedHelper.set("SecurityCode_" + securityCode, codeBean, 30 * 60);
        } catch (Exception e) {
            logger.error("查找二维码表中的二维码时异常错误，异常二维码：{}", securityCode);
            return null;
        }

        logger.info("读取数据库SecurityCode信息缓存在Memcached中");
        return codeBean;
    }

}
