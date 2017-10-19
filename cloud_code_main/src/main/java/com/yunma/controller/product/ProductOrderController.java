package com.yunma.controller.product;


import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.common.util.CommonConstants;
import com.common.util.CommonUtils;
import com.common.util.DateUtils;
import com.common.util.ResultObject;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.User;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.product.ProductService;
import com.yunma.service.user.UserService;
import com.yunma.utils.PageBean;
import com.yunma.vo.product.ProductOrderVO;

@Controller
public class ProductOrderController extends BaseController {
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

	/* 追加订单 */
	@RequestMapping("/POST/product/productOrder.do")
	@ResponseBody
	public ResultObject addOrder(ProductOrder order) {
		logger.info("新增订单开始: {}", DateUtils.getTimeString());
		int result = 0;
		try {
			Integer vendorId = order.getVendorId();
			User user = userService.findUserByVendorId(vendorId);
			String vendorName = user.getVendorName();
			order.setVendorName(vendorName);
			result = orderService.createOrder(order);
		} catch (Exception e) {
			logger.error("新增订单时：" + e.getMessage(), e);
		}

		if (result <= 0) {
			return ResultObject.createErrorInstance(-2, "新增失败,添加内容不符合要求");
		} else {
			logger.info("新增订单结束,新增成功");
			return ResultObject.createErrorInstance(0, "新增成功");
		}
	}
	@RequestMapping("/DELETE/product/productOrder.do")
	@ResponseBody
	public ResultObject deleteOrder(@RequestParam("orderId") Integer orderId) {
		logger.info("删除订单开始:");
		int result = 0;
		try {
			logger.info("删除订单附属的子表:");
			orderService.dropBatchOrderTable(orderId);	
			logger.info("删除订单:");
			result += orderService.deleteOrder(orderId);
		} catch (Exception e) {
			logger.error("删除订单时：" + e.getMessage(), e);
		}

		if (result <= 0) {
			return ResultObject.createErrorInstance(-2, "删除失败,请重试");
		} else {
			logger.info("删除订单结束,删除成功");
			return ResultObject.createErrorInstance(0, "删除成功");
		}
	}

	@RequestMapping("/GET/product/productOrder/list.do")
	@ResponseBody
	public ResultObject listOrder(
			@RequestParam("vendorId") int vendorId,
			@RequestParam("num") int num,
			@RequestParam("page") int page,
			@RequestParam(value = "productName", required = false) String productName)
			throws Exception {
		logger.info("获取订单信息开始:{}", DateUtils.getTimeString());
		User user = userService.findUserByUserId(vendorId);
		List<ProductOrder> list;
		int count = 0	;
		if (productName != null && !("").equals(productName)) {
			list = orderService.findAllOrderByVendorIdOrProduct(user,
					productName);
			count = orderService.findAllOrderByVendorIdOrProductNameCount(user,
					productName);
		} else {
			list = orderService.findOrderList(num, page, user);
			count = orderService.findOrderListCount(user);
		}
		ResultObject result = new ResultObject();
		result.setData(list);
		result.setMsg(String.valueOf(count));
		logger.info("获取订单列表结束时间 : {}", DateUtils.getTimeString());

		return null;

	}
	/**
	 * 生成二维码
	 * @param orderId
	 * @param request
	 * @return
	 */
	@RequestMapping("/POST/securityCode/createSecurityCode.do")
	@ResponseBody
	public ResultObject createSecurityCode(
			@RequestParam("orderId") Integer orderId)
		//	HttpServletRequest request)
	{
		logger.info("生成二维码开始时间 : {}", DateUtils.getTimeString());
		int result = 0;
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		String vendorName = order.getVendorName();
		Level level = null;
		Logger loggerPri = null;
		/**
		 * 使用事务生成二维码
		 */
		try{
			boolean isRepeat = CommonUtils.isRepeat("GenerateSecurityCode_" + orderId, CommonConstants.TIME_MINUTE_1);
			 if (isRepeat) {
	                return ResultObject.createErrorInstance(-3, "生成二维码重复提交！");
	            }

			/**
			 * 获取order信息和产品信息
			 */
			 /**
			  * 动态改变debug等级
			  */
			level = Level.toLevel(Priority.INFO_INT, Level.INFO);
			loggerPri = LogManager
					.getLogger("com.yunma.dao.product.ProductOrderDao");
			loggerPri.setLevel(level);
			 
			 String codeTableName = "tb_security_code_" + orderId;
			 String tableName2 = orderService.findIfDbName(codeTableName);
			 if(tableName2 != null ){
				 return ResultObject.createErrorInstance(-2, "该订单已生成过二维码,请重新选择订单!");
				 
			 }
			 
			Product product = productService.findProductByProductId(order.getProductId());
			List<SecurityCode> list =orderService.generateSecurityCode(orderId, vendorName);
			
			User user = new User();
			user = userService.findUserByVendorName(vendorName);
			Integer userType = user.getUserType();//获取用户类型:如果为测试用户则限定生成防伪码数量为10000
			if(userType == 2){
				Integer testSecurityCodeCount = user.getTestSecurityCodeCount();
				if(testSecurityCodeCount >= 10000){
					return ResultObject.createErrorInstance(-11, "测试用户生成二维码数量超过限定,请申请转化为正式用户!");
				}
			}
			
			
			int vendorId = orderService.findVendorIdByOrderId(orderId);
			String ScanTableName = "tb_anti_fake_" + vendorId;
			String tableName = orderService.findIfDbName(ScanTableName);
			if(tableName == null){
				orderService.createScanTable(vendorId);								
			}
			orderService.createOrderTable(orderId);	//生成二维码表

			result = orderService.createSecuritycode(list, orderId, vendorName, product);
			
			int updateOrderStatus = orderService.updateOrderStatus(orderId);
			
			
			level = Level.toLevel(Priority.INFO_INT, Level.DEBUG);
			loggerPri = LogManager
							.getLogger("com.yunma.dao.product.ProductOrderDao");
			loggerPri.setLevel(level);
 
		}catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			   return ResultObject.createErrorInstance(-28, "生成二维码失败！");
		}
		if (result <= 0) {
            return ResultObject.createErrorInstance(-15, "操作失败");
        } else {
            logger.info("生成二维码结束时间 : {}", DateUtils.getTimeString());
            
            return ResultObject.createErrorInstance(0, "操作成功");
        }
	
	}
	/**
	 * 分页显示订单信息
	 * @param vendorId
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/product/productInfoList.do")
	@ResponseBody
	public PageBean getProductOrderInfo(@RequestParam("vendorId") Integer vendorId,
			@RequestParam("connecTracingAndSecurty") Integer connecTracingAndSecurty,PageBean pageBean) {

		return orderService.getProductOrderInfo(pageBean, vendorId,connecTracingAndSecurty);
	}
	
	/**
	 * 分页显示订单生成红包信息
	 * @param vendorId
	 * @param envFlag "1:已生成红包 ;  0:未生成红包" 
	 * @param pageBean
	 * @param vo
	 * @return
	 */
	@RequestMapping("/GET/product/productEnvInfoList.do")
	@ResponseBody
	public PageBean getProductEnvOrderInfo(@RequestParam("vendorId") Integer vendorId,
			@RequestParam("envFlag") Integer envFlag,PageBean pageBean,ProductOrderVO vo) {

		return orderService.getOrderEnvInfo(pageBean,vendorId, envFlag);
	}
	
	/**
	 * 新增根据orderId 查询订单信息
	 */
	@RequestMapping("/GET/product/productOrderInfo.do")
	@ResponseBody
	public ProductOrderVO getSingleProductOrderInfo(@RequestParam("orderId") Integer orderId) {
		
		return orderService.getProductOrderInfoByOrderId(orderId);
	}
	/**
	 * 测试获取数据库名
	 * @param dbName
	 * @return
	 */
	@RequestMapping("/GET/product/testDBName.do")
	@ResponseBody
	public JSONObject getChargeName(@RequestParam("dbName") String dbName) {
		String dbName1 = orderService.findIfDbName(dbName);
		JSONObject result =new JSONObject();
		result.put("Name", dbName1);
		return result;
	}
	
}
