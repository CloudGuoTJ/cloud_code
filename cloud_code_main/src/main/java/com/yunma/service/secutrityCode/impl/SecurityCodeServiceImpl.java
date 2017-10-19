package com.yunma.service.secutrityCode.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.Radix;
import com.common.util.TxtExportUtils;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.securityCode.SecurityCodeDao;
import com.yunma.dao.user.UserDao;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.service.user.UserService;
import com.yunma.utils.PageBean;
import com.yunma.utils.WeChatConfig;
@Service
public class SecurityCodeServiceImpl extends BaseService implements SecurityCodeService
{
	@Autowired
	private SecurityCodeDao codeDao;
	@Autowired
	private ProductOrderDao orderDao;
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public PageBean getSecurityInfo(PageBean pageBean,SecurityCode code,Integer orderId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = codeDao.getSecurityCount(orderId);
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("SecurityCode", code);
		map.put("orderId", orderId);
		try{
		List<SecurityCode> list = codeDao.getSecurityCodeInfo(map);
		if (list != null && list.size() > 0) {
		for (SecurityCode securityCode : list) {
			/*
			 * SELECT securityCodeId,orderId,productId,productName," +
			 * "codeFlag,codePrefix,securityCode,codeTailTag,createRowNum,createDate"
			 */
			JSONObject object = new JSONObject();
			object.put("securityCodeId", securityCode.getSecurityCodeId());
			object.put("orderId", securityCode.getOrderId());
			object.put("productName", securityCode.getProductName());
			object.put("productId", securityCode.getProductId());
			object.put("codeFlag", securityCode.getCodeFlag());
			object.put("codePrefix", securityCode.getCodePrefix());
			object.put("securityCode", securityCode.getSecurityCode());
			object.put("codeTailTag", securityCode.getCodeTailTag());
			object.put("createRowNum", securityCode.getCreateRowNum());
			object.put("createDate", securityCode.getCreateDate());			
			array.add(object);
		}
	 }
		else{
			result.put("error:", "没有订单相关的二维码,请确定是否生成二维码!");
		}
		}catch(Exception e){
			logger.error("没有订单相关的二维码,请确定是否生成二维码!");
			
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}


	@Override
	public List<SecurityCode> getSecurityCodeByOrderId(Integer orderId) {
		return codeDao.getSecurityCodeByOrderId(orderId);
	}


	@Override
	public SecurityCode getSecurityCode(String securityCode) {
				SecurityCode codeBean;	      
	            String orderCode = securityCode.substring(0, 4);
	            Integer orderId = Radix.convert62To10(orderCode);
	            codeBean = codeDao.findBySecurityCode(orderId, securityCode);
	            if(codeBean == null){
	                logger.info("数据库中该码不存在，异常二维码：" + securityCode);
	                return null;
	            }else{

//	            XMemcachedHelper.set("SecurityCode_" + securityCode, codeBean, 30 * 60);
	       

//	        logger.info("读取数据库SecurityCode信息缓存在Memcached中");
	        return codeBean;
	    }
	}


	@Override
	public void exportBatchCodeTxt(HttpServletResponse response, Integer orderId) {
		try {
            
            //1. 获取二维码list
            List<String> codeList = codeDao.getCodeList(orderId);
            ProductOrder order = new ProductOrder();
            order = orderDao.findOrderByProductOrderId(orderId);
            /**
             * 异常,无法获取vendorId
             * 处理:在创建订单时添加vendorName
             */
            Integer vendorId = order.getVendorId();
            String vendorName = userDao.getVendorNameByVendorId(vendorId);
            String productName = order.getProductName();
            int downCountExtra = codeDao.findDownCountByOrderId(orderId);
            int downCount = 0;
           /**
            * 须在其他接口追加下载次数提醒
            * 即超过1次则告知异常,会出现扫码不正常现象
            */
            downCount = downCountExtra+1;
            	String url = "https://"+WeChatConfig.DOMAIN_NAME+"/s/";
            	String urlOut = url.trim();
            
            //2. 调用导出txt工具，导出txt   
            String plus = "该批二维码为："
                  
                    + vendorName+"厂商下:" + productName +"产品 "+"订单号为:"+ orderId;
            TxtExportUtils.exportTxt(response, codeList, orderId + "",  urlOut, plus);            
            codeDao.updateDownloadCount(orderId, downCount);

        } catch (Exception e) {
            logger.error("导出二维码异常 ：", e);
        }
		
		
	}


	@Override
	public int getDownloadCount(Integer orderId) {
		
		return codeDao.findDownCountByOrderId(orderId);
	}


	@Override
	public int getScanCountpri(Integer orderId,String securityCode) {
		return codeDao.findProductScanCountByCode(orderId,securityCode);
	}


	@Override
	public int updateCodeScanCount(Integer orderId, Integer codeScanCount,String securityCode) {

		return codeDao.updateScanCount(orderId,codeScanCount,securityCode);
	}


	@Override
	public String getNickNameByOpenId(String openId) {
		
		return codeDao.findNickNameByOpenId(openId);
	}


	@Override
	public String getPicUrlByOpenId(String openId) {
		return codeDao.findPicUrlByOpenId(openId);
	}


	@Override
	public Integer findCodeScanCount(String securityCode, Integer orderId) {

		return codeDao.findCodeScanCount(securityCode, orderId);
	}
		



	

}
