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
  
  <body>
  <!-- AddUser -->
  	<form action="/cloud_code/POST/user/uploadRegistrationPic.do" method="post" enctype="multipart/form-data">
  		<table>
    		<tr>
    			<td>
    								
    				<p>商家id:</p><input type="text" name="vendorId"/>		
					<p>营业执照:</p><input type="file" name="tradeMarkImgUrl" >
					<p>企业商标注册证书:</p><input type="file" name="tradeMarkLicense">		
					<p>银行开户许可:</p><input type="file" name="bankAccountOpeningLicense">
					<p>食品生产许可证:</p><input type="file" name="foodProductionLicence">	
				<!-- 	<p>组织机构代码证:</p><input type="file" name="organizationCodeCertificate"> -->
					<p>工业产品生产许可证:</p><input type="file" name="industrialProductionLicense">			   				
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="submit" value="提交"/>
    			</td>
    		</tr>
    	</table>
  	</form>
  	
    
  </body>
</html>
