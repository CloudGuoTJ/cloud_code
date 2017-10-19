package com.yunma.controller.secutrityCode;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.BaseController;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.service.product.ProductOrderService;
import com.yunma.service.secutrityCode.SecurityCodeService;
import com.yunma.utils.PageBean;
import com.yunma.utils.WeChatConfig;


@Controller
public class SecurityCodeController extends BaseController{
	@Autowired
	SecurityCodeService securityCodeService;
	@Autowired
	ProductOrderService orderService;
/**
 * 分页显示二维码
 * @param orderId
 * @param pageBean
 * @param code
 * @return
 */
    @RequestMapping("/GET/securityCode/securityCodeList.do")
    @ResponseBody
	public PageBean getSecurityCodeInfo(@RequestParam("orderId") Integer orderId,PageBean pageBean,SecurityCode code) {

		return securityCodeService.getSecurityInfo(pageBean, code,orderId);
	}
 /**
  *  生成二维码前缀(可前端写,静态)  
  * @param orderId
  * @return
  */
    @RequestMapping("/GET/securityCode/securityCodeWebsidePrefix.do")
    @ResponseBody
	public JSONObject getSecurityCodePrefix(Integer orderId) {
    	JSONObject result = new JSONObject() ;
    	String url =("https:"+WeChatConfig.DOMAIN_NAME + WeChatConfig.PROJECT_NAME+"/s/").trim();
    	result.put("prefix", url);
		return result;
	}
    
    /**
     * 导出二维码表的TXT文件
     * @param response
     * @param orderId
     */
    @RequestMapping("/POST/securityCode/exportVendorSecurityCode.do")
    @ResponseBody
    public void exportBatchCode(HttpServletResponse response,
                                @RequestParam("orderId") Integer orderId) {
    	securityCodeService.exportBatchCodeTxt(response, orderId);
    }
    /**
     * 警告提示:导出二维码次数超过1次则可能在流通过程中出现重复
     * @param response
     * @param orderId
     */
    @RequestMapping("/GET/securityCode/securityCodeExportCountWarn.do")
    @ResponseBody
    public JSONObject exportCode(HttpServletResponse response,
                                @RequestParam("orderId") Integer orderId) {
    	JSONObject result = new JSONObject(); 
    	int count = securityCodeService.getDownloadCount(orderId);
    	if(count >= 1){
    		result.put("code", -1);
    		result.put("msg", "该批次二维码已导出过,再导出可能在流通过程中出现重复!");
    	}
    	if(count == 0){
    		result.put("code", 0);
    		result.put("msg", "该订单未导出二维码!请确认是否导出二维码!");
    	}
		return result;
    }
    
    

}
