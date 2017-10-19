package com.yunma.controller.vendor;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.DateUtils;
import com.yunma.cache.VendorCache;
import com.yunma.controller.couponRule.CouponRuleController;
import com.yunma.entity.image.ImgAndVendor;
import com.yunma.entity.vendor.VendorAccountPayDetail;
import com.yunma.entity.vendor.VendorBrandImgs;
import com.yunma.entity.vendor.VendorInfo;
import com.yunma.model.User;
import com.yunma.service.user.UserService;
import com.yunma.service.vendor.VendorService;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.utils.PageBean;
import com.yunma.vo.image.ImgVendorVo;
import com.yunma.vo.vendor.VendorAllinfoVo;
import com.yunma.vo.vendor.VendorBasicInfoVo;

@Controller
public class VendorController extends BaseController {
	@Resource
	private VendorCache vendorCache;

	@Resource
	private VendorService vendorService;

	@Resource
	private CouponRuleController couponRuleController;

	@Resource
	private UserService userService;

	/**
	 * 获取企业信息
	 * 
	 * @return
	 */
	@RequestMapping("/GET/vendor/basicInfo.do")
	@ResponseBody
	public JSONObject getBasicInfo(Integer vendorId) {
		logger.info("VendorController.basicInfo中获取企业信息开始时间："
				+ DateUtils.getTimeString());
		JSONObject result = vendorCache.getBasicInfo(vendorId);
		logger.info("VendorController.basicInfo中获取企业信息结束时间："
				+ DateUtils.getTimeString());
		return result;
	}

	/**
	 * 新增企业基本信息
	 * 
	 * @param request
	 * @param vendorId
	 * @param vendorName
	 * @param brandName
	 * @param imgUrl
	 * @param industryName
	 * @param contactName
	 * @param customPhone
	 * @param vendorWeixin
	 * @param customTel
	 * @param vendorAddress
	 * @param link
	 * @param officialAccounts
	 * @return
	 */
	@RequestMapping("/ADD/vendor/basicInfo.do")
	@ResponseBody
	public JSONObject addWxConfigInfo(HttpServletRequest request,
			Integer vendorId, String vendorName, String brandName,
			MultipartFile imgUrl, String industryName, String contactName,
			String customPhone, String vendorWeixin, String customTel,
			String vendorAddress, String link, String officialAccounts) {
		JSONObject result = new JSONObject();
		VendorBasicInfoVo vo = new VendorBasicInfoVo();
		

		int vendorId1 = vendorService.getVendorId();

		if (vendorId1 > 0) {

		}

		try {

			if (request instanceof MultipartHttpServletRequest) {

				request.setCharacterEncoding("UTF-8");

				// vo.setVendorId(vendorId);
				vo.setVendorName(vendorName);
				vo.setBrandName(brandName);
				vo.setIndustryName(industryName);
				vo.setContactName(contactName);
				vo.setCustomPhone(customPhone);
				vo.setVendorWeixin(vendorWeixin);
				vo.setCustomTel(customTel);
				vo.setVendorAddress(vendorAddress);
				vo.setLink(link);
				vo.setOfficialAccounts(officialAccounts);
				ImageUploladUtil util = new ImageUploladUtil();
				if (imgUrl != null && imgUrl.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(imgUrl, result);
					if ((Integer) imageResult.get("uploadState") > 0) {

						vo.setImgUrl(imageResult.getString("imgUrl").toString());
					}
				}
			}
			int temp = vendorService.addVendorBasicInfoVo(vo);
			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "新增失败");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改企业基本信息
	 * 
	 * @param vendorId
	 *            id
	 * @param brandName
	 *            品牌商名称
	 * @param industryName
	 *            所属行业
	 * @param contactName
	 *            联系人
	 * @param customPhone
	 *            手机号码
	 * @param vendorWeixin
	 *            微信绑定
	 * @param customTel
	 *            客服电话
	 * @param vendorAddress
	 *            联系地址
	 * @param link
	 *            官网地址
	 * @return
	 */
	@RequestMapping("/UPDATE/vendor/basicInfo.do")
	@ResponseBody
	public JSONObject updateBasicInfo(HttpServletRequest request,
			Integer vendorId, String vendorName, String brandName,
			MultipartFile imgUrl, String industryName, String contactName,
			String customPhone, String vendorWeixin, String customTel,
			String vendorAddress, String link, String officialAccounts

	) {
		JSONObject result = new JSONObject();

		VendorBasicInfoVo vo = new VendorBasicInfoVo();

		ImageUploladUtil util = new ImageUploladUtil();

		try {
			if (request instanceof MultipartHttpServletRequest) {

				request.setCharacterEncoding("UTF-8");

				vo.setVendorId(vendorId);
				vo.setBrandName(brandName);
				vo.setIndustryName(industryName);
				vo.setContactName(contactName);
				vo.setCustomPhone(customPhone);
				vo.setVendorWeixin(vendorWeixin);
				vo.setCustomTel(customTel);
				vo.setVendorAddress(vendorAddress);
				vo.setLink(link);
				vo.setOfficialAccounts(officialAccounts);

				if (imgUrl != null && imgUrl.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(imgUrl, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						vo.setImgUrl(imageResult.getString("imgUrl").toString());
					}
				}
			}
			VendorInfo info = null;
			int temp = vendorService.updateVendorBasicInfoVo(vo);

			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "修改成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "修改失败");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取厂商账号余额
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendor/balance.do")
	@ResponseBody
	public JSONObject getBalanceByVendorId(Integer vendorId) {
		logger.info("获取厂商账号余额开始时间 : {}", DateUtils.getTimeString());
		double getBalanceByVendorId = vendorService
				.getBalanceByVendorId(vendorId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("balance", getBalanceByVendorId);
		logger.info("获取厂商账号余额结束时间 : {}", DateUtils.getTimeString());
		return jsonObject;
	}

	/**
	 * 获取厂商账户充值余额
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendor/paymoney.do")
	@ResponseBody
	public JSONObject getPaymoneyByVendorId(Integer vendorId) {
		logger.info("获取厂商账户充值金额开始时间：{}", DateUtils.getTimeString());
		double getPaymoneyByVendorId = vendorService
				.getPaymoneyByVendorId(vendorId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("paymoney", getPaymoneyByVendorId);
		logger.info("获取厂商账户充值金额开始时间：{}", DateUtils.getTimeString());
		return jsonObject;
	}

	/**
	 * 查询企业信息
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/vendor/getVendorInfo.do")
	@ResponseBody
	public JSONObject getVendorInfo(Integer vendorId) {
		JSONObject result = new JSONObject();

		VendorInfo info = vendorService.getVendorInfo(vendorId);
		List<ImgVendorVo> list = vendorService.getImgVendor(info.getVendorId());
		if (!list.isEmpty() && list.size() > 0) {
			String[] arr = (String[]) list.toArray();
			result.put("brandImgs", arr);
		}
		if (info != null) {
			result.put("detailId", info.getDetailId());
			result.put("vendorId", info.getVendorId());
			result.put("brandName", info.getBrandName());
			result.put("industryName", info.getIndustryName());
			result.put("customPhone", info.getCustomPhone());
			result.put("functionType", info.getFunctionType());
			result.put("link", info.getLink());
			result.put("vendorMall", info.getVendorMall());
			result.put("wxConfig", info.getWxConfig());
			result.put("Emaill", info.getEmaill());
			result.put("img_vendor_qualification",
					info.getImg_vendor_qualification());
			result.put("img_trademark_certificate",
					info.getImg_trademark_certificate());
			result.put("img_industrial_production_license",
					info.getImg_industrial_production_license());
			result.put("img_food_production_license",
					info.getImg_food_production_license());
			result.put("img_bank_account_license",
					info.getImg_bank_account_license());
		}

		return result;
	}

	/**
	 * 修改企业信息
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/UPDATE/vendor/updateVendorInfo.do")
	@ResponseBody
	public JSONObject updateVendorInfo(HttpServletRequest request,
			VendorInfo info
	// Integer detailId,Integer vendorId,
	// String brandName,String industryName,String customPhone,String
	// functionType,
	// String link,String vendorMall,String wxConfig,String Emaill,
	// String img_vendor_qualification,String img_trademark_certificate,
	// String img_industrial_production_license,String
	// img_food_production_license,
	// String img_bank_account_license
	) {
		JSONObject result = new JSONObject();
		try {
			request.setCharacterEncoding("UTF-8");
			System.out.println("商家名称：" + info.getBrandName());
			int temp = vendorService.updateVendorInfo(info);
			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "修改失败");
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 新增企业信息
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/POST/vendor/addVendorInfo.do")
	@ResponseBody
	public JSONObject addVendorInfo(HttpServletRequest request,
			String brandName, String industryName, String customPhone,
			String functionType, String link, String vendorMall,
			String wxConfig, String Emaill,
			MultipartFile img_vendor_qualification,
			MultipartFile img_trademark_certificate,
			MultipartFile img_industrial_production_license,
			MultipartFile img_food_production_license,
			MultipartFile img_bank_account_license,
			@RequestParam(value = "imgIds[]", required = false) String[] imgIds) {
		JSONObject result = new JSONObject();
		JSONObject result1 = new JSONObject();
		VendorInfo info = new VendorInfo();
		ImageUploladUtil util = new ImageUploladUtil();

		int vendorId = vendorService.countVendor();
		try {

			if (request instanceof MultipartHttpServletRequest) {
				request.setCharacterEncoding("UTF-8");

				// info.setVendorId(vendorId);
				info.setBrandName(brandName);
				info.setIndustryName(industryName);
				info.setCustomPhone(customPhone);
				info.setFunctionType(functionType);
				info.setLink(link);
				info.setVendorMall(vendorMall);
				info.setWxConfig(wxConfig);
				info.setEmaill(Emaill);
				// 判断是否有图片
				if (img_vendor_qualification != null
						&& img_vendor_qualification.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							img_vendor_qualification, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						info.setImg_vendor_qualification(imageResult.getString(
								"imgUrl").toString());
					}
				}
				if (img_trademark_certificate != null
						&& img_trademark_certificate.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							img_trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						info.setImg_trademark_certificate(imageResult
								.getString("imgUrl").toString());
					}
				}
				if (img_industrial_production_license != null
						&& img_industrial_production_license.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							img_trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						info.setImg_industrial_production_license(imageResult
								.getString("imgUrl").toString());
					}
				}
				if (img_food_production_license != null
						&& img_food_production_license.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							img_trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						info.setImg_food_production_license(imageResult
								.getString("imgUrl").toString());
					}
				}
				if (img_bank_account_license != null
						&& img_bank_account_license.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							img_trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						info.setImg_bank_account_license(imageResult.getString(
								"imgUrl").toString());
					}
				}
			}
			int temp = vendorService.addVendorInfo(info);
			Integer vendorDetailId = null;
			if (temp > 0) {
				vendorDetailId = info.getVendorId();
				// 添加系统默认的优惠券规则
				addDefaultCouponRule(info.getVendorId());
				result1.put("statuscode", 1);
				result1.put("msg", "成功");
			} else {
				result1.put("statuscode", -2);
				result1.put("msg", "新增失败");
			}

			if (vendorDetailId != null) {
				ImgAndVendor imgVendor = new ImgAndVendor();
				imgVendor.setVendor_detail_id(vendorDetailId);
				if (imgIds.length > 0) {
					for (int i = 0; i < imgIds.length; i++) {
						int imgId = Integer.parseInt(imgIds[i]);
						imgVendor.setImg_id(imgId);
						vendorService.addImgVendor(imgVendor);
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result1;
	}

	/**
	 * 5 上传图片
	 * 
	 * @param vendorDetailId
	 * @param brandImg
	 * @return
	 */
	@RequestMapping("/POST/vendor/uploadVendorBrandImg.do")
	@ResponseBody
	public JSONObject uploadVendorBrandImg(HttpServletRequest request,
			MultipartFile brandImg) {
		JSONObject result = new JSONObject();
		VendorBrandImgs img = new VendorBrandImgs();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		if (brandImg != null && brandImg.getSize() > 0) {
			JSONObject imgObj = vendorService.uploadImage(brandImg, result);
			if ((Integer) imgObj.get("uploadState") > 0) {
				img.setImgName(imgObj.getString("fileName").toString());
				img.setImgUrl(imgObj.getString("imgUrl").toString());
				img.setImgheight(imgObj.getInteger("height"));
				img.setImgWidth(imgObj.getInteger("width"));

				int temp = vendorService.addImageInfo(img);
				if (temp > 0) {
					result.put("imgId", img.getId());
					result.put("statuscode", 1);
					result.put("msg", "成功");
				} else {
					result.put("statuscode", -2);
					result.put("msg", "上传失败");
				}
			} else {
				result.put("statuscode", -2);
				result.put("msg", "上传失败");
				return imgObj;
			}
		}

		return result;

	}

	/**
	 * 分页查询企业信息
	 * 
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/vendor/info.do")
	@ResponseBody
	public PageBean getVendor(PageBean pageBean, VendorAllinfoVo vo) {

		return vendorService.getVendor(pageBean, vo);
	}

	/**
	 * 厂商余额充值
	 * 
	 * @param userId
	 * @param payMoney
	 * @param payVendorAccount
	 * @param payChannel
	 * @param payUser
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/GET/vendor/account.do")
	@ResponseBody
	public JSONObject payVendorAccount(@RequestParam("userId") int userId,
			@RequestParam("payMoney") double payMoney,
			@RequestParam("payVendorAccount") String payVendorAccount,
			@RequestParam("payChannelName") String payChannel,
			@RequestParam("payUserName") String payUser, HttpSession session)
			throws UnsupportedEncodingException {
		JSONObject result = new JSONObject();
		User user = userService.findUserByUserId(userId);
		String payUserName = new String(payUser.getBytes("iso8859-1"), "UTF-8");
		String payChannelName = new String(payChannel.getBytes("iso8859-1"),
				"UTF-8");

		if (payMoney > 0) {
			int i = vendorService.payMoney(1, payMoney);
			if (i > 0) {
				result.put("isSuccess", "充值成功");// 充值成功
				VendorAccountPayDetail vendorWallet = new VendorAccountPayDetail();
				vendorWallet.setVendorId(user.getVendorId());
				vendorWallet.setPayMoney(payMoney);
				vendorWallet.setRemark("1");
				vendorWallet.setCreateTime(new java.sql.Date(
						new java.util.Date().getTime()));
				vendorWallet.setSerialNumber(DateUtils.format(new Date(),
						"yyyyMMddHHmmss"));// 充值流水号
				vendorWallet.setPayVendorAccount(payVendorAccount);// 充值账户
				vendorWallet.setPayChannelName(payChannelName);// 充值途径名称
				vendorWallet.setPayUserName(payUserName);// 充值用户名
				vendorWallet.setVendorAccountId(vendorService
						.getVendorAccountId(user));
				vendorWallet.setStatus(0);
				vendorWallet.setPayChannelRateMoney(2.00);// 手续费

			} else {
				result.put("isSuccess", "未查找到账户");// 充值失败
			}
		} else {
			result.put("isSuccess", "充值失败");// 充值失败
		}
		return result;
	}

	/**
	 * 添加企业资质认证证书
	 * 
	 * @param request
	 * @param vendorQualification
	 *            企业营业执照
	 * @param trademark_certificate
	 *            商标权证书
	 * @return
	 */
	@RequestMapping("/ADD/vendor/certificationInfo.do")
	@ResponseBody
	public JSONObject addUploadVendorCertificationImg(
			HttpServletRequest request, MultipartFile vendorQualification,
			MultipartFile trademark_certificate) {
		JSONObject result = new JSONObject();
		VendorAllinfoVo vo = new VendorAllinfoVo();
		ImageUploladUtil util = new ImageUploladUtil();
		try {
			if (request instanceof MultipartHttpServletRequest) {
				request.setCharacterEncoding("UTF-8");

				if (vendorQualification != null
						&& vendorQualification.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							vendorQualification, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						vo.setVendorQualification(imageResult.getString(
								"imgUrl").toString());
					}
				}
				if (trademark_certificate != null
						&& trademark_certificate.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						vo.setTrademark_certificate(imageResult.getString(
								"imgUrl").toString());
					}
				}
			}
			int temp = vendorService.updateVendorCertification();

			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "提交成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "提交失败");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新企业资质认证证书
	 * 
	 * @param request
	 * @param vendorQualification
	 *            企业营业执照
	 * @param trademark_certificate
	 *            商标权证书
	 * @return
	 */
	@RequestMapping("/UPDATE/vendor/certificationInfo.do")
	@ResponseBody
	public JSONObject updateUploadVendorCertificationImg(
			HttpServletRequest request, MultipartFile vendorQualification,
			MultipartFile trademark_certificate) {
		JSONObject result = new JSONObject();
		VendorAllinfoVo vo = new VendorAllinfoVo();
		ImageUploladUtil util = new ImageUploladUtil();

		try {
			if (request instanceof MultipartHttpServletRequest) {
				request.setCharacterEncoding("UTF-8");

				if (vendorQualification != null
						&& vendorQualification.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							vendorQualification, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						vo.setVendorQualification(imageResult.getString(
								"imgUrl").toString());
					}
				}
				if (trademark_certificate != null
						&& trademark_certificate.getSize() > 0) {
					JSONObject imageResult = util.uploadImage(
							trademark_certificate, result);
					if ((Integer) imageResult.get("uploadState") > 0) {
						vo.setTrademark_certificate(imageResult.getString(
								"imgUrl").toString());
					}
				}
			}
			int temp = vendorService.addVendorCertification();

			if (temp > 0) {
				result.put("statuscode", 1);
				result.put("msg", "更新成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "更新失败");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 添加系统默认的优惠券规则
	 * 
	 * @param vendorId
	 */
	private void addDefaultCouponRule(Integer vendorId) {
		String ruleName = "系统默认规则";
		String startTime = "2016-01-01";
		String endTime = "2100-01-01";
		Integer[] blackList = null;
		Integer[] whiteList = null;
		String timeType = "0";
		String isBlackWhite = null;
		String isScope = null;
		String isDefault = "1";
		Integer[] productList = null;
		Integer[] orderList = null;
		couponRuleController.addCouponRule(vendorId, ruleName, startTime,
				endTime, blackList, whiteList, timeType, isBlackWhite, isScope,
				isDefault, productList, orderList);
	}

	@RequestMapping("/test3.do")
	public void test(Integer vendorId) {
		addDefaultCouponRule(vendorId);
	}

}
