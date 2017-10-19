package com.yunma.dao.vendor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yunma.entity.image.ImgAndVendor;
import com.yunma.entity.register.RegisterChecking;
import com.yunma.entity.vendor.VendorAccount;
import com.yunma.entity.vendor.VendorAccountPayDetail;
import com.yunma.entity.vendor.VendorBrandImgs;
import com.yunma.entity.vendor.VendorInfo;
import com.yunma.entity.vendor.VendorUser;
import com.yunma.entity.vendor.VendorUserDetail;
import com.yunma.vo.image.ImgVendorVo;
import com.yunma.vo.vendor.VendorAllinfoVo;
import com.yunma.vo.vendor.VendorBasicInfoVo;

@Repository("vendorDao")
public interface VendorDao {
	
	/**
	 * 待审核图片储存
	 * @param registerCheckingPri
	 * @return
	 */
	int uploadRegistrationPic(RegisterChecking registerCheckingPri);

	/**
	 * 根据企业id获取基本信息
	 * @param vendorId
	 * @return
	 */
//	@Select("SELECT u.userName userName,d.brand_name brand_name,d.vendor_logo imgUrl,d.industry_name industry_name," +
//			"d.contact_name contact_name,d.custom_phone custom_phone,d.vendor_weixin vendor_weixin," +
//			"d.custom_tel custom_tel,d.vendor_address vendor_address,d.link link " +
//			"FROM tb_user u LEFT JOIN tb_vendor_user_detail d ON u.vendorId=d.vendor_id WHERE d.vendor_id=#{vendorId} LIMIT 1")
	public VendorBasicInfoVo getBasicInfo(Integer vendorId);
	
	/**
	 * 修改vendorUser表的信息
	 * @param vendor
	 * @return
	 */
	public int updateVendorUserByVendorId(VendorUser vendor);
	
	/**
	 * 修改vendorUserDetail表的信息
	 * @param detail
	 * @return
	 */
	public int updateVendorUserDetailByVendorId(VendorUserDetail detail);

	/**
	 * 查询该厂商下的余额
	 * @param vendorId
	 * @return
	 */
	public double getBalanceByVendorId(Integer vendorId);
	
	/**
	 * 查询 该厂商的账户钱包充值金额
	 * @param vendorId
	 * @return
	 */
	public double getPaymoneyByVendorId(Integer vendorId);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	@Select("select detail_id detail_id, vendor_id vendorId,brand_name,industry_name industryName,contact_name contactName ,custom_phone customPhone,function_type functionType,vendor_emaill vendorEmaill," +
			"link link,vendor_mall vendor_mall,official_accounts officialAccounts,createTime createTime,comment comment from tb_vendor_user_detail " +
			"where delete_flag=0 ORDER BY createTime desc,vendorId DESC  LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<VendorAllinfoVo> getVendor(Map<String,Object> map);
	
	/**
	 * 分页查询企业信息总条数
	 * @param vendor
	 * @return
	 */	
	@Select("select count(*) from tb_vendor_user_detail where delete_flag=0")
	public Integer getVendorCount();
	
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
	 * 添加图片信息
	 * @param img
	 * @return
	 */
    int addImageInfo(VendorBrandImgs img);
    
    /**
     * 查询所有的厂商数
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
	
	@Insert("insert into tb_vendor_user_detail set vendor_id=#{vendorId},brand_name=#{brandName},vendor_logo=#{imgUrl},vendor_emaill=#{vendorEmaill},industry_name=#{industryName}, contact_name=#{contactName}, " +
            "custom_phone=#{customPhone}, vendor_weixin=#{vendorWeixin}, custom_tel=#{customTel} , vendor_address=#{vendorAddress}, link=#{link}, official_accounts=#{officialAccounts}")

	int addVendorBasicInfoVo(VendorBasicInfoVo vo);
	
	
	@Insert("INSERT INTO tb_vendor_user(vendor_id)SELECT vendor_id FROM tb_vendor_user_detail WHERE detail_id=#{detailId} AND delete_flag=0")
	int getVendorId();
	
	
	@Update("update tb_vendor_user_detail set vendor_name=#{vendorName},brand_name=#{brandName},vendor_logo=#{imgUrl}," +
			"industry_name=#{industryName},contact_name=#{contactName},custom_phone=#{customPhone},vendor_weixin=#{vendorWeixin},custom_tel=#{customTel}," +
			"vendor_address=#{vendorAddress},link=#{link},official_accounts=#{officialAccounts} where vendor_id=#{vendorId}")
	int updateVendorBasicInfoVo(VendorBasicInfoVo vo);
	
	
	@Update("update tb_envelope_vendor_account set currentMoney=currentMoney+#{1}  where id=#{0}")
    int payMoney(int vendorAccountId,double money);
	
	
	@Select("select id  from  tb_vendor_account where vendorId=#{1}")
    Integer  getVendorAccountId(int type,int vendorId);
	
	
	@Insert("insert into tb_vendor_user_detail set vendor_qualification=#{vendorQualification},trademark_certificate=#{trademark_certificate}")
	int addVendorCertification();
	
	
	@Update("update tb_vendor_user_detail set vendor_qualification=#{vendorQualification},trademark_certificate=#{trademark_certificate}")
	int updateVendorCertification();
	
}

