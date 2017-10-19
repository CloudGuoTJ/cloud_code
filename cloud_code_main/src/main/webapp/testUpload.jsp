<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'testUpload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body><!-- enctype="multipart/form-data" -->


 	<!-- <form action="/cloud_code/openweixincredentials/upload.do" method="post"> -->

   	<form action="/cloud_code/test/collectWord/test.do" method="post" enctype="multipart/form-data">


  	<!--<form action="/cloud_code/ADD/vendor/basicInfo.do" method="post" enctype="multipart/form-data">-->
 
  		<table>
    		<tr>
    			<td>

    				<input type="file" id="imgUrl" name="credentials">
    				<!-- <input type="file" id="img_certName" name="credentials">
    				<input type="file" id="imgUrl1" name="credentials">
    				<input type="file" id="img_certName1" name="credentials">
    				<input type="text" id="vendorId" name="vendorId" value="1"/> -->
    				<!-- <input type="text" id="vendorName" name="vendorName"/>
    				<input type="text" id="brandName" name="brandName"/>
    				<input type="text" id="industryName" name="industryName"/>
    				<input type="text" id="contactName" name="contactName"/> 
    				<input type="text" name="customPhone"/>
    				<input type="text" name="vendorWeixin"/>
    				<input type="text" name="customTel"/>
    				<input type="text" name="vendorAddress"/>
    				<input type="text" name="link"/>
    				<input type="text" name="officialAccounts"/> -->
    			<!-- 		<input type="text" name="threeMinMoney"/>

    				<%-- <input type="file" id="buffer" name="buffer">
    				<input type="text" id="access_token" name="access_token" value="${accessToken }"/> --%>
    				<input type="text" id="tagName" name="tagName">
    				<input type="text" id="vendorId" name="name"/>
    				<!-- <input type="text" id="reduce" name="reduce" value="2"/>
    				<input type="text" id="leastCost" name="leastCost" value="10"/>
    				<input type="text" id="stock" name="stock" value="5"/>
    				<input type="text" name="buyerLimit" value="1"/>
    				<input type="text" name="beginTime" value="2017-06-24 00:00:00"/>
    				<input type="text" name="endTime" value="2017-06-30 00:00:00"/>
    				<input type="text" name="openGet" value="1"/>
    				<input type="text" name="showFinish" value="0"/> -->
    				<!--
    				<input type="text" name="threeRate"/>
    				<input type="text" name="threeMinMoney"/>

    				<input type="text" name="threeMaxMoney"/>
    				<input type="text" name="fourRate"/>
    				<input type="text" name="fourMinMoney"/>
    				<input type="text" name="fourMaxMoney"/>
    				<input type="text" name="fiveRate"/>
    				<input type="text" name="fiveMinMoney"/>
    				<input type="text" name="fiveMaxMoney"/>  -->
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="submit" onclick="test()" value="提交"/>
    			</td>
    		</tr>
    	</table>
  	</form>
  	
  </body>
  	<script src="http://cdn.bootcss.com/blueimp-md5/1.1.0/js/md5.js"></script>  
  	<script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  	<script type="text/javascript">
	function test() {
		var ext_json = '{"extEnable":true,"extAppid":"wxf9c4501a76931b33","ext":{"name":"wechat","attr":{"host":"open.weixin.qq.com","users":["user_1","user_2"]}},"extPages":{"pages/logs/logs":{"navigationBarTitleText":"logs"}},"window":{"backgroundTextStyle":"light","navigationBarBackgroundColor":"#fff","navigationBarTitleText":"Demo","navigationBarTextStyle":"black"},"tabBar":{"list":[{"pagePath":"pages/index/index","text":"首页"},{"pagePath":"pages/logs/logs","text":"日志"}]},"networkTimeout":{"request":10000,"downloadFile":10000}}';
		$.ajax({
	    		url : "http://localhost:8080//cloud_code/test/openweixinwxapp/commit.do",
	    		type : "post",
	    		data:{
	    			ext_json : ext_json,
	    			accessToken : ""
	    		},
	    		datatype : "json",
	       		success : function(data) {
	       			alert(data);
				},
				async: false,
				cache : false
       	});
	} 
  		
  	</script>
</html>
