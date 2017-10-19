package com.yunma.utils.weChat;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.yunma.controller.wechat.WechatController;


public class Global
{
	
	private static Logger log = LoggerFactory.getLogger(WechatController.class);

	private final static DecimalFormat nf = new DecimalFormat("###,##0.00");
	
	public static String parseStrSql(Object o)
	{
		if (o == null)
			return "";
		else
			return o.toString().replace("'", "’").trim();
	}
	
	public static String parseString(Object o)
	{
		if (o == null)
			return "";
		else
			return o.toString().trim();
	}

	
	public static double parseDouble(Object o)
	{
		if (o == null)
			return 0;
		else if(o.toString().trim().equals(""))
			return 0;
		else
			return Double.parseDouble(o.toString().trim());

	}
	
	public static Double parseDoubleO(Object o)
	{
		if (o == null)
			return 0.0;
		else if(o.toString().trim().equals(""))
			return 0.0;
		else
			return Double.valueOf((o.toString().trim()));

	}
	
		
	
	public static int parseInteger(Object o)
	{
		if (o == null)
			return 0;
		else if(o.toString().trim().equals(""))
			return 0;
		else
			return Integer.parseInt(o.toString().trim());

	}
	
	public static long parseLong(Object o)
	{
		if (o == null)
			return 0;
		else if(o.toString().trim().equals(""))
			return 0;
		else
			return Long.parseLong(o.toString().trim());
	}
	
	public static String argValue(Object o,int i)
	{
		if(o==null)
		{
			return null;
		}
		else
		{
			String[] args=(String[])o;
			if(args!=null && args.length>i)
			{
				return args[i];
			}
			else
			{
				return null;
			}
		}
	}
	
	
	//格式化日期（yyyyMMdd）
	public static String dateFmt(String strDate)
	{
		if(strDate!=null)
		{
			if(strDate.length()==8)
			{
				String rtn=strDate.substring(0,4)+"-"+strDate.substring(4,6)+"-"+strDate.substring(6,8);
				return rtn;
			}
			else if(strDate.equals("0"))
			{
				return "";
			}
			else
			{
				return strDate;
			}
		}
		else
		{
			return "";
		}
	}

	public static double fmtNum(double num)
	{
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"##########.00");
		double rtn = Double.parseDouble(df.format(num));
		return rtn;
	}
	public static double fmtNum3(double num){
		DecimalFormat df = new DecimalFormat("##########.000");
		double rtn = Double.parseDouble(df.format(num));
		return rtn;
	}
	
	public static String formatToMoney(Double d)
	{
		if(d==0)
		{
			return "0";
		}
		
		DecimalFormat df = new DecimalFormat("#########0.00");
        try
        {
            return df.format(d);
        } 
        catch (Exception e) 
        {
            return String.valueOf(d);
        }
	}
	
	public static Long formatDate(String frmDate)
	{
		Long rtn=new Long(0);
		String []strReplease={"/","-",".",","};
		StringBuffer str1=new StringBuffer();
		String strDate;
		try
		{
			for(int i=0;i<strReplease.length;i++)
			{
				if(frmDate.contains(strReplease[i]))
				{
					String  [] split1 =frmDate.split(strReplease[i]);
					for(int j=0;j<split1.length;j++)
					{
						str1.append(split1[j]);
					}
					frmDate=str1.toString();
				}
			}
			System.out.println("frmDate"+frmDate);
			strDate=frmDate;
			if(strDate.length()==8)
			{
				rtn=Long.valueOf(strDate);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return rtn;
	}

	/**
	 * ip校验
	 * 
	 * @param s
	 * @return
	 */
	public static Boolean isIpAddress(String s)
	{
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientAddress(HttpServletRequest request)
	{
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && isIpAddress(address))
		{
			return address;
		}
		return request.getRemoteAddr();
	}
	
	public static String toUtf8String(String src)
    {
        byte[] b = src.getBytes();
        char[] c = new char[b.length];
        for(int i=0;i<b.length;i++)
        {
            c[i] = (char)(b[i]&0x00FF);
        }
        return new String(c);
    }
	
	public static String filtStr(String str)
	{
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	    Matcher m = p.matcher(str);
	    String strRtn = m.replaceAll("");
	    return strRtn;
	}
	
	public static String filtStr(String str1,String replacement)
	{
//		Pattern p = Pattern.compile("\r\n");
//	    Matcher m = p.matcher(replacement);
//	    String strRtn = m.replaceAll(replacement);
	    String strRtn=str1.replaceAll("\r\n", replacement);
	    return strRtn;
	}
	
	public static String arryToString(String[] arr)
	{
		if(arr==null)
		{
			return "";
		}
		StringBuffer bf=new StringBuffer();
		for(int i=0;i<arr.length;i++)
		{
			arr[i]=filtStr(arr[i]);
			if(bf==null || bf.length()==0)
			{
				bf.append("'").append(arr[i].trim()).append("'");
			}
			else
			{
				bf.append(",").append("'").append(arr[i].trim()).append("'");
			}	
		}
		
		return bf.toString();
	}
	
	public static String strToStr(String str,String ch)
	{
		String[] arr=str.split(ch);
		arr=array_unique(arr); //去除數組中重復的數據
		return arryToString(arr);
	}

    //去除數組中重復的數據,和空的數據
    public static String[] array_unique(String[] a)
	{
		//array_unique
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < a.length; i++)
		{
			if (a[i]!=null && !a[i].trim().equals("") && !list.contains(a[i].trim()))
			{
				list.add(a[i].trim());
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
    public static String formatStr (String str1){
    	String rtn="";
    	rtn=str1==null?"":str1.trim();
    	return rtn;
    }
    public static String copyPhoto(String newFilePath,String oldFilePath,String photoBuffer){
    	String fileName = oldFilePath.substring(oldFilePath.lastIndexOf("\\")+1);
    	
    	/*
    	File oldFile= new File(oldFilePath);
    	
    	File newFile = new File(newFilePath,fileName);
    	try {
			FileUtils.copyFile(oldFile, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 */
    	 if (photoBuffer !=""){ 
         BASE64Decoder decoder = new BASE64Decoder();
         try {
             // Base64解码
             byte[] bytes = decoder.decodeBuffer(photoBuffer);
             for (int i = 0; i < bytes.length; ++i) {
                 if (bytes[i] < 0) {// 调整异常数据
                     bytes[i] += 256;
                 }
             }
             // 生成jpeg图片
             OutputStream out = new FileOutputStream(newFilePath+"\\"+fileName);
             out.write(bytes);
             out.flush();
             out.close();
         } catch (Exception e) {
        	 e.printStackTrace();
         }
    	 }
    	return fileName;
    }
    
    public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void copyProperty(Object obj, Map map) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            try {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = findExitField((String) entry.getKey(),
                                            obj.getClass());
                if (name != null) {
                	if(entry.getValue()!=null)
                		BeanUtils.copyProperty(obj, name, entry.getValue());
                }
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * 查找存在的字段
     * @param name String
     * @param cls Class
     * @return String
     */
    public static String findExitField(String key, Class cls) {
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (key.equalsIgnoreCase(name))
                return name;
        }
        if (cls.getSuperclass() != null)
            return findExitField(key, cls.getSuperclass());
        else
            return null;
    }
    
    /**
     * 省对应注解
     */
    public static Map<String, String> setShenMap(){
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("HU_NAN", "湖南");
    	map.put("SI_CHUAN", "四川");
    	map.put("HE_NAN", "河南");
    	map.put("GUANG_XI", "广西");
    	map.put("GUANG_DONG", "广东");
    	map.put("HU_BEI", "湖北");
    	map.put("JIANG_XI", "江西");
    	map.put("GUI_ZHOU", "贵州");
    	map.put("CHONG_QING", "重庆");
    	map.put("AN_HUI", "安徽");
    	map.put("SHAN3_XI", "陕西");
    	map.put("HAI_NAN", "海南");
    	map.put("YUN_NAN", "云南");
    	map.put("XI_ZANG", "西藏");
    	map.put("GAN_SU", "甘肃");
    	map.put("QING_HAI", "青海");
    	map.put("NING_XIA", "宁夏");
    	map.put("XIN_JIANG", "新疆");
    	map.put("TAI_WAN", "台湾");
    	map.put("XIANG_GANG", "香港");
    	map.put("AO_MEI", "澳门");
    	map.put("BEI_JING", "北京");
    	map.put("TIAN_JING", "天津");
    	map.put("HE_BEI", "河北");
    	map.put("SHAN1_XI", "山西");
    	map.put("NEI_MENG_GU", "内蒙古");
    	map.put("LIAO_NING", "辽宁");
    	map.put("JI_LIN", "吉林");
    	map.put("HEI_LONG_JIANG", "黑龙江");
    	map.put("SHANG_HAI", "上海");
    	map.put("JIANG_SU", "江苏");
    	map.put("ZHE_JIANG", "浙江");
    	map.put("FU_JIAN", "福建");
    	map.put("SHAN_DONG", "山东");
    	return map;
    }
    public static Map<String, String> setShenColorMap(){
    	Map<String, String> map=new HashMap<String, String>();
    	map.put("HU_NAN", "1160B8");
    	map.put("SI_CHUAN", "F2AC0C");
    	map.put("HE_NAN", "BF0000");
    	map.put("GUANG_XI", "00247C");
    	map.put("GUANG_DONG", "118900");
    	map.put("HU_BEI", "E95D0F");
    	map.put("JIANG_XI", "123456");
    	map.put("GUI_ZHOU", "A12B13");
    	map.put("CHONG_QING", "D23A32");
    	map.put("AN_HUI", "C44D55");
    	map.put("SHAN3_XI", "D55E66");
    	map.put("HAI_NAN", "AADDBB");
    	map.put("YUN_NAN", "CCAADD");
    	map.put("XI_ZANG", "123ADE");
    	map.put("GAN_SU", "FE3211");
    	map.put("QING_HAI", "1160B8");
    	map.put("NING_XIA", "F2AC44");
    	map.put("XIN_JIANG", "BF0005");
    	map.put("TAI_WAN", "00247C");
    	map.put("XIANG_GANG", "1189CC");
    	map.put("AO_MEI", "E95DEA");
    	map.put("BEI_JING", "AAAC0C");
    	map.put("TIAN_JING", "BFDA00");
    	map.put("HE_BEI", "78247C");
    	map.put("SHAN1_XI", "A718900");
    	map.put("NEI_MENG_GU", "E12D0F");
    	map.put("LIAO_NING", "12EC56");
    	map.put("JI_LIN", "A12DCA");
    	map.put("HEI_LONG_JIANG", "D2DC32");
    	map.put("SHANG_HAI", "C47355");
    	map.put("JIANG_SU", "F29044");
    	map.put("ZHE_JIANG", "BFAC15");
    	map.put("FU_JIAN", "1EC47C");
    	map.put("SHAN_DONG", "11ADFC");
    	return map;
    }
	public static String getSelected(String selectName,String value) 
	{
		String strRtn="<SCRIPT language='JavaScript'> ";
		strRtn+=" var selectName = "+selectName+";";
	 	strRtn+=" for(var i=0;i<selectName.options.length;i++)";
	 	strRtn+=" {";     
	 	strRtn+=" if(selectName.options[i].value=='"+value+"')";   
	    strRtn+=" selectName.options[i].selected=true;"; 
	    strRtn+=" }";
	    strRtn+=" </SCRIPT>";
		return strRtn;
	}
	/**
	 * 改变图片大小 
	 * @param filePath
	 */
	public static void changePhotoSize(String filePath){
		File srcfile = new File(filePath);      
        if (!srcfile.exists()) {      
            return;      
        }   
        //载入图片文件   
		try {
			Image src = javax.imageio.ImageIO.read(srcfile);
			int w0 = src.getWidth(null);    //得到源图宽   
			int h0 = src.getHeight(null);   //得到源图长   
			if(w0>1000){
				w0=(int)w0/4;
				h0=(int)h0/4;
			}else{
				w0=(int)w0/2;
				h0=(int)h0/2;
			}
			BufferedImage tag= new BufferedImage(w0, h0, BufferedImage.TYPE_INT_RGB);      
			//保存文件   
			//绘制缩小后的图   
			tag.getGraphics().drawImage(src.getScaledInstance(w0, h0,  Image.SCALE_SMOOTH), 0, 0,  null);    
			
			//输出到文件流   
			FileOutputStream out = new FileOutputStream(filePath);   
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
			//近JPEG编码    
			encoder.encode(tag);   
			out.close();   
		} catch (IOException e) {
			log.info(e.toString());
		}
	}
	public static void changePhotoSizeV1(String filePath){
		File srcfile = new File(filePath);      
		if (!srcfile.exists()) {      
			return;      
		}   
		//载入图片文件   
		try {
			Image src = javax.imageio.ImageIO.read(srcfile);
			int w0 = src.getWidth(null);    //得到源图宽   
			int h0 = src.getHeight(null);   //得到源图长   
			if(w0>1500){
				w0=(int)w0/2;
				h0=(int)h0/2;
			}else if(w0>1000){
				w0=(int)(w0/1.5);
				h0=(int)(h0/1.5);
			}
			BufferedImage tag= new BufferedImage(w0, h0, BufferedImage.TYPE_INT_RGB);      
			//保存文件   
			//绘制缩小后的图   
			tag.getGraphics().drawImage(src.getScaledInstance(w0, h0,  Image.SCALE_SMOOTH), 0, 0,  null);    
			
			//输出到文件流   
			FileOutputStream out = new FileOutputStream(filePath);   
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
			//近JPEG编码    
			encoder.encode(tag);   
			out.close();   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	//产生决定书校验位
	public static String genJdsxyw(String jdsh){
		if(jdsh.length()<8){
			return "";
		}
		String jdsh8=jdsh.substring(7);
		byte[] bb=jdsh8.getBytes();
		int sum=0;
		for(byte b1:bb){
			char c=(char)b1;
			String s=String.valueOf(c);
			int i=Integer.parseInt(s);
			//System.out.println(i);
			sum+=i;
		}
		int mod=sum%8;
		return String.valueOf(mod);
	}
	public static int genRandom(int min,int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	//去除html标记
	public static String removeHtmlTag(String str){
		Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		String string = matcher.replaceAll("");
		return string;
	}
	
	//去除script标记
	public static String removeScriptTag(String str){
		str = StringUtils.lowerCase(str);
		Pattern pattern = Pattern.compile("<script.+?script>", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(str);
		String string = matcher.replaceAll("");
		return string;
	}
	
	/**
	 * 隐藏手机号码
	 * @param telephone
	 * @return
	 */
	public static String hiddenTel(String telephone){
		if (telephone == null)
			return "";
		String newTelphone="";
		int len=telephone.length();
		if(len>10){
			newTelphone=telephone.substring(0,3)+"****"+telephone.substring(len-4);
		}
		return newTelphone;
	}
	
	/**
	 * 获取区域市
	 * @param area
	 * @return
	 */
	public static String areaCity(String area){
		String city="";
		String area_2 = StringUtils.substring(area, 0, 2);
		if(area_2.equals("50")||area_2.equals("11")||area_2.equals("12")||area_2.equals("31")){		//50重庆，11北京 ，12 天津 31 上海
			//city=area_2+"0000";
			city=area;
			return city;
		}else{
			String area_4 = StringUtils.substring(area, 0, 4);
			city=area_4+"00";
			return city;
		}
	}
	
	public static String urlEncoder(String value){
		try {
			value=URLEncoder.encode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	public static String urlDecoder(String value){
		try {
			value=URLDecoder.decode(value,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static String randomNum(){
		double d = Math.random()*100000000;
		int i =(int)d*10;
		return String.valueOf(i);
	}
   
	public static void main(String[] args) {
    	String str="441906";
    	String area_2 = areaCity(str);
    	System.out.print(area_2);
    	
	}
}
