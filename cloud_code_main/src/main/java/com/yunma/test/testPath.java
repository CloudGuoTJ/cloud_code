package com.yunma.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class testPath {
//	@Test 
//	public void testUrl(){
//		String url =
//					"redirect:"+"https://open.weixin.qq.com/connect/oauth2/authorize"+"?appid="+"wx4e8cf1716ea239ef" + "&redirect_uri=http%3A%2F%2F"+"ym-b.top"+"%2F"+"cloud_code"+"%2Fwx%2FwxCallback.do?source="
//			+"0003020380Z1e5"+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//		System.out.println(url);
//	}
//	@Test
//	public void testFile() throws IOException{
//		
//		String classesPath = getClass().getResource("/").getPath(); //获取到webapps/classes路径
//        String path = classesPath.substring(0, classesPath.lastIndexOf("webapps") + 7);
//        String content = "/cloud_code/wx/";
//        String realPath = path + content;
//        
//		File fp=new File(realPath+"order.html");
//        String str="ABCDE";
//       PrintWriter pfp= new PrintWriter(fp);
//       pfp.print(str);
//       pfp.close();
//      System.out.println(1);
//		
//	}
//	@Test
//	public void testPath(){
//		 String classesPath = getClass().getResource("/").getPath(); //获取到webapps/classes路径
//	        String path = classesPath.substring(0, classesPath.lastIndexOf("webapps") + 7);
//         String content = "/cloud_code/wx/";
//		System.out.println(classesPath);
//		
//	} 
	
//
//    public JSONObject uploadWxCert(MultipartFile cert, Integer vendorId) {
//        JSONObject jsonObject = new JSONObject();
//
//        if (cert != null && !cert.isEmpty()) { //证书不为空就上传证书
//            String certUrl = null;
//            String pathTest = getClass().getResource("/").getPath();
//            String path = pathTest.substring(0, pathTest.lastIndexOf("webapps") + 7);
//            // 证书存储文件夹
//            String content = "/imageContent/certContent";
//            String realPath = path + content;
//            File pathFile = new File(realPath);
//            // 如果文件夹不存在，则创建文件夹
//            if (!pathFile.exists()) {
//                pathFile.mkdirs();
//            }
//            // 获取证书的文件名
//            String fileName = cert.getOriginalFilename();
//            String extensionName = fileName
//                    .substring(fileName.lastIndexOf(".") + 1);
//            //判断证书的文件格式是否为p12,文件格式为p12才是发微信红包所需要的证书
//            if (!extensionName.equals("p12")) {
//                jsonObject.put("errorCode", -100);
//                jsonObject.put("msg", "证书格式不正确！");
//                return jsonObject;
//            }
//            // 新的证书文件名 = tomcat根目录 +　/wxCertContent　＋　厂商id + 文件名
//            certUrl = realPath + "/" + vendorId + "-" + fileName;
//            File file = new File(certUrl);
//            if (file.exists()) {
//                //如果证书已经存在了，就删除原来的证书
//                file.delete();
//            }
//            try {
//                cert.transferTo(file);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("上传证书时出错：" + e.getMessage(), e);
//                jsonObject.put("errorCode", -100);
//                jsonObject.put("msg", "上传证书失败！");
//                return jsonObject;
//            }
//            jsonObject.put("errorCode", 0);
//            jsonObject.put("certUrl", certUrl);
//            return jsonObject;
//        }
//
//        jsonObject.put("errorCode", -100);
//        jsonObject.put("msg", "证书不存在！");
//        return jsonObject;
//    }

}
