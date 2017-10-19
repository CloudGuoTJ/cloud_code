package com.yunma.controller.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.CommonConstants;
import com.common.util.DateUtils;
import com.common.util.MD5Util;
import com.common.util.Radix;
import com.common.util.ResultObject;

import com.yunma.entity.register.RegisterChecking;
import com.yunma.model.User;
import com.yunma.service.user.UserService;
import com.yunma.service.vendor.VendorService;
import com.yunma.utils.FileIOUtil;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.utils.PageBean;
import com.yunma.vo.user.UserVO;
import com.yunma.vo.user.UserVendorVO;
import com.yunma.vo.vendor.VendorBasicInfoVo;

@Controller
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private VendorService vendorService;

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param passwd
	 * @param 验证码
	 * @return
	 */
	@RequestMapping("/POST/user/login.do")
	@ResponseBody
	public JSONObject login(@RequestParam("username") String username,
			@RequestParam("passwd") String passwd,
			// @RequestParam(value = "captcha", required = false) String
			// captcha,
			HttpSession session) throws Exception {
		JSONObject successResult = new JSONObject();
		logger.info("开始登陆时间 : {}", DateUtils.getTimeString());
		JSONObject result = new JSONObject();
		User user = userService.findUserByUserName(username);
		if (user == null) {
			result.put("statuscode", -69);
			result.put("msg", "用户名不存在!");
			return result;
			// return (JSONObject) result.put("statuscode", -69);//(-69,
			// "用户名不存在");
		} else if (user.getStatus() == 0) {
			result.put("statuscode", -70);
			result.put("msg", "用户已锁定!");
			return result;
			// return (JSONObject) result.put("statuscode", -70)//(-70,
			// "用户已锁定！");
		}
		String passwdPrivate = user.getPasswd();
		byte[] md = passwdPrivate.getBytes("UTF8");
		BASE64Encoder encoder = new BASE64Encoder();
		String passWd = encoder.encode(md);
		if (!passWd.equals(passwd)) {
			result.put("statuscode", -71);
			result.put("msg", "密码错误!请重新输入!");
			return result;
		}

		// 登录成功
		int type;
		String msg = null;
		type = user.getUserType();
		if (type == 1) {
			msg = "厂商用户";
		}
		if (type == 99) {
			msg = "管理员用户";
		}
		
		logger.info("登录用户类型", msg);
		user.setPasswd("");
		
		Integer userType = user.getUserType();
		if(userType == 2){
			RegisterChecking registerChecking = new RegisterChecking();
			registerChecking = userService.findRegisterCheckingInfoByVendorName(user.getVendorName());
			String checkComment = registerChecking.getCheckComment();
			if(checkComment == null){
				checkComment = "您现在是待审核试用用户,联系管理员可快速成为正式用户,享受更多服务!";
			}
			successResult.put("checkComment", checkComment);
		}
		
		successResult.put("id", user.getUserId());
		successResult.put("userName", user.getUserName());
		successResult.put("deleteFlag", user.getDeleteFlag());
		successResult.put("userType", user.getUserType());
		successResult.put("statuscode", 1);
		successResult.put("msg", msg);
		successResult.put("vendorId", user.getVendorId());
		successResult.put("vendorName", user.getVendorName());

		logger.info("登陆完成时间:{}", DateUtils.getTimeString());
		return successResult;
	}

	/**
	 * 登录页面验证码信息
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	// @RequestMapping(value = "/GET/user/captcha.do")
	// @ResponseBody
	// public void captcha(HttpServletRequest request, HttpServletResponse
	// response,HttpSession session)
	// throws ServletException, IOException {
	// logger.debug("获取验证码信息:开始");
	// // System.out.println();
	// CaptchaUtil.outputCaptcha(request,response,session);
	//
	// }

	/**
	 * 根据用户ID查找用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/GET/user/userInfo.do")
	@ResponseBody
	public JSONObject getUserInfo(@RequestParam("userId") Integer userId) {
		logger.info("获取用户信息开始时间 : {}", DateUtils.getTimeString());
		UserVendorVO userVO = userService.getUserVOInfo(userId);
		JSONObject resultObject = new JSONObject();
		if (userVO != null) {
			resultObject.put("userName", userVO.getUserName());
			resultObject.put("userId", userVO.getUserId());
			resultObject.put("vendorName", userVO.getVendorName());
			resultObject.put("vendorId", userVO.getVendorId());
			resultObject.put("createTime", userVO.getCreateTime());
			logger.info("获取用户信息结束时间 : {}", DateUtils.getTimeString());
			return resultObject;
		} else {
			resultObject.put("errorCode", -73);
			resultObject.put("msg", "请登录");
			return resultObject;

		}
	}

	/**
	 * 用户信息分表查询
	 * 
	 * @param pageBean
	 * @param vo
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/GET/user/userInfoList.do")
	@ResponseBody
	public PageBean getUserInfo(PageBean pageBean, UserVO vo) {

		return userService.getUserInfo(pageBean, vo);
	}

	/**
	 * 创建厂商用户
	 * 
	 * @param userName
	 * @param passwd
	 * @param vendorName
	 * @return
	 */

	@RequestMapping("/POST/user/vendorUser.do")
	@ResponseBody
	public JSONObject addUser(@RequestParam("userName") String userName,
			@RequestParam("passwd") String passwd,
			@RequestParam("vendorName") String vendorName,
			@RequestParam("vendorId") Integer vendorId) {
		JSONObject result = new JSONObject();
		User user = userService.findUserByUserName(userName);
		User userPrivate = userService.findUserByVendorId(vendorId);
		String passwdPri = MD5Util.makeMd5Sum(passwd.getBytes());

		if (user != null || userPrivate != null) {
			result.put("msg", "用户已存在!");
			result.put("errorCode", -1);
			return result;
			// return ResultObject.createErrorInstance(-74, "用户已存在！");
		} else {
			User user1 = new User();
			// int vendorId = userDao.getMaxVendorId() + 1;
			user1.setUserName(userName);
			user1.setPasswd(passwdPri);
			user1.setUserType(CommonConstants.User_Type_ProductVendor
					.getState());
			user1.setVendorId(vendorId);
			user1.setVendorName(vendorName);
			int result1 = userService.createUser(user1);
			if (result1 < 0) {
				result.put("msg", "新增失败!请重试.");
				result.put("errorCode", "-2");

				return result;
			} else {
				result.put("msg", "新增成功");
				result.put("errorCode", "0");
				return result;
			}
		}
	}

	/**
	 * 退出操作
	 * 
	 * @param userId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/POST/user/logout.do")
	@ResponseBody
	public ResultObject logout(@RequestParam("userId") Integer userId,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// XMemcachedHelper.deleteCache(userId.toString());
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("userCookie".equals(cookie.getName())) {
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		return ResultObject.createErrorInstance(0, "success");
	}

	/**
	 * 修改密码
	 * 
	 * @param passwd
	 * @param userId
	 * @return
	 */
	@RequestMapping("/POST/user/updatePasswd.do")
	@ResponseBody
	public ResultObject setPasswd(@RequestParam("userId") int userId,
			@RequestParam("passwd") String passwd) {
		logger.info("修改密码开始时间 : {}", DateUtils.getTimeString());
		User user = userService.findUserByUserId(userId);
		int userType = user.getUserType();
		if (userType != CommonConstants.User_Type_SuperAdmin.getState()) {
			if (user.getUserId() != userId) {
				return ResultObject.createErrorInstance(-75, "请本人修改");
			}
		}
		passwd = MD5Util.makeMd5Sum(passwd.getBytes());
		int result = userService.setPasswd(passwd, userId);
		if (result < 0) {
			return ResultObject.createErrorInstance(-4, "修改失败");
		} else {
			logger.info("修改密码结束时间 : {}", DateUtils.getTimeString());
			return ResultObject.createErrorInstance(0, "修改成功");
		}
	}

	/**
	 * 设置找回密码
	 * 
	 * @param passwd
	 * @param userId
	 * @return
	 */
	@RequestMapping("/POST/user/findPasswd.do")
	@ResponseBody
	public ResultObject setFindPasswd(@RequestParam("passwd") String passwd,
			@RequestParam("userId") int userId) {
		User user = userService.findUserByUserId(userId);
		int userType = user.getUserType();
		if (userType != CommonConstants.User_Type_SuperAdmin.getState()) {
			if (user.getUserId() != userId) {
				return ResultObject.createErrorInstance(-75, "请本人修改");
			}
		}
		int result = userService.setFindPasswd(passwd, userId);
		if (result < 0) {
			return ResultObject.createErrorInstance(-13, "设置失败");
		} else {
			return ResultObject.createErrorInstance(0, "设置成功");
		}
	}

	/**
	 * 锁定账号
	 * 
	 * @param UserId
	 * @return
	 */

	@RequestMapping("/POST/user/lockUser.do")
	@ResponseBody
	public JSONObject lockUser(@RequestParam("userId") Integer userId) {
		JSONObject result = new JSONObject();
		logger.info("锁定账号开始时间 : {}", DateUtils.getTimeString());
		result = userService.setLockUser(userId);
		logger.info("锁定账号结束时间 : {}", DateUtils.getTimeString());
		return result;
	}

	/**
	 * 解锁账号
	 * 
	 * @param UserId
	 * @return
	 */
	@RequestMapping("/POST/user/canselLockUser.do")
	@ResponseBody
	public JSONObject cancelLockUser(@RequestParam("userId") Integer userId) {
		JSONObject result = new JSONObject();
		logger.info("锁定账号开始时间 : {}", DateUtils.getTimeString());
		result = userService.cencalLockUser(userId);
		logger.info("取消锁定账号结束时间 : {}", DateUtils.getTimeString());
		return result;
	}

	/**
	 * 注册账号
	 * 
	 * @param UserId
	 * @return
	 */
	@RequestMapping("/POST/user/registvendorUser.do")
	@ResponseBody
	public JSONObject registvendorUser(
			@RequestParam("username") String username,
			@RequestParam("passwd") String passwd,
			@RequestParam("vendorName") String vendorName,// 厂商名
			@RequestParam("industryName") String industryName,// 行业
			@RequestParam("contactName") String contactName,// 联系人
			@RequestParam("customPhone") String customPhone,// 联系人手机
			@RequestParam(required = false) String vendorAddress,// 企业联系地址,由用户添加
			@RequestParam("vendorEmaill") String vendorEmaill,// 企业邮箱
			@RequestParam(required = false) MultipartFile tradeMarkImgUrl,// 企业营业执照url
			@RequestParam(required = false) MultipartFile tradeMarkLicense// 企业商标注册证书图片
	) {
		JSONObject result = new JSONObject();
		RegisterChecking registerChecking = new RegisterChecking();// 试用用户审核表
		VendorBasicInfoVo vendorVo = new VendorBasicInfoVo();// 添加vendor表
		User user = new User();// 创建User信息
		logger.info("注册测试用户开始: {}", DateUtils.getTimeString());
		// 储存注册用户信息
		// 1.查询用户名是否已存在
		User priUser = userService.findUserByUserName(username);
		if (priUser != null) {
			result.put("msg", "用户名已存在请重新输入用户名!");
			result.put("errorCode", -1);
			return result;
		}
		// 2.查询vendorName是否存在
		User vendorUser = userService.findUserByVendorName(vendorName);
		if (vendorUser != null) {
			result.put("msg", "注册的商家已存在!");
			result.put("errorCode", -3);
			return result;
		}
		// 3.保存测试用户信息

		// 3.1保存vendor信息

		vendorVo.setBrandName(vendorName);
		vendorVo.setIndustryName(industryName);
		vendorVo.setContactName(contactName);
		vendorVo.setCustomPhone(customPhone);
		// vendorVo.setVendorAddress(vendorAddress);
		vendorVo.setVendorEmaill(vendorEmaill);
		// addVendorBasicInfoVo
		int a = vendorService.addVendorBasicInfoVo(vendorVo);
		if (a < 0) {
			result.put("msg", "预注册商家信息异常!请检查数据.");
			result.put("errorCode", -5);
			return result;
		}

		// 获取存入的vendorId
		Integer vendorId = userService
				.getVendorIdFromVendorDetailByVendorName(vendorName);

		// 3.2追加User信息存入vendor表
		String passwdPri = MD5Util.makeMd5Sum(passwd.getBytes());// 加密原始密码

		user.setVendorName(vendorName);
		user.setPasswd(passwdPri);
		user.setUserName(username);
		user.setVendorId(vendorId);
		user.setUserType(2);// 适用用户类型 2 ,正式用户为 1 .
		user.setTestSecurityCodeCount(0);
		user.setTestTracingCodeCount(0);
		// 注册成功,返回数据
		int b = userService.createUser(user);
		if (b < 0) {
			result.put("msg", "注册失败请联系管理员!");
			result.put("errorCode", -7);
			return result;
		}
		// 3.3将试用用户存入试用表

		// 验证图片将图片保存留待验证

		String tradePath;
		String realPath;
		String path = null;
		String namePrefix = null;
		String content = null;
		try {
			tradePath = getClass().getResource("/").toURI().getPath();
			path = tradePath.substring(0, tradePath.lastIndexOf("webapps") + 7);
			namePrefix = Radix.convert10To62(vendorId, 2);
			content = "/img/" + namePrefix;// 文件夹位置
		} catch (URISyntaxException e1) {

			e1.printStackTrace();
		}

		if (tradeMarkImgUrl != null && tradeMarkImgUrl.getSize() > 0) {
			try {

				String realPathTra;

				byte[] tradeMarkImgUrlFile = tradeMarkImgUrl.getBytes();
				try {
					// 创建对应文件夹
					realPathTra = path + content;
					// 保存文件
					FileOutputStream fosTra = new FileOutputStream(
							realPathTra
									+ (UUID.randomUUID().toString().replace(
											"\\-", "")).substring(1, 6)
									+ "T.jpg");
					fosTra.write(tradeMarkImgUrlFile);
					fosTra.close();
					String Tname = content + "T.jpg";

					registerChecking.setTradeMarkImgUrl(Tname);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		if (tradeMarkLicense != null && tradeMarkLicense.getSize() > 0) {

			try {

				byte[] tradeMarkLicenseFile = tradeMarkLicense.getBytes();
				try {
					// 创建对应文件夹
					realPath = path + content;
					// 保存文件
					FileOutputStream fos = new FileOutputStream(
							realPath
									+ (UUID.randomUUID().toString().replace(
											"\\-", "")).substring(1, 6)
									+ "L.jpg");
					fos.write(tradeMarkLicenseFile);
					fos.close();
					String Lname = content + "L.jpg";
					registerChecking.setTradeMarkLicense(Lname);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

		registerChecking.setContactName(contactName);
		registerChecking.setCustomPhone(customPhone);
		registerChecking.setVendorName(vendorName);
		registerChecking.setIndustryName(industryName);
		registerChecking.setVendorAddress(vendorAddress);
		registerChecking.setVendorEmaill(vendorEmaill);
		registerChecking.setCheckStatus(1);

		int i = userService.addVendorTestRegister(registerChecking);
		if (i < 0) {
			result.put("msg", "添加试用用户列表异常,请联系管理员!");
			result.put("errorCode", -2);
			return result;
		}
		result.put("msg", "注册成功!您现在已成为试用用户!请登录!");
		result.put("errorCode", 0);
		logger.info("注册测试用户结束时间 : {}", DateUtils.getTimeString());
		return result;
	}

	/**
	 * 通过审查 if( userVO.getTradeMarkImgUrl().length() == 0 ||
	 * userVO.getTradeMarkLicense().length() == 0){ object.put("warnMassage",
	 * "测试用户未上传相关证书,请提醒上传!"); }
	 * 
	 * @param UserId
	 * @return
	 */
	@RequestMapping("/POST/user/passCheckingUser.do")
	@ResponseBody
	public JSONObject passCheckingUserInfo(
			@RequestParam("userId") Integer userId) {
		JSONObject result = new JSONObject();
		logger.info("通过审查开始时间 : {}", DateUtils.getTimeString());
		result = userService.passCheckingUserInfo(userId);
		logger.info("取消锁定账号结束时间 : {}", DateUtils.getTimeString());
		return result;
	}

	/**
	 * 用户信息分表查询测试用户
	 * 
	 * @param pageBean
	 * @param vo
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping("/GET/user/userUncheckedUserInfoInfoList.do")
	@ResponseBody
	public PageBean getTestUserInfo(PageBean pageBean, UserVO vo) {

		return userService.getUncheckedUserInfo(pageBean, vo);
	}

	/**
	 * 检查用户名是否重复
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/GET/user/checkOutUserNameResult.do")
	@ResponseBody
	public JSONObject getTestcheckOutUserNameResult(
			@RequestParam("username") String username) {
		JSONObject result = new JSONObject();
		result = userService.checkOutUserName(username);
		return result;
	}

	/**
	 * 注册测试用户证书完善
	 * 
	 * @param tradeMarkImgUrl
	 * @param tradeMarkLicense
	 * @param bankAccountOpeningLicense
	 * @param foodProductionLicence
	 * @param organizationCodeCertificate
	 *            industrialProductionLicense
	 * @return
	 */
	@RequestMapping("/POST/user/uploadRegistrationPic.do")
	@ResponseBody
	public JSONObject uploadRegistrationPic(

			@RequestParam("vendorId") int vendorId,
			@RequestParam(required = false) MultipartFile tradeMarkImgUrl,// 企业营业执照url
			@RequestParam(required = false) MultipartFile tradeMarkLicense,// 企业商标注册证书图片
			@RequestParam(required = false) MultipartFile bankAccountOpeningLicense,// 银行开户许可
			@RequestParam(required = false) MultipartFile foodProductionLicence,// 食品生产许可证
			// @RequestParam(required = false)MultipartFile
			// organizationCodeCertificate,//组织机构代码证
			@RequestParam(required = false) MultipartFile industrialProductionLicense// 工业产品生成许可证
	) {
		JSONObject result = new JSONObject();

		result = userService.uploadRegistrationPic(vendorId, tradeMarkImgUrl,
				tradeMarkLicense, bankAccountOpeningLicense,
				foodProductionLicence,
				// organizationCodeCertificate,
				industrialProductionLicense);

		return result;
	}
	
	/**
	 * 不合格未通过审核用户
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/POST/user/unqualifiedPassingInfo.do")
	@ResponseBody
	public JSONObject unqualifiedPassingInfoSave(
			@RequestParam("userId") Integer userId,
			@RequestParam("checkComment") String checkComment//审核未通过概述
			) {
		JSONObject result = new JSONObject();
		result = userService.unqualifiedPassingInfoSave(userId,checkComment);
		return result;
	}
	
	/**
	 * 不合格未通过审核用户
	 * 
	 * @param userName
	 * @return
	 */
	@RequestMapping("/POST/user/singleTestingUserInfo.do")
	@ResponseBody
	public JSONObject getSingleTestingUserInfo(
			@RequestParam("userId") Integer userId
		//审核未通过概述
			) {
		JSONObject result = new JSONObject();
		result = userService.getSingleTestingUserInfo(userId);
		return result;
	}
	
}
