package com.yunma.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunma.entity.register.RegisterChecking;
import com.yunma.model.User;
import com.yunma.utils.PageBean;
import com.yunma.vo.user.UserVO;
import com.yunma.vo.user.UserVendorVO;

public interface UserService {
	User findUserByUserName(String username);

   
    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return
     */
    User findUserByUserId(Integer userId);


//    /**
//     * 登录查询用户对象
//     * @param userId
//     * @param globalization
//     * @return
//     */
//    User getUserInfo(Integer userId,String globalization);
    public UserVendorVO getUserVOInfo(Integer userId);

    List<User> findUserList(int num, int page, User user);

    int findUserListCount(User user);

    int createUser(User user);


    int setPasswd(String passwd, int userId);

    int setFindPasswd(String passwd, int userId);

    JSONObject setLockUser(int userId);

    JSONObject cencalLockUser(int userId);

    User findUserByVendorId(int vendorId,int findUserByVendorId);
    /**
     * 分页显示用户信息
     * @param pageBean
     * @param vo
     * @return
     */
	PageBean getUserInfo(PageBean pageBean,UserVO vo);

    Integer updateUserMainHtmlConfig(User user);

/**
 * 通过vendorId查询User信息
 * @param vendorId
 * @return
 */
	User findUserByVendorId(Integer vendorId);

/**
 * 根据vendorName查询User
 * @param vendorName
 * @return
 * 
 */
User findUserByVendorName(String vendorName);

/**
 * 从tb_vendor_user_detail获取vendorId
 * @param vendorName
 * @return
 */
Integer getVendorIdFromVendorDetailByVendorName(String vendorName);

/**
 * 创建注册用户信息
 * @param registerChecking
 * @return
 */
@Transactional
int  addVendorTestRegister(RegisterChecking registerChecking);

/**
 * 审核通过
 * @param userId
 * @return
 */
JSONObject passCheckingUserInfo(Integer userId);

/**
 * 查询分页显示测试用户
 * @param pageBean
 * @param vo
 * @return
 */
PageBean getUncheckedUserInfo(PageBean pageBean, UserVO vo);

/**
 * 查询用户名是否重复
 * @param userName
 * @return
 */
JSONObject checkOutUserName(String username);

/***
 * 上传证书
 * @param vendorId
 * @param tradeMarkImgUrl
 * @param tradeMarkLicense
 * @param bankAccountOpeningLicense
 * @param foodProductionLicence
 * @param organizationCodeCertificate
 * @return
 */
JSONObject uploadRegistrationPic(int vendorId,
		MultipartFile tradeMarkImgUrl, MultipartFile tradeMarkLicense,
		MultipartFile bankAccountOpeningLicense,
		MultipartFile foodProductionLicence,
//		MultipartFile organizationCodeCertificate,
		MultipartFile industrialProductionLicense);

/**
 * 审核未通过
 * @param userId
 * @param checkComment
 * @return
 */
JSONObject unqualifiedPassingInfoSave(Integer userId, String checkComment);

/**
 * 通过vendorName查询待审核用户信息
 * @param vendorName
 * @return
 */
RegisterChecking findRegisterCheckingInfoByVendorName(String vendorName);

/**
 * 更新user表中对测试用户的数量限定
 * @param vendorId
 * @param count
 * @return
 */

int updateTestUserCreatingTracingCodeCount(Integer vendorId, int count);

/**
 * 单个显示用户信息
 * @param userId
 * @return
 */
JSONObject getSingleTestingUserInfo(Integer userId);


	

}
