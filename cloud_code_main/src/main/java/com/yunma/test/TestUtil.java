package com.yunma.test;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import com.common.util.CommonUtils;
import com.common.util.Radix;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.model.WxConfig;


public class TestUtil {
	
	@Test
	public void testSB(){
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE `tb_product_tracing_code_"
				+ 100
				+ "`("
				+ "`productTracingCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID',"
				+ "`orderId` bigint(20) NOT NULL COMMENT '订单ID',"
				+ "`productId` bigint(32) NOT NULL COMMENT '产品ID',"
				+ "`vendorId` bigint(32) NOT NULL COMMENT '商家ID',"
				+ "`productName` varchar(64) NOT NULL COMMENT '产品名',"
				+ "`codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源',"
				+ "`codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成',"
				+ "`productTracingCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '溯源码全码',"
				+ "`codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关',"
				+ "`createRowNum` varchar(32) NOT NULL COMMENT '分组生成的组号，单次生成不超过rowCount的，组号为1',"
				+ "`rowCount` bigint(32) NOT NULL COMMENT '单组数量,用户指定,必须满足规则:0 < rowCount <= 1000 <= orderCount <= productCount',"
				+ "`createDate` datetime NOT NULL COMMENT '生成日期',"
				+ " PRIMARY KEY (`productTracingCodeId`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='溯源码表';");
	


	
			System.out.println(sb.toString());
	}
	
	
	@Test
	public void getVendorId() throws UnsupportedEncodingException{
		String vendorCode ="%E7%9B%92%E5%AD%90%E5%85%88%E7%94%9F%E2%84%A1%C2%B9%E2%81%B7%E2%81%B6%C2%B2%E2%81%B0%E2%81%B4%E2%81%B7%E2%81%B4%E2%81%B0%E2%81%B7%C2%B2";
		String name = URLDecoder.decode(vendorCode, "utf-8"); //需要取出昵称时使用对称解码获取
		System.out.println(name);
		int vendorId = Radix.convert62To10(vendorCode);
		System.out.println(vendorId);
	}
	
//	@Test
//	public void testFromat(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		List<String> weekDate = new ArrayList<String>();
//		Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
//
//		for(int i=1; i <= 7; i++){
////			System.out.println(i);
//				calendar1.add(Calendar.DATE, -1 );
//				String times = sdf.format(calendar1.getTime());
//				String dateName ="date" + i;
//				dateName = times;
//				weekDate.add(dateName);
//				}
		
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		List<String> weekDate = new ArrayList<String>();
//		Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
//		for(int i=0; i < 7; i++){
//		System.out.println(i);
//			calendar1.add(Calendar.DATE, -1 );
//			String times = sdf.format(calendar1.getTime());
//			weekDate.add(times);
//			}
//		Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
//		calendar1.add(Calendar.DATE, -7 );
//
//		Calendar calendar2 = Calendar.getInstance();// 日历对象 结束时间
//		calendar2.add(Calendar.DATE, 0);

//		String stratTime = sdf.format(calendar1.getTime());
//		String endTime = sdf.format(calendar2.getTime());

		// calendar.add(Calendar.MONTH, -1);
//		System.out.println(weekDate);
////		System.out.println(endTime);
//
//	}
//	@Test
//	public void test(){
//		List<Integer> totalOrderId = new ArrayList<Integer>();
//		totalOrderId.add(21);
////		totalOrderId.add(30);
////		totalOrderId.add(35);
////		totalOrderId.add(36);
////		totalOrderId.add(40);
////		totalOrderId.add(50);
////		totalOrderId.add(55);
////		totalOrderId.add(56);
////		totalOrderId.add(78);
//		System.out.println(totalOrderId);
//		int sum =0;
//		for(int i = 0;i < totalOrderId.size();i++){
//			
//			 sum =sum+ totalOrderId.get(i);
//
//		}
//	
//		 System.out.println(sum);
//		
//	}
//		
//		
//		try{
//			int insertSqlCount = 0;
//			for (int j = 0; j <= list.size(); j++) {
//				StringBuffer sql = new StringBuffer();
//				sql.append("insert into `"
//						+ "tb_security_code_"
//						+ orderId
//						+ "`"
//						+ "(orderId,productId,productName,"
//						+ "securityCode,codeTailTag,createRowNum,codeFlag,codePrefix,"
//						+ "createDate) values ");					
//				
//				for (SecurityCode code1 : list) {
//					Integer codeFlag = 1;
//					sql.append("(" + code1.getOrderId() + ",");
//					sql.append(productId + ",");
//					sql.append("'" + code1.getProductName() + "',");
//					sql.append("'" + code1.getSecurityCode() + "',");
//					sql.append("'" + codeTailTag + "',");
//					sql.append(code1.getCreateRowNum() + ",");
//					sql.append(codeFlag + ",");
//					sql.append("'" + code1.getCodePrefix() + "',");
//					sql.append("now()),");
//				}
//				insertSqlCount += productOrderDao.createSecurityCode(sql
//						.substring(0, sql.length() - 1));
//			
//		}
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//	}finally{
//		
//		
//	}
//	}
//	
//	@Test
//	public void testSecurityCode(){
//
////			ProductOrder order = productOrderDao.findOrderByProductOrderId(orderId);// 获取产品订单
//			int productCount = 100000;
//			// System.out.println("order:"+order+ "orderId:"+orderId +"vendorName:"+
//			// vendorName);
//
//			String i = Integer.toString(productCount);// 追加定长的字符串根据订单的数量来
//			int productId = 151;// 拿到产品id
//			int vendorId =110;
//			/**
//			 * 先将需要附码的商品分组
//			 */
//			int orderId = 1515;
//			BitSet bitSet = new BitSet(62 * 62 * 62);// 每组bitSet的最大数量
//			/**
//			 * 处理生成二维码数量低无法生成问题
//			 */
//			int rowCount;
//			if ((int) Math.ceil((productCount / (62 * 62))) == 0
//					|| (int) Math.ceil((productCount / (62 * 62))) == 1) {
//				rowCount = 1;
//			} else {
//				rowCount = (int) Math.ceil((productCount / (62 * 62)))+1;
//			}
//
//			int len = i.length() - 1;// 追加tailTag的位数
//			int rowNum = 1;// 设置组编号并初始化
//			/**
//			 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
//			 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//			 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//			 */
//			List<SecurityCode> list = new ArrayList<SecurityCode>();
//			/**
//			 * 外层循环按照组数递增
//			 */
//			String codeTailTag = null;
//			String codePrefix = null;
//			int productCountNum = 3844;// 默认每组使用base62 低位总数去重
//			String productName = "kupaoba";
//			String orderCode = Radix.convert10To62(orderId, 4);
//			String vendorCode = Radix.convert10To62(vendorId, 2);
//			String productCode = Radix.convert10To62(productId, 2);
////			System.out.println("orderId:   " + orderId + "   vendorId:" + vendorId
////					+ "   rowCount " + rowCount + "  productCount" + productCount
////					+ "  len" + len);
//
//			for (int a = 1; a <= rowCount; a++) {
//				rowNum = a;
//
//				/**
//				 * 分组控制方式
//				 */
//				if (productCountNum > productCount) {
//					productCountNum = productCount;
//				}
//				
//				if (productCountNum * a >= productCount) {
//					productCountNum = productCount - (productCountNum * (a - 1));
//				}
//				if (productCountNum * a < productCount) {
//					productCountNum = 3384;
//				}
//				
//				// System.out.println("step:" + 2);
//				try {
//					/**
//					 * 生成一组二维码
//					 */
//					LinkedList<String> codeList = CommonUtils.getRandomStrList(
//							productCountNum, 3, bitSet);
//					for (int num = 0 ;num < productCountNum; num++ ) {
//						/**
//						 * 生成二维码
//						 */
//						String set = codeList.poll();
//						SecurityCode securityCode = new SecurityCode();
//						codeTailTag = (UUID.randomUUID().toString().replace("\\-",
//								"")).substring(0, len);// 追加尾数
//						codePrefix = orderCode + vendorCode + productCode + set;
//						String code = codePrefix + rowNum + codeTailTag;// 二维组合方式:前缀+rownum+后缀
//						/***
//						 * 保存二维码
//						 */
//						securityCode.setCreateRowNum(rowNum);
//						securityCode.setOrderId(orderId);
//						securityCode.setProductId(productId);
//						securityCode.setSecurityCode(code);
//						securityCode.setProductName(productName);
//						securityCode.setCodePrefix(codePrefix);
//						securityCode.setCodeTailTag(codeTailTag);
//
//						/**
//						 * 存入数组
//						 */			
//						list.add(securityCode);
////						System.out.println("code:" + list);
//					}
//					
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
////				
//			}
//			System.out.println("code:" + list);
//
//		}
//
//	
	
	
	
	
	
//	@Test
//	public void testCode1() {
//
//		int productCount = 100000;// 假设有10w商品需要附码
//		String i = Integer.toString(productCount);
//		/**
//		 * 先将需要附码的商品分组
//		 */
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组码的数量
////		int rowCount = (int) Math.ceil((Math.log(productCount))
////				/ (Math.log(62)));// 总的组数
//		int rowCount = (int)Math.ceil((productCount/(62 * 62)));
//		int len = i.length() - 1;
//		int rowNum = 1;// 设置组编号并初始化
//		/**
//		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
//		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//		 */
//		List<String> list = new ArrayList<String>();
//
////		System.out.println("step:" + 1);
//
////		/**
////		 * 外层循环按照组数递增
////		 */
//		String code = null;
//		String testCode = null;
//		String tailTag = null;
//		int vendorId = 100;
//		int productCountNum  = 3844;
//
////		
////		
////		for (int a = 1; a < rowCount; a++) {
////			rowNum = a;
////			if(productCountNum * a < productCount){
////				productCountNum = 3844;
////				}
////			if(productCountNum * a >= productCount){
////				productCountNum = productCount - (productCountNum * (a-1));
////			}			
//				LinkedList<String> codeList = CommonUtils.getRandomStrList(
//						productCount, 2, bitSet);
////				System.out.println("step:" + 2);
//				for (String set : codeList) {
//					
//					tailTag = (UUID.randomUUID().toString().replace("\\-", ""))
//							.substring(0,len);
//					code = Radix.convert10To62(vendorId, 4);
//					testCode = code + set + rowNum + tailTag;
////					System.out.println("step:" + 3);
//					list.add(testCode);
//				}
//				System.out.println("code:" + list);
//				
//	
//	}
//			
//
//		
//	
//	
//	@Test 
//	public void testMath(){
//		int productCount  = 5000;
//		int rowCount = (int) Math.ceil((productCount / (62 * 62)));		
//		System.out.println("rowCount:"+ rowCount);
//	}
//	@Test
//	public void testUrl() {
//		String redirect_uri = "http://b.cyc8.cn/getOpenId.json?securityCode=";
//		String scope = "snsapi_base";
//		redirect_uri = redirect_uri.replaceAll("getOpenId.json",
//				"getWxUserInfoByOAuth.json");
//		scope = "snsapi_userinfo";
//		StringBuffer url = new StringBuffer(
//				"redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
//		url.append("wxd8b97df93d037989").append("&redirect_uri=").append(redirect_uri);
//		 if (redirect_uri.indexOf('=') >= 0) {
//	            url.append(1330);
//	        }
//	        url.append("&response_type=code&scope=" + scope + "&state=csslink&connect_redirect=1#wechat_redirect");
//	        System.out.println("url"+ url);
//	}
	
//	@Test
//	public void testPasswd(){
//		String passwdPrivate = "e10adc3949ba59abbe56e057f20f883e";		
//		// System.out.println(pps);
//		 try {
//			byte[] inputBytes = passwdPrivate.getBytes("UTF8");
//			 BASE64Encoder encoder = new BASE64Encoder();
//			 String md = encoder.encode(inputBytes);
//	//	
//			System.out.println(md);
//		} catch (UnsupportedEncodingException e) {
//		
//			e.printStackTrace();
//		}
		
//			System.out.println(md);
//	}
//	@Test
//	public void testModelAndView(){
//		ModelAndView mav = new ModelAndView("redirect:/redirectToJsp.json?code=" + 52473 + "&batchCode=" + "00040204wNp14f7");
//		System.out.println(mav);
//								
//		
//	}
//	
//	
//	@Test
//	public void testSubString(){
//		String code = "00040204wNp14f7";
//		String orderCode = code.substring(0,4);
//		System.out.println(orderCode);
//	}
//	@Test
//	public void testBase62(){
//		int orderId = 4;
//		String orderCode = Radix.convert10To62(orderId, 4);
//		System.out.println("orderCode :" +orderCode);
//		int orderIdBack = Radix.convert62To10(orderCode);
//		System.out.println("orderIdBack : " + orderIdBack);
//	}
//	
//	@Test
//	public void testMathLog() {
//		Double rowNum = (Math.log(10000000)) / (Math.log(62));
//		int max = (int) Math.ceil(rowNum);
//		int min = (int) Math.floor(rowNum);
//		// System.out.println("max:   " + max + "   and   min:   " + min);
//		System.out.println(62 * 62 * 62);
//		for (int i = 0; i < 100000000; i++) {
//
//		}
//
//	}
//
//	@Test
//	public void testRadix() {
//
//		int productCount = 200;// 假设有10w商品需要附码
//		String i = Integer.toString(productCount);
//		/**
//		 * 先将需要附码的商品分组
//		 */
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组码的数量
//		int rowCount = (int) Math.ceil((Math.log(productCount))
//				/ (Math.log(64)));// 总的组数
//		int len = i.length() - 1;
//		int rowNum = 1;// 设置组编号并初始化
//		/**
//		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
//		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//		 */
//		List<String> list = new ArrayList<String>();
//
//		System.out.println("step:" + 1);
//
//		/**
//		 * 外层循环按照组数递增
//		 */
//		String code = null;
//		String testCode = null;
//		String tailTag = null;
//		int vendorId = 100;
////		SecurityCode securityCode = new SecurityCode();
//		int productCountNum = 3844;
//		for (int a = 1; a < 6; a++) {
//			rowNum = a;
//			if(productCountNum > productCount){
//				productCountNum = productCount;
//			}
//			if (productCountNum * a < productCount) {
//				productCountNum = 3844;
//			}
//			if (productCountNum * a >= productCount) {
//				productCountNum = productCount - (productCountNum * (a - 1));
//			}
//			LinkedList<String> codeList = CommonUtils.getRandomStrList(
//					productCount, 2, bitSet);
//			System.out.println("step:" + 2);
//			for (String set : codeList) {
//
//				tailTag = (UUID.randomUUID().toString().replace("\\-", ""))
//						.substring(0, len);
//
//				code = Radix.convert10To62(vendorId, 4);
//				testCode = code + set + rowNum + tailTag;
//				System.out.println("step:" + 3);
//				list.add(testCode);
//
//			}
//			System.out.println("code生成为:" + list);
//		}
//
//	}
//
//	@Test
//	public void testCode() {
//
//		int productCount = 1000000;// 假设有10w商品需要附码
//		String i = Integer.toString(productCount);
//		/**
//		 * 先将需要附码的商品分组
//		 */
//		BitSet bitSet = new BitSet(62 * 62 * 62);// 每组码的数量
////		int rowCount = (int) Math.ceil((Math.log(productCount))
////				/ (Math.log(62)));// 总的组数
//		int rowCount = (int)Math.ceil((productCount/(62 * 62)));
//		int len = i.length() - 1;
//		int rowNum = 1;// 设置组编号并初始化
//		/**
//		 * 需要参数 1 -> 生成code需要参数: (包含:"根据需要附码产品数量生成的字符 2位 ,控制数量在62 * 62 = 3844" +
//		 * "order表中的prefixOrderNo的2位字符" + "根据产品总量生成的 4位base62 字符" + "row" +
//		 * "储存位置编号" + "UUID生成的变成随机数" ) 2 -> 通过bitSet去重
//		 */
//		List<String> list = new ArrayList<String>();
//
////		System.out.println("step:" + 1);
//
//		/**
//		 * 外层循环按照组数递增
//		 */
//		String code = null;
//		String testCode = null;
//		String tailTag = null;
//		int vendorId = 100;
//		int productCountNum  = 3844;
//		
//		
//		for (int a = 1; a < rowCount; a++) {
//			rowNum = a;
//			if(productCountNum * a < productCount){
//				productCountNum = 3844;
//				}
//			if(productCountNum * a >= productCount){
//				productCountNum = productCount - (productCountNum * (a-1));
//			}			
//				LinkedList<String> codeList = CommonUtils.getRandomStrList(
//						productCountNum, 2, bitSet);
////				System.out.println("step:" + 2);
//				for (String set : codeList) {
//					
//					tailTag = (UUID.randomUUID().toString().replace("\\-", ""))
//							.substring(0,len);
//					code = Radix.convert10To62(vendorId, 4);
//					testCode = code + set + rowNum + tailTag;
////					System.out.println("step:" + 3);
//					list.add(testCode);
//				}
//				System.out.println("code:" + list);
//				}
//			
//
//		}
//
//	}
//	@Test
//	
//	public void testStringSql() {
//		
//		
//		
//        StringBuffer sql = new StringBuffer();
//        String name = "cloud";
//        String securityCode = "001CLQ14df91e";
//        String codeTailTag = "4df91e";
//        String codePrefix = "001CLQ";
//        sql.append("insert into `" + "tb_security_code_"+201 +"`"+ "(orderId,productId,productName," +
//        		"securityCode,codeTailTag,createRowNum,codeFlag,codePrefix," +
//                "createDate) values ");
//        sql.append("(" + 101 + ",");
//        sql.append(5 + ",");
//        sql.append("'" + name + "',");
//        sql.append("'" + securityCode + "',");
//        sql.append("'" + codeTailTag + "',");
//        sql.append( 12 + ",");
//        sql.append( 1 + ",");
//        sql.append("'" + codePrefix + "',");
//        sql.append("now()),");
//        
//        System.out.println("sql:" +sql);
//
//	}
//	
//	@Test
//	public void testCreateTable(){
//		StringBuffer sb = new StringBuffer();
//        StringBuffer sb1 = new StringBuffer();
//    
//        List list = new ArrayList();
//
//        sb.append("CREATE TABLE `tb_security_code_"+201+"`(" +
//        		 "`securityCodeId` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID'," +
//        		 "`orderId` bigint(20) NOT NULL COMMENT '订单ID'," +
//        		 "`productId` bigint(32) NOT NULL COMMENT '产品ID'," +
//        		 "`productName` varchar(64) NOT NULL COMMENT '产品名'," +
//        		 "`codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源'," +
//        		 "`codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成'," +
//        		 "`securityCode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码全码'," +
//        		 "`codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关'," +
//        		 "`createRowNum` varchar(32) NOT NULL COMMENT '分组生成的组号，单次生成未超过3844个，组号为1'," +
//        		 "`createDate` datetime NOT NULL COMMENT '生成日期',"+
//        		 " PRIMARY KEY (`securityCodeId`)" +
//        		") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二维码表';");
//        list.add(sb);//扫描信息记录表
//
//        sb1.append("CREATE TABLE `tb_anti_fake_"+201+"`(" +
//                "rowkey bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键'," +
//                "securityCodeId bigint(32) not null COMMENT '防伪码ID'," +
//                "securityCode varchar(64) not null COMMENT '防伪码'," +
//                "productId bigint(32) not null COMMENT '产品ID'," +
//                "productName varchar(32) not null COMMENT '产品名称'," +
//                "scanTime datetime not null COMMENT '扫描日期'," +
//                "scanAddress varchar(128)  DEFAULT NULL COMMENT '扫描地点'," +
//                "longitude varchar(32)  DEFAULT '' COMMENT '经度'," +
//                "latitude varchar(32)  DEFAULT NULL COMMENT '纬度'," +
//                "userId bigint(32)  DEFAULT NULL COMMENT '用户ID'," +
//                "openId varchar(32) DEFAULT NULL COMMENT '微信openId'," +
//                "lastUpdateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间'," +
//                "province varchar(32)  DEFAULT null," +
//                "city varchar(32)   DEFAULT null," +
//                "district varchar(32)   DEFAULT null," +
//                "scanCount int(20)  not null," +
//                "productScanCount int(11) DEFAULT '0' COMMENT '当天产品扫码次数'," +
//                "PRIMARY KEY (rowkey)," +
//                "UNIQUE KEY `new_uk_name` (`securityCodeId`)," +
//                "UNIQUE KEY `new_uk1_name` (`userId`,`securityCodeId`)" +
//                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;");
//        list.add(sb1);//订单二维码信息表
//        System.out.println("sql:" +list);
//	}
//	
//	
//	@Test
//	public void testSet(){
//		List<String>arr = new ArrayList<String>();
//		BitSet bitSet = new BitSet(62 * 62 * 62);
//		LinkedList<String> codeList = CommonUtils.getRandomStrList(
//				200, 3, bitSet);
//		for(String set: codeList){
//			String tailTag = (UUID.randomUUID().toString().replace("\\-", ""))
//					.substring(0, 4);
//
//			String code = Radix.convert10To62(12, 4);
//			String trueCode = tailTag + code +set;
//			arr.add(trueCode);
//		}
//		System.out.println("codeList:"+codeList);
//		System.out.println("FanilyCode: " +arr);
//	} 
//	@Test
//	public void testFindTale(){
//		StringBuffer sb = new StringBuffer();
//		StringBuffer sb1 = new StringBuffer();
//		List list = new ArrayList();
//		sb.append("tb_security_code_"+1);
//		list.add(sb.toString());
//		sb1.append("tb_anti_fake_"+1);
//		list.add(sb1.toString());
//		System.out.println(list);
////		for (Object obj : list) {
////			System.out.println(obj);;// 删除对应表
////		}
////		
//
//	}
////
//	public void testSecority(){
//		BitSet bitSet = new BitSet(62 * 62 * 62);
//		int productCountNum = 10000;
//		
//		List<SecurityCode> list = new ArrayList<SecurityCode>();
//		LinkedList<String> codeList = CommonUtils.getRandomStrList(
//				productCountNum, 3, bitSet);
//		for (int num = 0 ;num < productCountNum; num++ ) {
//			/**
//			 * 生成二维码
//			 */
//		String code = "dgdfg";
//			/**
//			 * 存入数组
//			 */			
//			list.addAll(code);
//			
//		}
//		System.out.println("code:" + list);
//	}


}
