package com.yunma.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.common.util.CommonConstants;
import com.common.util.ImageUtils;

public class ImageUploladUtil {

	//图片宽度
    private final int BATCH_WIDTH = 60;
    
    private final int BATCH_HEIGHT = 60;
    // 图片大小限制
    private final long SIZE = 1024 * 1024;
    //上传图片的后缀名限制
    private final String IMAGE_SUFFIX = "jpg--png--bmp--jpeg";
    
    /**
     * 压缩图片大小 并重调固定宽高
     * @param picture
     * @param result
     * @return
     */
    public JSONObject uploadBatchImage(MultipartFile picture,JSONObject result) {
    	
    	try {
    		result = dealImage(picture, SIZE,result);
    		if ((Integer)result.get("statuscode") < 0) {
    			result.put("uploadState", -1);
    			return result;
    		}
    		
            int[] sizeInfo = ImageUtils.getSizeInfo(picture.getInputStream());
            /*int width = sizeInfo[0];
            int height = sizeInfo[1];
            int newHeight = BATCH_WIDTH * height / width;*/
            
            String targetPath = result.get("imgPath").toString();
            //压缩图片
            ImageUtils.resize(picture.getInputStream(), new FileOutputStream(targetPath), BATCH_WIDTH, BATCH_HEIGHT);

            String imgUrl = targetPath.substring(targetPath.lastIndexOf(CommonConstants.IMAGE_CONTENT));//外部用来访问图片的地址
            result.clear();
			result.put("imgUrl", imgUrl);
			result.put("uploadState", 1);
            return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	//dealImage(picture, 10 * 1024 * 1024);
    	return null;
    }
    
    /**
     * 上传图片
     * @param picture
     * @param result
     * @return
     */
    public JSONObject uploadImage(MultipartFile picture,JSONObject result) {
    	
    	try {
    		result = dealImage(picture, SIZE,result);
    		if ((Integer)result.get("statuscode") < 0) {
    			result.put("uploadState", -1);
    			return result;
    		}
    		
            int[] sizeInfo = ImageUtils.getSizeInfo(picture.getInputStream());
            int width = sizeInfo[0];
            int height = sizeInfo[1];
            /*
            int newHeight = BATCH_WIDTH * height / width;*/
            
            String targetPath = result.get("imgPath").toString();
            //压缩图片
            ImageUtils.resize(picture.getInputStream(), new FileOutputStream(targetPath), width, height);

            String imgUrl = targetPath.substring(targetPath.lastIndexOf(CommonConstants.IMAGE_CONTENT));//外部用来访问图片的地址
            result.clear();
			result.put("imgUrl", imgUrl);
			result.put("width", width);
			result.put("height", height);
			result.put("fileName", picture.getOriginalFilename());
			result.put("uploadState", 1);
            return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	//dealImage(picture, 10 * 1024 * 1024);
    	return null;
    }

	private JSONObject dealImage(MultipartFile image, long size,JSONObject result) {
		if (image == null || image.isEmpty()) {
			result.put("statuscode", -3);
			result.put("msg", "图片为空");
			return result;
		}

        if (image.getSize() > size) {
			result.put("statuscode", -4);
			result.put("msg", "图片大小超过限制");
			return result;
        }
        
        String ext = FilenameUtils.getExtension(image.getOriginalFilename()).toLowerCase();
        if (!IMAGE_SUFFIX.contains(ext)) {
			result.put("statuscode", -5);
			result.put("msg", "图片类型错误");
			return result;
        }
        
        String classesPath;
		try {
			classesPath = getClass().getResource("/").toURI().getPath();//获取到webapps/classes路径
			String path = classesPath.substring(0, classesPath.lastIndexOf("webapps") + 7);
	        String realPath = path + CommonConstants.IMAGE_CONTENT;
	        
	        File pathFile = new File(realPath);
	        // 如果文件夹不存在，则创建文件夹
	        if (!pathFile.exists()) {
	            pathFile.mkdirs();
	        }
	        int rand = new Random().nextInt(100000);
	        // 图片的存储路径为：tomcat路径 +　时间戳 + 随机数 +　文件扩展名
	        String imgPath = realPath + "/" + String.valueOf(System.currentTimeMillis())
	                + rand + "." + ext;
	        
	        File file = new File(imgPath);
	        if (file.exists()) {
	        	result.put("statuscode", -6);
				result.put("msg", "出现异常，请重试");
				return result;
	        } else {
	        	result.put("statuscode", 0);
				result.put("imgPath", imgPath);
				return result;
	        }
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
          return result;
	}
}
