package com.yunma.service.vendor.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.yunma.dao.vendor.VendorDao;
import com.yunma.entity.image.ImgAndVendor;
import com.yunma.entity.vendor.VendorAccount;
import com.yunma.entity.vendor.VendorAccountPayDetail;
import com.yunma.entity.vendor.VendorBrandImgs;
import com.yunma.entity.vendor.VendorInfo;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.model.User;
import com.yunma.service.vendor.VendorService;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.utils.PageBean;
import com.yunma.vo.image.ImgVendorVo;
import com.yunma.vo.vendor.VendorAllinfoVo;
import com.yunma.vo.vendor.VendorBasicInfoVo;

@Service
public class VendorServiceImpl extends BaseService implements VendorService {

	@Resource
	private VendorDao vendorDao;

	public double getBalanceByVendorId(Integer vendorId) {
		return vendorDao.getBalanceByVendorId(vendorId);
	}

	public double getPaymoneyByVendorId(Integer vendorId) {
		return vendorDao.getPaymoneyByVendorId(vendorId);
	}

	@Override
	public PageBean getVendor(PageBean pageBean, VendorAllinfoVo vo) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		// 设置总记录数
		pageBean.setTotalRecords(vendorDao.getVendorCount());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("vendor", vo);
		//Date createTime =vendorDao.getCreateTime();
		List<VendorAllinfoVo> list = vendorDao.getVendor(map);

		for (VendorAllinfoVo vao : list) {

			JSONObject object = new JSONObject();
			 object.put("detail_id", vao.getDetail_id());
			 object.put("vendorId", vao.getVendorId());
			 object.put("brand_name", vao.getBrandName());
			 object.put("industryName", vao.getIndustryName());
			 object.put("customPhone", vao.getCustomPhone());
			 object.put("functionType", vao.getFunctionType());
			 object.put("vendorEmail", vao.getVendorEmaill());
			 object.put("createTime", sdf.format(vao.getCreateTime()));
			 object.put("link", vao.getLink());
			 object.put("vendor_mall", vao.getVendor_mall());
//			 object.put("createTime", vao.getCreateTime());
			 object.put("officialAccounts", vao.getOfficialAccounts());
			 object.put("comment", vao.getComment());
		
			 array.add(object);
		}
		result.put("data", array);
		pageBean.setResult(result);
		return pageBean;
	}

	@Override
	public VendorInfo getVendorInfo(Integer detailId) {

		return vendorDao.getVendorInfo(detailId);
	}

	@Override
	public int updateVendorInfo(VendorInfo info) {

		return vendorDao.updateVendorInfo(info);
	}

	@Override
	public int addVendorInfo(VendorInfo info) {
		return vendorDao.addVendorInfo(info);
	}

	@Override
	public JSONObject uploadImage(MultipartFile image, JSONObject result) {
		// 判断是否有图片
		if (image != null && image.getSize() > 0) {

			ImageUploladUtil util = new ImageUploladUtil();
			return result = util.uploadImage(image, result);

		} else {
			return null;
		}
	}

	@Override
	public int addImageInfo(VendorBrandImgs img) {
		
		return vendorDao.addImageInfo(img);
	}

	@Override
	public int countVendor() {
		return vendorDao.countVendor();
	}

	@Override
	public int addImgVendor(ImgAndVendor img) {
		return vendorDao.addImgVendor(img);
	}

	@Override
	public List<ImgVendorVo> getImgVendor(Integer vendor_detail_id) {
		
		return vendorDao.getImgVendor(vendor_detail_id);
	}

	@Override
	public int addPayRecord(VendorAccountPayDetail detail) {
		return vendorDao.addPayRecord(detail);
	}
	
	@Override
	public int updateAccount(VendorAccount account) {
		return vendorDao.updateAccount(account);
	}

	@Override
	public int addVendorBasicInfoVo(VendorBasicInfoVo vo) {
		
		return vendorDao.addVendorBasicInfoVo(vo);
	}

	@Override
	public int updateVendorBasicInfoVo(VendorBasicInfoVo vo) {
		
		return vendorDao.updateVendorBasicInfoVo(vo);
	}

	@Override
	public int payMoney(int vendorAccountId, double money) {
		
		return vendorDao.payMoney(vendorAccountId, money);
	}

	@Override
	public Integer getVendorAccountId(User user) {
		int userType = user.getUserType();
        if(userType == CommonConstants.User_Type_SuperAdmin.getState()
                || userType == CommonConstants.User_Type_SecondaryAdmin.getState()){
            return vendorDao.getVendorAccountId(2,user.getVendorId());
        }else if(userType == CommonConstants.User_Type_ProductVendor.getState()
                ||userType == CommonConstants.User_Type_FindProductVendor.getState()){
            return vendorDao.getVendorAccountId(2,user.getVendorId());
        }else if(userType == CommonConstants.User_Type_LogisticsVendor.getState()){
            return vendorDao.getVendorAccountId(1,user.getVendorId());

        }
		return 0;
	}

	@Override
	public int addVendorCertification() {
		
		return vendorDao.addVendorCertification();
	}

	@Override
	public int updateVendorCertification() {
		
		return vendorDao.updateVendorCertification();
	}

	@Override
	public int getVendorId() {
		
		return vendorDao.getVendorId();
	}
}


