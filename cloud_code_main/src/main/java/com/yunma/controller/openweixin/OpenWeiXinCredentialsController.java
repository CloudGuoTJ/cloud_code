package com.yunma.controller.openweixin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yunma.service.openweixin.OpenWeiXinService;
import com.yunma.utils.WeChatConfig;

@Controller
@RequestMapping("/openweixincredentials")
public class OpenWeiXinCredentialsController {
	
	@Resource
	private OpenWeiXinService openWeiXinService;
	
	/**
	 * 上传证书
	 * @param vendorId
	 * @param credentials
	 * @return
	 */
	@RequestMapping("/upload.do")
	@ResponseBody
	public JSONObject uploadCredentials(
			Integer vendorId,
			@RequestParam(value="credentials", required=false) CommonsMultipartFile[] credentials
			) {
		JSONObject result = new JSONObject();
		
		if (credentials != null && credentials.length > 0) {
			String classesPath;
			try {
				classesPath = getClass().getResource("/").toURI().getPath();//获取到webapps/classes路径
				String path = classesPath.substring(0, classesPath.lastIndexOf("webapps") + 7);
				String realPath = path + WeChatConfig.USER_CREDENTIALS_LOCATION+"/"+vendorId;
				
				File pathFile = new File(realPath);
				// 如果文件夹不存在，则创建文件夹
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				
				for (CommonsMultipartFile commonsMultipartFile : credentials) {
					File file = new File(realPath+"/"+commonsMultipartFile.getOriginalFilename());
					commonsMultipartFile.transferTo(file);
				}
				
				int temp = openWeiXinService.updateCredentialsAddress(realPath+"/apiclient_cert.p12",vendorId);
				if (temp > 0) {
 		        	result.put("statuscode", 1);
 					result.put("msg", "上传成功");
				} else {
 		        	result.put("statuscode", -1);
 					result.put("msg", "上传失败");
				}
				return result;
		        
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
