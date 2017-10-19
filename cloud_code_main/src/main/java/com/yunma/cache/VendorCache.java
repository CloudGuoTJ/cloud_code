package com.yunma.cache;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.vendor.VendorDao;
import com.yunma.entity.vendor.VendorUser;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.vo.vendor.VendorBasicInfoVo;

@Service

public class VendorCache extends BaseService{

	@Resource
	private VendorDao vendorDao;
	
	/**
	 * 从缓存中获取企业基本信息
	 * @param vendorId 企业id
	 * @return
	 */
	@Transactional
	public JSONObject getBasicInfo(Integer vendorId) {
		JSONObject result = new JSONObject();
		VendorBasicInfoVo vo;
		
		try {
			//vo = (VendorBasicInfoVo) XMemcachedHelper.findCache("VendorBasicInfo_VendorId_"+vendorId);
			
			//判断缓存中是否存在该数据
/*			if (vo != null) {

				logger.info("从Memcached中获取到了VendorBasicInfo信息");
				result.put("statuscode", "1");
				result.put("data", vo);
				return result;
				
			} else {*/
				//根据id查询企业基本信息
				vo = vendorDao.getBasicInfo(vendorId);
				
				if (vo != null) {
					//将查询的数据添加到缓存中
					//XMemcachedHelper.set("VendorBasicInfo_VendorId_"+vendorId, vo, CommonConstants.TIME_MINUTE_30);
					
				} else {
					logger.info("数据库未获取到企业基本信息");
					result.put("statuscode", "-1");
					result.put("msg", "未获取到对应id的信息");
					return result;
				}
				
			/*}*/
			
		} catch (Exception e) {
			logger.error("获取企业基本信息时：", e);
			result.put("statuscode", "服务器异常");
			return result;
		}
		
		//logger.info("读取数据库VendorBasicInfo信息缓存在Memcached中");
		result.put("statuscode", "1");
		result.put("data", vo);
		return result;
	}
	
	public void test() {
		//XMemcachedHelper.deleteCache("VendorBasicInfo_VendorId_3");
	}
	
	public static void main(String[] args) {
		XMemcachedHelper.deleteCache("VendorBasicInfo_VendorId_155");
	}
	

	@Transactional
	public JSONObject updateBasicInfo(VendorUserDetail detail,MultipartFile logoImage,JSONObject result) {
		
		//判断是否有图片
		if (logoImage != null && logoImage.getSize() > 0) {
			
			ImageUploladUtil util = new ImageUploladUtil();
			result = util.uploadBatchImage(logoImage,result);
			
			//判断图片是否上传失败
			if ((Integer)result.get("uploadState") < 0) {
				return result;
			} else {
				String img = result.get("imgUrl").toString();
				detail.setVendorLogo(img);
			}
			
		} 
		
		int temp = vendorDao.updateVendorUserDetailByVendorId(detail);
		if (temp == 1) {
			XMemcachedHelper.deleteCache("VendorBasicInfo_VendorId_"+detail.getVendorId());
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		} else if (temp ==0) {
			result.put("statuscode", -1);
			result.put("msg", "该id不存在无法修改");
			return result;
		} else {
			result.put("statuscode", -2);
			result.put("msg", "修改失败");
			return result;
		}
		
	}
	
}
