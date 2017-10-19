package com.yunma.cache.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.product.ProductDao;
import com.yunma.service.product.ProductService;
import com.yunma.vo.product.GroupVo;

@Service
public class ProductCache extends BaseService{
	
	@Resource
	private ProductDao productDao;
	
	@Resource
	private ProductService productService;
	
	/*@Transactional
	public JSONObject getGroup(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = null;
		
		try {
			array = (JSONArray)XMemcachedHelper.findCache("ProductGroup_vendorId_"+vendorId);
			
			//判断缓存中是否存在该数据
			if (array != null && array.size() > 0) {
				
				logger.info("从Memcached中获取到了VendorBasicInfo信息");
				result.put("statuscode", "1");
				result.put("data", array);
				return result;
				
			} else {
				//将查询的数据添加到缓存中
				array = productService.getGroup(vendorId);
				
				if (array.size() > 0) {
					//将查询的数据添加到缓存中
					XMemcachedHelper.set("ProductGroup_vendorId_"+vendorId, array, CommonConstants.TIME_MINUTE_30);
					
				} else {
					logger.info("数据库未获取到企业基本信息");
					result.put("statuscode", "-1");
					result.put("msg", "未获取到对应id的信息");
					return result;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("读取数据库VendorBasicInfo信息缓存在Memcached中");
		result.put("statuscode", "1");
		result.put("data", array);
		return result;
	
		
	}
	
	@Transactional
	public JSONObject updateGroup(GroupVo vo,JSONObject result) {
		int resultVal = productService.updateGroup(vo);
		if (resultVal == 1) {
			XMemcachedHelper.deleteCache("ProductGroup_vendorId_"+vo.getVendorId());
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		} else if (resultVal == 0) {
			result.put("statuscode", -1);
			result.put("msg", "该id不存在无法修改");
			return result;
		} else {
			result.put("statuscode", -2);
			result.put("msg", "修改失败");
			return result;
		}
	}

	@Transactional
	public int deleteGroup(Integer id) {
		List<GroupVo> list = productDao.getGroupSecond(id);
		
		if (list != null && list.size() > 0) {
			for (GroupVo groupVo : list) {
				int temp = productDao.deleteGroupById(groupVo.getRowId());
				if (temp != 1) {
					return 0;
				}
			}
		}
		
		int temp = productDao.deleteGroupById(id);
		if (temp == 1) {
			XMemcachedHelper.deleteCache("ProductGroup_vendorId_"+list.get(0).getVendorId());
			return 1;
		} else {
			return 0;
		}
	}

	public int addGroup(GroupVo vo) {
		int temp = productDao.addGroup(vo);
		if (temp == 1) {
			XMemcachedHelper.deleteCache("ProductGroup_vendorId_"+vo.getVendorId());
			return 1;
		} else {
			return 0;
		}
	}*/
	

}
