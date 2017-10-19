package com.yunma.service.vendor;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.image.ImgAndVendor;
import com.yunma.entity.vendor.VendorAccount;
import com.yunma.entity.vendor.VendorAccountPayDetail;
import com.yunma.entity.vendor.VendorBrandImgs;
import com.yunma.entity.vendor.VendorInfo;
import com.yunma.model.User;
import com.yunma.utils.PageBean;
import com.yunma.vo.image.ImgVendorVo;
import com.yunma.vo.vendor.VendorAllinfoVo;
import com.yunma.vo.vendor.VendorBasicInfoVo;


public interface VendorService {
	
	/**
	 * 查询该厂商下的余额
	 * @param vendorId
	 * @return
	 */
	public double getBalanceByVendorId(Integer vendorId);
	
	/**
	 * 查询该厂商充值金额
	 * @param vendorId
	 * @return
	 */
	public double getPaymoneyByVendorId(Integer vendorId);
	
	/**
	 * 分页
	 * @param page
	 * @param vendor
	 * @return
	 */
	PageBean getVendor(PageBean page, VendorAllinfoVo vendor);
    
    /**
     * 获取商户信息
     * @param vendorId
     * @return
     */
    VendorInfo getVendorInfo(Integer detailId);
    
    /**
     * 修改商户信息
     * @param vendorId
     * @return
     */
    int updateVendorInfo(VendorInfo info);
    
    /**
     * 新增企业信息
     * @param info
     * @return
     */
    int addVendorInfo(VendorInfo info);
    
    /**
     * 上传图片
     * @param image
     * @return
     */
    public JSONObject uploadImage(MultipartFile image,JSONObject result);
    
    /**
	 * 添加图片信息
	 * @param img
	 * @return
	 */
	public int addImageInfo(VendorBrandImgs img);
	
	/**
	 * 查询所有厂商数
	 * @return
	 */
	int countVendor();
	
	/**
	 * 新增厂商信息和图片中间表
	 * @param img
	 * @return
	 */
	int addImgVendor(ImgAndVendor img);
	
	/**
	 * 查询厂商下的品牌代表图片
	 * @param vendor_detail_id
	 * @return
	 */
	List<ImgVendorVo> getImgVendor(Integer vendor_detail_id);

	/**
	 * 添加消费记录
	 * @param detail
	 * @return
	 */
	public int addPayRecord(VendorAccountPayDetail detail);

	/**
	 * 修改厂商钱包信息
	 * @param account
	 * @return
	 */
	public int updateAccount(VendorAccount account);
	
	/**
	 * 新增企业基本信息
	 * @param vo
	 * @return
	 */
	int addVendorBasicInfoVo(VendorBasicInfoVo vo);
	
	/**
	 * 修改企业基本信息
	 * @param vo
	 * @return
	 */
	
	int updateVendorBasicInfoVo(VendorBasicInfoVo vo);
	
	/**
	 * 修改厂商
	 * @param vendorAccountId
	 * @param money
	 * @return
	 */
	int  payMoney(int vendorAccountId,double money);
	
	
	Integer  getVendorAccountId(User  user);
	/**
	 * 添加企业资质
	 * @return
	 */
	int addVendorCertification();
	/**
	 * 更新企业资质
	 * @return
	 */
	int updateVendorCertification();
	
	/**
	 * 根据detail表的detailId查询vendorId,并插入到vendorUser表
	 * @param detailId
	 * @return
	 */
	int getVendorId();
}
