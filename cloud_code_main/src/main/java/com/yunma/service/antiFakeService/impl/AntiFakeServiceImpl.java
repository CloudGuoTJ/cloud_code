package com.yunma.service.antiFakeService.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.common.service.BaseService;
import com.common.util.Radix;
import com.common.util.ResultObject;
import com.common.util.WeixinUtil;
import com.github.sd4324530.fastweixin.api.response.OauthGetTokenResponse;
import com.yunma.cache.antiFake.AntiFakeCache;
import com.yunma.dao.securityCode.WxAntiFakeDao;
import com.yunma.dao.weChart.VendorHtmlActivInfoDao;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.AntiFake;
import com.yunma.model.Location;
import com.yunma.model.ScanPageConfig;
import com.yunma.model.User;
import com.yunma.model.WxConfig;
import com.yunma.service.antiFakeService.AntiFakeService;
import com.yunma.vo.antiFake.AntiFakeAnalyseVO;
import com.yunma.vo.antiFake.AntiFakeVO;

@Service
public class AntiFakeServiceImpl extends BaseService implements AntiFakeService {
	@Autowired
	private WxAntiFakeDao antiFakeDao;
	@Autowired
	private AntiFakeCache antiFakeCache;
	@Autowired
	private VendorHtmlActivInfoDao activInfoDao;
	@Override
	public String getVendorUrl(Integer orderId,Integer funcFlag){
		return activInfoDao.findUrlNameByOrderId(orderId,funcFlag);
		
	};
	
	
	public void createAntiFake(SecurityCode code, String openId,
			Location location, int productScanCount, ResultObject result) {
		String securityCode = code.getSecurityCode();
		AntiFake antiFake = new AntiFake();
		antiFake.setSecurityCode(securityCode);
		antiFake.setSecurityCodeId(code.getSecurityCodeId());
		antiFake.setProductId(code.getProductId());
		antiFake.setProductName(code.getProductName());
		antiFake.setScanTime(new Date());
		antiFake.setScanCount(1);
		antiFake.setScanAddress(location.getAddress());
		antiFake.setProvince(location.getProvince());
		antiFake.setCity(location.getCity());
		antiFake.setDistrict(location.getDistrict());
		antiFake.setLongitude(location.getLongitude());
		antiFake.setLatitude(location.getLatitude());
		antiFake.setOpenId(openId);
		antiFake.setProductScanCount(productScanCount);
		
		// 放入缓存
		antiFakeCache.set(antiFake);

		try {
			Integer orderId = Radix.convert62To10((code.getSecurityCode().substring(0, 4)).toString());
			int value =  antiFakeDao.insertAntiFake(orderId, antiFake);
			if (value <= 0) {
				result.set(-2, "异常错误：新增防伪扫码记录");
			}
		} catch (Exception e) {
			logger.error("新增防伪扫码记录时：", e);
		}

		result.set(1, "新增成功");

	}

/**
 * 将扫码用户信息存入anti_fake表中
 */
	@Override
	public int createOldAntiFake(Integer vendorId,AntiFake antiFake) {
		  
		        return antiFakeDao.createAntiFake(vendorId,antiFake);
		   
	}


	@Override
	public AntiFake findFirstFakeBySecurityCode(String perfixOrder,
			String scanResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AntiFake findOldFirstFakeBySecurityCode(String scanResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AntiFakeVO> findOldAntiFakeList(int scanCount,
			String productName, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AntiFakeVO> findAntiFakeList(int scanCount, String productName,
			User user, String perfixOrderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int antiFakeCount(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findScanCountByOrderList(List<ProductOrder> orderList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AntiFakeAnalyseVO> findAntiFakeListByAnlysis(String openId,
			User user, String perfixOrderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSecurityCodeByConfig(String shortCode, String url) {
		 //判断短码是否异常，完整二维码最少14位长度（减掉前6位=8）
        if (StringUtils.isBlank(shortCode) || shortCode.length() < 8) {
            logger.info("扫码包含的短码异常，异常码：" + shortCode);
            return null;
        }

        //读取shortUrl-product.properties配置文件
        InputStream urlInputStream = AntiFakeServiceImpl.class.getClassLoader().getResourceAsStream("shortUrl-product.properties");
        Properties properties = new Properties();
        try {
            properties.load(urlInputStream);
            urlInputStream.close();
        } catch (IOException e) {
            logger.error("读取shortUrl-product.properties文件时：", e);
            return null;
        }

        //获取域名对应二维码前缀
        String productStr = properties.getProperty(url);

        //判断是否需要短域名，为空则为非短域名，返回长码
        if (productStr == null || productStr.length() < 6) {
            return shortCode;
        }

        /**
         * 判断为短码则添加二维码前缀
         * 1、到这一步的都是需要短域名的厂商，而产品有需要短域名和不需要短域名的区别（前4位为厂商标识，前6位为产品标识）
         * 2、前4位不相等则需要短码（短码的前4未去掉了厂商标识）
         */
        if (!shortCode.substring(0, 4).equals(productStr.substring(0, 4))) {
            logger.info("从配置文件shortUrl-product.properties中获取二维码前缀");
            return productStr + shortCode;
        }

        return shortCode;
	}

	@Override
	public ModelAndView webCallBack(String securityCode, WxConfig wxConfig,
			boolean isGrant) {
		 if (StringUtils.isBlank(securityCode) || securityCode.length() < 12) {
	            return new ModelAndView("error");
	        }

	        String redirect_uri = wxConfig.getRedirect_uri();
	        String scope = "snsapi_base";

	        if (isGrant) {
	            //需要获取昵称--回执到getWxUserInfoByOAuth.json接口
	            redirect_uri = redirect_uri.replaceAll("getOpenId.json", "getWxUserInfoByOAuth.json");
	            scope = "snsapi_userinfo";
	        }

	        StringBuffer url = new StringBuffer("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
	        url.append(wxConfig.getAppid()).append("&redirect_uri=").append(redirect_uri);

	        if (redirect_uri.indexOf('=') >= 0) {
	            url.append(securityCode);
	        }
	        url.append("&response_type=code&scope=" + scope + "&state=csslink&connect_redirect=1#wechat_redirect");

	        logger.info("微信WEB回调地址：{}", redirect_uri);
	        return new ModelAndView(url.toString());
	}

	@Override
	public String getOpenId(String code, WxConfig wxConfig, boolean isGrant) {
		//return code;
		 logger.info("微信回调code值：{}", code);

	        /*if(isGrant){
	            String openId = oAuthInfoCache.findOpenIdByCode(code, wxConfig);

	            //刷新微信用户缓存
	            wxUserCache.flush(openId);

	            return openId;
	        }*/

		 	OauthGetTokenResponse oAuthInfo = WeixinUtil.getOAuthOpenId(wxConfig.getAppid(), wxConfig.getAppsecret(), code);
	        if (oAuthInfo == null) {
	            logger.info("通过微信授权获取OpenId失败");
	            return null;
	        }

	        logger.info("通过微信授权获取OpenId：{}", oAuthInfo.getOpenid());
	        return oAuthInfo.getOpenid();//静默授权
	}

	@Override
	public AntiFake getAntiFake(String securityCode,Integer vendorId) {
		return antiFakeDao.findAntiFakeBySecurityCode(securityCode,vendorId);
	}

	@Override
	public void scanInsideCode(AntiFake antiFake, Location location,
			String openId, ResultObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scanOutsideCode(SecurityCode codeBean, ResultObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public Location getLocation(String longitude, String latitude) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScanPageConfig getScanPageImageByProductId(long productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateMoreMoney(String securityCode, double moreMoney) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void parallelRedEnvelope(String securityCode, double moreMoney,
			ResultObject result) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean parallelRedEnvelopeByOpenId(String openId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int antiFakeCountToday(User sysUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer findScanCountByCalendar(List<ProductOrder> orderList) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int updateAntiFake(Integer OrderId, AntiFake antiFake) {
		
		return 0;
	}


	@Override
	public int hasWeChatScanInfo(String openId, String securityCode) {
		Integer orderId = Radix.convert62To10(securityCode.substring(0,4).toString());				
		return antiFakeDao.findAntiFake(orderId, openId);
	}

}
