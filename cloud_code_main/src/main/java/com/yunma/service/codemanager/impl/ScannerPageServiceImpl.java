package com.yunma.service.codemanager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yunma.dao.codemanager.ScannerPageDao;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.codemanager.CodemanagerEntity;
import com.yunma.model.ModeFunctionFile;
import com.yunma.service.codemanager.ScannerPageService;
import com.yunma.utils.PageBean;
import com.yunma.vo.codemanager.CodeManagerVo;
import com.yunma.vo.product.ProductOrderVO;

@Service
public class ScannerPageServiceImpl implements ScannerPageService {
	
	@Resource
	private ScannerPageDao dao;
	@Resource
	private ProductOrderDao productOrderDao;

	@Override
	public int addCodeManager(CodemanagerEntity entity) {
		
		return dao.addCodeManager(entity);
	}

	@Override
	public int updateCodeManager(CodemanagerEntity entity) {
		return dao.updateCodeManager(entity);
	}

	@Override
	public int deleteCodeManager(Integer id) {
		return dao.deleteCodeManager(id);
	}

	@Override
	public PageBean getCodeManagerAll(PageBean page,String keyword, Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		int countScaPage=dao.countScannerPage(vendorId);
		page.setTotalRecords(countScaPage);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", page);
		map.put("keyword", keyword);
		map.put("vendorId", vendorId);
		List<CodeManagerVo> list = dao.getCodeManagerAll(map);
		
		for (CodeManagerVo vo:list) {
			JSONObject obj=new JSONObject();
			obj.put("id", vo.getId());
			obj.put("vendor_id", vo.getVendor_id());
			obj.put("anti_fake_name", vo.getAnti_fake_name());
			obj.put("start_time", vo.getStart_time());
			obj.put("end_time", vo.getEnd_time());
			obj.put("orderId", vo.getOrderId());
			obj.put("mark", vo.getMark());
			obj.put("activity_status", vo.getActivity_status());
			obj.put("mode_id", vo.getMode_id());
			obj.put("urlName", vo.getUrlName());
			obj.put("getRedEnv", vo.getGetRedEnv());
			obj.put("productInfo", vo.getProductInfo());
			obj.put("vendorHttp", vo.getVendorHttp());
			obj.put("weShop", vo.getWeShop());
			obj.put("spree", vo.getSpree());
			obj.put("securityAndTraceability", vo.getSecurityAndTraceability());
			
			array.add(obj);
		}
		
		result.put("data", array);
		page.setResult(result);
		return page;
	}

	@Override
	public CodeManagerVo getCodeManagerById(Integer id) {
		return dao.getCodeManagerById(id);
	}

	@Override
	public int updateScaPageStatus(Integer id) {
		return dao.updateScaPageStatus(id);
	}

	@Override
	public int saveModeFunFile(ModeFunctionFile file) {
		return dao.saveModeFunFile(file);
	}

	@Override
	public int updateModeStatus(Integer modeId) {
		// TODO Auto-generated method stub
		return dao.updateModeStatus(modeId);
	}

	@Override
	public int updateScaPageStatus1(Integer id) {
		// TODO Auto-generated method stub
		return dao.updateScaPageStatus1(id);
	}

	@Override
	public int updateModeStatus1(Integer modeId) {
		// TODO Auto-generated method stub
		return dao.updateModeStatus1(modeId);
	}

	@Override
	public PageBean getOrderInfoForActiv(PageBean pageBean, Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = productOrderDao.getOrderCountForActiv(vendorId);
		ProductOrderVO vo = new ProductOrderVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("ProductOrderVO", vo);
		map.put("vendorId", vendorId);
		List<ProductOrderVO> list = productOrderDao.getOrderInfoForActiv(map);
		if (list != null && list.size() > 0) {
			for (ProductOrderVO orderVO : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("orderId", orderVO.getOrderId());
				object.put("vendorId", orderVO.getVendorId());
				object.put("vendorName", orderVO.getVendorName());
				object.put("productId", orderVO.getProductId());
				object.put("productName", orderVO.getProductName());
				object.put("productCount", orderVO.getProductCount());
				object.put("createDate", orderVO.getCreateDate());
				object.put("status", orderVO.getStatus());
				object.put("deleteFlag", orderVO.getDeleteFlog());
				object.put("lastUpdateTime", orderVO.getLastUpdateTime());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}
	
	@Override
	public int updateOrderInfoForActiv(Integer orderId) {
		// TODO Auto-generated method stub
		return productOrderDao.updateOrderInfoForActiv(orderId);
	}

	@Override
	public int updateOrderInfoForTracingActiv(Integer orderId) {
		
		return productOrderDao.updateOrderInfoForTracingActiv(orderId);
	}

	@Override
	public PageBean getOrderInfoForTracyActiv(PageBean pageBean,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = productOrderDao.getOrderCountForTracyActiv(vendorId);
		ProductOrderVO vo = new ProductOrderVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("ProductOrderVO", vo);
		map.put("vendorId", vendorId);
		List<ProductOrderVO> list = productOrderDao.getOrderInfoForTracyActiv(map);
		if (list != null && list.size() > 0) {
			for (ProductOrderVO orderVO : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("orderId", orderVO.getOrderId());
				object.put("vendorId", orderVO.getVendorId());
				object.put("vendorName", orderVO.getVendorName());
				object.put("productId", orderVO.getProductId());
				object.put("productName", orderVO.getProductName());
				object.put("productCount", orderVO.getProductCount());
				object.put("createDate", orderVO.getCreateDate());
				object.put("status", orderVO.getStatus());
				object.put("deleteFlag", orderVO.getDeleteFlog());
				object.put("lastUpdateTime", orderVO.getLastUpdateTime());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public PageBean getCodeManagerAllForTracy(PageBean page, String keyword,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		int countScaPage=dao.countScannerPageForTracy(vendorId);
		page.setTotalRecords(countScaPage);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", page);
		map.put("keyword", keyword);
		map.put("vendorId", vendorId);
		List<CodeManagerVo> list = dao.getCodeManagerAllForTracy(map);
		
		for (CodeManagerVo vo:list) {
			JSONObject obj=new JSONObject();
			obj.put("id", vo.getId());
			obj.put("vendor_id", vo.getVendor_id());
			obj.put("anti_fake_name", vo.getAnti_fake_name());
			obj.put("start_time", vo.getStart_time());
			obj.put("end_time", vo.getEnd_time());
			obj.put("orderId", vo.getOrderId());
			obj.put("mark", vo.getMark());
			obj.put("activity_status", vo.getActivity_status());
			obj.put("mode_id", vo.getMode_id());
			obj.put("urlName", vo.getUrlName());
			obj.put("getRedEnv", vo.getGetRedEnv());
			obj.put("productInfo", vo.getProductInfo());
			obj.put("vendorHttp", vo.getVendorHttp());
			obj.put("weShop", vo.getWeShop());
			obj.put("spree", vo.getSpree());
			obj.put("securityAndTraceability", vo.getSecurityAndTraceability());
			
			array.add(obj);
		}
		
		result.put("data", array);
		page.setResult(result);
		return page;
	}

}
