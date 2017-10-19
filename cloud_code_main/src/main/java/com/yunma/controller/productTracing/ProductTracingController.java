package com.yunma.controller.productTracing;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.tracing.ProductTracingCode;
import com.yunma.model.BoxCode;
import com.yunma.model.GroupCode;
import com.yunma.model.User;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.product.ProductService;
import com.yunma.service.tracing.ProductsTracingService;
import com.yunma.service.user.UserService;
import com.yunma.utils.DoubleUtil;
import com.yunma.utils.PageBean;
import com.yunma.utils.WeChatConfig;


@Controller
public class ProductTracingController extends BaseController{
	@Autowired
	private ProductsTracingService tracingService;
	@Autowired
	private ProductOrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	/**
	 * 生成溯源码
	 * @param orderId
	 * @param rowCount
	 * @return
	 */
	@RequestMapping("/POST/productTracing/createProductTracingCode.do")
	@ResponseBody
	public JSONObject createProductTracingCode(
			@RequestParam("orderId") Integer orderId,
			@RequestParam("rowCount") Integer rowCount,
			@RequestParam("boxCount") Integer boxCount)
		//	HttpServletRequest request)
	{
		
		Level level = null;
		Logger loggerPri = null;
		
		level = Level.toLevel(Priority.INFO_INT, Level.INFO);
		loggerPri = LogManager
				.getLogger("com.yunma.dao.tracing.ProductTracingDao");
		loggerPri.setLevel(level);
		
		
		logger.info("生成溯源码开始时间 : {}", DateUtils.getTimeString());
		JSONObject result = new JSONObject();
		int a;
		int checking = 0;
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		int count = order.getProductCount();
		Integer vendorId = order.getVendorId();		
		String vendorName = order.getVendorName();
		
		/**
		 * 测试用户数量限定
		 */
		User user = userService.findUserByVendorId(vendorId);
		Integer userType = user.getUserType();//获取用户类型:如果为测试用户则限定生成防伪码数量为10000
		if(userType == 2){
			Integer testTracingCodeCount = user.getTestTracingCodeCount();
			if(testTracingCodeCount >= 10000 ){
				
				result.put("errorCode", -5);
				result.put("msg", "测试用户生成二维码数量超过试用上限,请申请转化为正式用户!");
				return result;
			}
			else
			{
				if(count >= 10000) {
					count = 10000;
					checking = userService.updateTestUserCreatingTracingCodeCount(vendorId,count);
				}
				if(count + testTracingCodeCount >= 10000){
					count = 10000 - testTracingCodeCount;
					checking += userService.updateTestUserCreatingTracingCodeCount(vendorId,10000);
				}
				if(count + testTracingCodeCount < 10000){
					checking += userService.updateTestUserCreatingTracingCodeCount(vendorId,count + testTracingCodeCount);
				}
				if(checking < 0){
					result.put("errorCode", -7);
					result.put("msg", "测试用户生成二维码异常,请联系管理员!");
					return result;
				}
			}
		}
		/**
		 * 生成状态判断
		 */
		String tableCodeTracingCodeName = "tb_product_tracing_code_"+orderId;
		String tableName4 = tracingService.getTBName(tableCodeTracingCodeName);
		if(tableName4 != null){
			result.put("errorCode", -2);
			result.put("msg", "该订单已生成过溯源码请重新选择!");
			return result;
		}
		if(count < rowCount || boxCount > count){
			result.put("error", -1);
			result.put("msg", "输入数量超过订单内产品总量,请重新输入!");
			return result;
			
		}else{
			//String vendorName = request.getParameter("vendorName");
			/**
			 * 使用事务生成二维码
			 */
			try{
				boolean isRepeat = CommonUtils.isRepeat("GenerateProductTracingCode_" + orderId, CommonConstants.TIME_MINUTE_1);
				 if (isRepeat) {
					 
					 result.put("errorCode", -3);
					 result.put("msg", "生成溯源码重复提交！");		           
		            }
	 
				/**
				 * 获取order信息和产品信息
				 */
				 
		
				Product product = productService.findProductByProductId(order.getProductId());

				List<ProductTracingCode> list =tracingService.vendorGenerateProductTracingCode(orderId, vendorId, rowCount, boxCount);
				
				List<BoxCode> boxCodelist = tracingService.vendorGenerateProductTracingBoxCode(orderId, vendorId, rowCount, boxCount);
				
				
				List<GroupCode> groupCodelist = tracingService.vendorGenerateProductTracingGroupCode(orderId, vendorId, rowCount, boxCount);
				
					//生成表判断,以商家为单位的表不需要重建
				String tableName = null;
								
				String ScanTableName = "tb_product_tracing_agent_scan_" + vendorId;
				String tableName0 = tracingService.getTBName(ScanTableName);
				if(tableName0 == null){
					tracingService.createScanTable(vendorId);												
				}
				
//				String ScanTableName1 = "tb_product_tracing_customer_scan_" + vendorId;
//				String tableName1 = tracingService.getTBName(ScanTableName1);
//				if(tableName1 == null){
//					tracingService.createCustomerTracingScanTable(vendorId);								
//					
//				}
				/**
				 * 
				 * 以订单为单位的表必须重建
				 * 
				 */
				String tableCodeNameCode = "tb_product_tracing_box_code_"+orderId;
				String tableName2 = tracingService.getTBName(tableCodeNameCode);
				if(tableName2 != null){
					
					tableName = tableName2;
					tracingService.dropTable(tableName);
					tracingService.createBoxCodeTable(orderId);
				}else{
					tracingService.createBoxCodeTable(orderId);

				}
				String tableCodeGroupName = "tb_product_tracing_group_code_"+orderId;
				String tableName3= tracingService.getTBName(tableCodeGroupName);
				if(tableName3 != null){
					
					tableName = tableName3;
					tracingService.dropTable(tableName);
					tracingService.createGroupCodeTable(orderId);
				}else{
					tracingService.createGroupCodeTable(orderId);
					
				}
		
				tracingService.createOrderTracingTable(orderId);
				
				a = tracingService.createProductTracingCode(list, orderId, vendorName, product);
				int b = tracingService.insertBoxCodeInfo(boxCodelist, orderId, vendorName, product);
				int c = tracingService.insertGroupCodeInfo(groupCodelist, orderId, vendorName, product);
		
				int updateOrderTracingStatus = tracingService.updateOrderStatus(orderId);
				
				level = Level.toLevel(Priority.INFO_INT, Level.DEBUG);
				loggerPri = LogManager
						.getLogger("com.yunma.dao.tracing.ProductTracingDao");
				loggerPri.setLevel(level);
						
			}catch(Exception e)
			{
				logger.error(e.getMessage(), e);
				result.put("errorCode", -28);
				result.put("msg", "生成失败！");
	               return result;
			}
			
			if (a <= 0) {
				result.put("errorCode", -15);
				result.put("msg", "操作失败,请重试 ！");
				return result;
			} else {
				logger.info("生成溯源码结束时间 : {}", DateUtils.getTimeString());
				
				result.put("errorCode", 0);
				result.put("msg", "操作成功！");
				return result;
			}
		}
	
	}
	
	/**
	  *  生成二维码前缀(可前端写,静态)  
	  * @param orderId
	  * @return
	  */
	    @RequestMapping("/POST/productTracing/createProductTracingCodePrefix.do")
	    @ResponseBody
		public JSONObject getSecurityCodePrefix(Integer orderId) {
	    	JSONObject result = new JSONObject() ;
	    	String url =("https:"+WeChatConfig.DOMAIN_NAME + WeChatConfig.PROJECT_NAME+"/t/").trim();
	    	result.put("prefix", url);
			return result;
		}
	    
	    /**
	     * 导出二维码表的TXT文件
	     * @param response
	     * @param orderId
	     */
	    @RequestMapping("/POST/securityCode/exportProductTracingCode.do")
	    @ResponseBody
	    public void exportTracingBatchCode(HttpServletResponse response,
	                                @RequestParam("orderId") Integer orderId) {
	    	tracingService.exportTracingBatchCodeToTXT(response, orderId);
	    }
	
	/**
	 * 分页显示溯源码信息
	 * @param orderId
	 * @param pageBean
	 * @param code
	 * @return
	 */
	    @RequestMapping("/GET/productTracingcode/tracingCodeList.do")
	    @ResponseBody
		public PageBean getSecurityCodeInfo(@RequestParam("orderId") Integer orderId,PageBean pageBean) {

			return tracingService.getProductTracingCodeInfo(pageBean, orderId);
	}

	/**
	 * 提示用户分箱信息
	 * 
	 * @param orderId
	 * @param rowCount
	 * @return
	 */
	@RequestMapping("/GET/productTracingcode/getCodeDivideCount.do")
	@ResponseBody
	public JSONObject getCodeDivideCount(
			@RequestParam("orderId") Integer orderId, Integer rowCount) {
		JSONObject result = new JSONObject();
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		int productCount = order.getProductCount();
		int rowNum = 0;
		int lastRowCount = 0;
		rowNum = (int) Math.ceil(DoubleUtil.divide((double) productCount,
				(double) rowCount, 2));

		if ((productCount % rowCount) != 0) {
			lastRowCount = productCount - (rowCount * (rowNum - 1));

		} else {
			lastRowCount = rowCount;
		}

		result.put("rowNum", rowNum);
		result.put("lastRowCount", lastRowCount);

		return result;
	}

	/**
	 * 分页显示溯源码箱码信息
	 * 
	 * @param orderId
	 * @param pageBean
	 * @param code
	 * @return
	 */
	@RequestMapping("/GET/productTracingBoxcode/tracingCodeList.do")
	@ResponseBody
	public PageBean getTracingcodeInfo(
			@RequestParam("orderId") Integer orderId, PageBean pageBean) {

		return tracingService.getProductTracingBoxCodeInfo(pageBean, orderId);
	}

	/**
	 * 分页显示溯源码条码信息
	 * 
	 * @param orderId
	 * @param pageBean
	 * @param code
	 * @return
	 */
	@RequestMapping("/GET/productTracingGroupcode/tracingCodeList.do")
	@ResponseBody
	public PageBean getTracingGroupcodeInfo(
			@RequestParam("orderId") Integer orderId, PageBean pageBean) {

		return tracingService.getProductTracingGroupCodeInfo(pageBean, orderId);
	}
	
	/**
	 * 
	 *设置高级溯源模式:
	 *该模式下产品严格遵守 同分支从高级到底级 分销模式,防止区域代理 窜货现象出现
	 * @param orderId
	 * @return
	 */
	
	@RequestMapping("/GET/productTracingGroupcode/upperTracingDegree.do")
	@ResponseBody
	public JSONObject upperTracingDegree(
			@RequestParam("orderId") Integer orderId) {
		JSONObject result = new JSONObject(); 
		//获取对应订单信息
		ProductOrder order = orderService.findOrderByProductOrderId(orderId);
		
		Integer codeFlag = order.getTracingFlag();
		
		Integer nativeDegree = order.getTracingHigherDegreeControll();
		
		if(nativeDegree == 2 || codeFlag == 2){
			result.put("errorCode", -1);
			result.put("msg", "溯源码已生成,或已设置为高级溯源模式,不能再更改!");
			return result;
		}
		//设置溯源等级
		int a = tracingService.exchangeTracingDegree(orderId);
		
		if(a < 0){
			result.put("errorCode", -15);
			result.put("msg", "未升级成功请稍后重试!");
			return result;
		}
		result.put("errorCode", 0);
		result.put("msg", "已成功设置溯源等级,可以生成对应溯源码并使用了!");
		
		return result;

		
	}
	
	
	
	
	
	
}
