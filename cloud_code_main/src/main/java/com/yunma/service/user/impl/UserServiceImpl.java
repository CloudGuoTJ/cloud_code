package com.yunma.service.user.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sun.java2d.pipe.BufferedBufImgOps;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.service.BaseService;
import com.common.util.CommonConstants;
import com.common.util.Radix;
import com.common.util.XMemcachedHelper;
import com.yunma.dao.user.UserDao;
import com.yunma.dao.vendor.VendorDao;
import com.yunma.entity.register.RegisterChecking;
import com.yunma.model.User;
import com.yunma.service.user.UserService;
import com.yunma.utils.PageBean;
import com.yunma.vo.user.UserVO;
import com.yunma.vo.user.UserVendorVO;

@Service
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private VendorDao vendordao;

	/* 通过用户名查找用户信息 */
	public User findUserByUserName(String username) {
		return userDao.findUserByUserName(username);
	}

	/**
	 * 查询用户信息	 
	 * @param userId:用户ID
	 * @return
	 */
	public User findUserByUserId(Integer userId) {
		if (userId == null) {
			return null;
		}
		User user = null;
		try {
			user = (User) XMemcachedHelper.findCache(userId + "");
			if (user == null) {
				user = userDao.findUserByUserId(userId);
				XMemcachedHelper
						.set(user.getUserId().toString(), user, 30 * 60);
			}
		} catch (Exception e) {
			logger.error("根据userId查询用户时：" + e.getMessage(), e);
		}
		return user;
	}

	/**
	 * 商家用户登录查询用户信息
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public UserVendorVO getUserVOInfo(Integer userId) {
		if (userId == null) {
			return null;
		}
		UserVendorVO userVendor = null;
		/* 用户信息首先从缓存中取,如果缓存没数据则通过userDao取出数据 */
		try {
			
			userVendor = (UserVendorVO) XMemcachedHelper.findCache(userId + "");
			if (userVendor == null) {
				userVendor = userDao.findUserVOByUserId(userId);
				userVendor = userDao.findUserVOByUserId(userId);
				/* 将用户信息存入缓存 */
				logger.info("将user信息存入缓存");
				XMemcachedHelper.addCache(userVendor.getUserId().toString(), userVendor,
						30 * 60);
			}
		} catch (Exception e) {
			logger.error("根据userId查询用户时：" + e.getMessage(), e);
		}
		logger.info("成功获取user信息成功!");
		return userVendor;
	}

	public List<User> findUserList(int num, int page, User user) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return userDao.findUserList((page - 1) * num, num);
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()) {
			return userDao.findUserByVendorList((page - 1) * num, num,
					user.getVendorId());
		} else if (userType > 1) {
			List<User> userList = new ArrayList<User>();
			userList.add(user);
			return userList;
		}
		return null;
	}

	public int findUserListCount(User user) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return userDao.findUserListCount();
		} else if (userType == CommonConstants.User_Type_ProductVendor
				.getState()) {
			return userDao.findUserByVendorListCount(user.getVendorId());
		} else if (userType > 1) {
			return 1;
		}
		return 0;
	}

	public int createUser(User user) {
		return userDao.createUser(user);
	}

	public int setPasswd(String passwd, int userId) {
		int result1 = userDao.setUserPasswd(passwd, userId);		
		return result1; // + result2;
	}

	public int setFindPasswd(String passwd, int userId) {
		return userDao.setFindPasswd(passwd, userId);
	}

	public JSONObject setLockUser(int userId) {
		User user = userDao.findUserByUserId(userId);
		int status = user.getStatus();
		JSONObject result =new JSONObject();
		if(status == 0){
			result.put("errorCode", -1);
			result.put("msg", "用户已锁定,无需再锁定!");
			return result;		
		}if(status == 1){
			userDao.setLockUser(userId);
			result.put("errorCode", 0);
			result.put("msg", "已锁定该用户!");
			}else{
				result.put("errorCode", -13);
				result.put("msg", "用户账户异常!");
			}
		return result;
	}

	
	public JSONObject cencalLockUser(int userId) {
		User user = userDao.findUserByUserId(userId);
		int status = user.getStatus();
		JSONObject result =new JSONObject();
		if(status == 1){
			result.put("errorCode", -1);
			result.put("msg", "用户已解锁定,无需再解锁!");
			return result;		
		}if(status == 0){
			userDao.cencalLockUser(userId);
			result.put("errorCode", 0);
			result.put("msg", "已解锁该用户!");
			}else{
				result.put("errorCode", -13);
				result.put("msg", "用户账户异常!");
			}
		return result;
	}

	public User findUserByVendorId(int vendorId, int islogisticsVendorAccount) {
		int userType = 0;
		if (islogisticsVendorAccount == 1) {
			userType = 2;
		} else if (islogisticsVendorAccount == 2) {
			userType = 1;
		}
		return userDao.findTypeUserByVendorId(vendorId, userType);
	}

	@Override
	public Integer updateUserMainHtmlConfig(User user) {

		return userDao.UpdateMainHtmlConfig(user);
	}

	@Override
	/**
	 * 使用分页类实现分页
	 */
	@Transactional
	public PageBean getUserInfo(PageBean pageBean,UserVO vo) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		pageBean.setTotalRecords(userDao.getUserCount());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("UserVO", vo);
		List<UserVO> list = userDao.getUserInfo(map);
		if (list != null && list.size() > 0) {
		for (UserVO userVO : list) {
			JSONObject object = new JSONObject();
			object.put("userName", userVO.getUserName());
			object.put("vendorId", userVO.getVendorId());
			object.put("userType", userVO.getUserType());
			object.put("vendorName", userVO.getVendorName());
			object.put("deleteFlag", userVO.getDeleteFlag());
			object.put("createTime", userVO.getCreateTime());
			object.put("userId", userVO.getUserId());
			object.put("checkStatus", userVO.getCheckStatus());
			array.add(object);
		}
	 }
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public User findUserByVendorId(Integer vendorId) {
		return userDao.findUserByVendorId(vendorId);
	}

	@Override
	public User findUserByVendorName(String vendorName) {
		
		return userDao.findUSerByVendorName(vendorName);
	}

	@Override
	public Integer getVendorIdFromVendorDetailByVendorName(String vendorName) {
		
		return userDao.getVendorIdFromVendorDetailByVendorName(vendorName);
	}

	@Override
	public int addVendorTestRegister(RegisterChecking registerChecking) {
		
		return userDao.addRegisterInfo(registerChecking);
	}

	@Override
	public JSONObject passCheckingUserInfo(Integer userId) {
		JSONObject result = new JSONObject();
		User user = userDao.findUserByUserId(userId);
		Integer userType = user.getUserType();
		if(userType == 2){
			int a  = userDao.updateUserType(userId);
			String vendorName = user.getVendorName();
			int b = userDao.updateUserTestUserCheckStatus(vendorName);
			if(a < 0 || b < 0){
				result.put("errorCode", -1);
				result.put("msg", "审核通过失败,请联系数据管理员!");
			}
			
			result.put("errorCode", 0);
			result.put("msg", "审核通过," + user.getVendorName() + " 该用户已成为正式用户!");
		}
		return result;
	}

	@Override
	public PageBean getUncheckedUserInfo(PageBean pageBean, UserVO vo) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		pageBean.setTotalRecords(userDao.getTestUserCount());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("UserVO", vo);
		List<UserVO> list = userDao.getTestUserAllInfo(map);
		if (list != null && list.size() > 0) {
			for (UserVO userVO : list) {
				
				JSONObject object = new JSONObject();
				
				object.put("userName", userVO.getUserName());
				object.put("vendorId", userVO.getVendorId());
				object.put("userType", userVO.getUserType());
				object.put("vendorName", userVO.getVendorName());
//				object.put("deleteFlag", userVO.getDeleteFlag());
				object.put("createTime", userVO.getCreateTime());
				object.put("userId", userVO.getUserId());
				object.put("checkStatus", userVO.getCheckStatus());
				object.put("vendorEmaill", userVO.getVendorEmaill());
				object.put("customPhone", userVO.getCustomPhone());
				object.put("tradeMarkImgUrl", userVO.getTradeMarkImgUrl());
				object.put("tradeMarkLicense", userVO.getTradeMarkLicense());
				object.put("bankAccountOpeningLicense", userVO.getBankAccountOpeningLicense());
				object.put("foodProductionLicence", userVO.getFoodProductionLicence());
//				object.put("organizationCodeCertificate", userVO.getOrganizationCodeCertificate());
				object.put("industrialProductionLicense", userVO.getIndustrialProductionLicense());								
				array.add(object);
				
			}
		}
		result.put("data", array);
		pageBean.setResult(result);
		return pageBean;
	}

	@Override
	public JSONObject checkOutUserName(String username) {
		JSONObject result = new JSONObject();
		User user = userDao.findUserByUserName(username);
		if(user == null){
			result.put("errorCode", 0);
			result.put("msg", "用户名可以使用!");
		}else{
			result.put("errorCode", -1);
			result.put("msg", "用户名已存在!");
		}
		
		return result;
	}

	@Override
	public JSONObject uploadRegistrationPic(int vendorId,
			MultipartFile tradeMarkImgUrl, MultipartFile tradeMarkLicense,
			MultipartFile bankAccountOpeningLicense,
			MultipartFile foodProductionLicence,
//			MultipartFile organizationCodeCertificate,
			MultipartFile industrialProductionLicense) {
		JSONObject result = new JSONObject();
		
		User user = userDao.findTestUserByVendorId(vendorId);
		String vendorName = user.getVendorName();
		RegisterChecking registerChecking =userDao.findRegisterCheckingByVendorName(vendorName);//试用用户审核表
		Integer checkId = registerChecking.getCheckId();
		String tradePath;
		String realPath;
		String path = null;
		String namePrefix = null;
		String content = null;
//		User user = userDao.findUserByVendorId(vendorId);
		RegisterChecking registerCheckingPri = new RegisterChecking();
		registerCheckingPri.setCheckId(checkId);
		try {
			tradePath = getClass().getResource("/").toURI().getPath();
			 path = tradePath.substring(0,
					tradePath.lastIndexOf("webapps") + 7) ;
			 namePrefix = vendorId +"_"+ Radix.convert10To62(vendorId, 2);
			 content = "/img/" + namePrefix;//文件夹位置
			 
			 realPath = path + content;
			 //创建厂商对应的文件夹
			 File pathFile = new File(realPath);
				logger.debug("生成文件路径为: {}" + realPath);
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				} 
			 
			 
		} catch (URISyntaxException e1) {
			
			e1.printStackTrace();
		}
		//营业执照
		if(tradeMarkImgUrl != null && tradeMarkImgUrl.getSize() > 0 ){		
			try {

				String realPathTra;

				byte[]  tradeMarkImgUrlFile = tradeMarkImgUrl.getBytes();
				try {					
					//创建对应文件夹
					realPathTra = path  + content;
					//保存文件	
					String Tname =content + "/" +"T"+ namePrefix
							+ (UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "T.jpg";
					FileOutputStream fosTra = new FileOutputStream(path + Tname);
					fosTra.write(tradeMarkImgUrlFile);
					fosTra.close();
					
					registerCheckingPri.setTradeMarkImgUrl(Tname);
				} catch (Exception e) {					
					e.printStackTrace();
				}								
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
		}
		//企业商标注册证书
		if(tradeMarkLicense != null && tradeMarkLicense.getSize() > 0){

			try {
				
				byte[] tradeMarkLicenseFile = tradeMarkLicense.getBytes();
				try {				
					//创建对应文件夹
					realPath = path +  content;
					//保存文件	
					
					String Lname =content + "/" +"L"+ namePrefix
							+ (UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "L.jpg";
					FileOutputStream fos = new FileOutputStream(path + Lname);
					fos.write(tradeMarkLicenseFile);
					fos.close();

					registerCheckingPri.setTradeMarkLicense(Lname);					
				} catch (Exception e) {					
					e.printStackTrace();
				}								
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		//银行开户许可
		if(bankAccountOpeningLicense != null && bankAccountOpeningLicense.getSize() > 0){

			try {
				
				byte[] bankAccountOpeningLicenseFile = bankAccountOpeningLicense.getBytes();
				try {				
					//创建对应文件夹
					realPath = path +  content;
					//保存文件	
					String Bname =content + "/"+ "B" + namePrefix
							+(UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "B.jpg";
					FileOutputStream fos = new FileOutputStream(path + Bname );
					fos.write(bankAccountOpeningLicenseFile);
					fos.close();
					registerCheckingPri.setBankAccountOpeningLicense(Bname);				
				} catch (Exception e) {					
					e.printStackTrace();
				}								
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		//食品许可证
		if(foodProductionLicence != null && foodProductionLicence.getSize() > 0){

			try {
				
				byte[] foodProductionLicenceFile = foodProductionLicence.getBytes();
				try {				
					//创建对应文件夹
					realPath = path +  content;
					//保存文件	
					String Fname =content + "/" + "F"+ namePrefix
							+ (UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "F.jpg";
					FileOutputStream fos = new FileOutputStream(path + Fname);
					fos.write(foodProductionLicenceFile);
					fos.close();				
					registerCheckingPri.setFoodProductionLicence(Fname);				
				} catch (Exception e) {					
					e.printStackTrace();
				}								
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
//		//组织机构代码
//		if(organizationCodeCertificate != null && organizationCodeCertificate.getSize() > 0){
//
//			try {
//				
//				byte[] organizationCodeCertificateFile = organizationCodeCertificate.getBytes();
//				try {				
//					//创建对应文件夹
//					realPath = path +  content;
//					//保存文件	
//					String Oname =content + "/" + "O" + namePrefix
//							+ (UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "O.jpg";
//					FileOutputStream fos = new FileOutputStream(path + Oname);
//					fos.write(organizationCodeCertificateFile);
//					fos.close();
//					registerCheckingPri.setOrganizationCodeCertificate(Oname);					
//				} catch (Exception e) {					
//					e.printStackTrace();
//				}								
//			} catch (IOException e) {
//				
//				e.printStackTrace();
//			}
//			
//		}
		//工业产品生成许可证
		if(industrialProductionLicense != null && industrialProductionLicense.getSize() > 0){

			try {
				
				byte[] industrialProductionLicenseFile = industrialProductionLicense.getBytes();
				try {				
					//创建对应文件夹
					realPath = path +  content;
					//保存文件	
					String Iname =content + "/" + "I" + namePrefix
							+ (UUID.randomUUID().toString().replace("\\-", "")).substring(1, 9) + "I.jpg";
					FileOutputStream fos = new FileOutputStream(path + Iname);
					fos.write(industrialProductionLicenseFile);
					fos.close();
					registerCheckingPri.setIndustrialProductionLicense(Iname);					
				} catch (Exception e) {					
					e.printStackTrace();
				}								
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		
//		sb.append(" WHERE checkId =").append(checkId);
		
//		String sql = sb.toString();
//		logger.info("printSql:{}"+sql);
		vendordao.uploadRegistrationPic(registerCheckingPri);				
		result.put("errorCode", 0);
		result.put("msg", "添加成功!");
		
		return result;
						
	}

	@Override
	public JSONObject unqualifiedPassingInfoSave(Integer userId,
			String checkComment) {
		JSONObject result = new JSONObject();
		//异常判断1
		User user = userDao.findUserByUserId(userId);
		Integer userType = user.getUserType();
		String  vendorName  = user.getVendorName();
		if(userType != 2){
			result.put("errorCode", -1);
			result.put("msg", "数据异常请联系开发运维人员!");
			return result;
		}
//		RegisterChecking registerChecking = new RegisterChecking();
//		registerChecking.setCheckComment(checkComment);
		int a = userDao.updateCheckingInfo(vendorName,checkComment);
		if(a < 0){
			result.put("errorCode", -5);
			result.put("msg", "系统异常请联系开发人员!");
			return result;
		}
		result.put("errorCode", 0);
		result.put("msg", "操作成功!将会在该用户登录时进行提醒!");
		return result;
	}

	@Override
	public RegisterChecking findRegisterCheckingInfoByVendorName(
			String vendorName) {
		
		return userDao.findRegisterCheckingByVendorName(vendorName);
	}

	@Override
	public int updateTestUserCreatingTracingCodeCount(Integer vendorId,
			int count) {
		
		return userDao.updateTestUserCreatingTracingCodeCount(vendorId,count);
	}

	@Override
	public JSONObject getSingleTestingUserInfo(Integer userId) {
		JSONObject result = new JSONObject();
		UserVO userVO = userDao.findSingleUserInfo(userId);
	
		
		result.put("userName", userVO.getUserName());
		result.put("vendorId", userVO.getVendorId());
		result.put("userType", userVO.getUserType());
		result.put("vendorName", userVO.getVendorName());
		result.put("createTime", userVO.getCreateTime());
		result.put("userId", userVO.getUserId());
		result.put("checkStatus", userVO.getCheckStatus());
		result.put("vendorEmaill", userVO.getVendorEmaill());
		result.put("customPhone", userVO.getCustomPhone());
		result.put("tradeMarkImgUrl", userVO.getTradeMarkImgUrl());
		result.put("tradeMarkLicense", userVO.getTradeMarkLicense());
		result.put("bankAccountOpeningLicense", userVO.getBankAccountOpeningLicense());
		result.put("foodProductionLicence", userVO.getFoodProductionLicence());
//		object.put("organizationCodeCertificate", userVO.getOrganizationCodeCertificate());
		result.put("industrialProductionLicense", userVO.getIndustrialProductionLicense());	
		return result;
	}
	
	
	
	

}
