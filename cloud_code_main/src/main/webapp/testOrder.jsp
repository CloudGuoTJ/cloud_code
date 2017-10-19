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
<script type="text/javascript" src= "js/ajaxfileupload.js"></script>
<script type="text/javascript">
    	
</script>
  </head>
  
  <body>
  	<form action="/cloud_code/POST/product/productOrder.do" method="post" enctype="multipart/form-data">
  		<table>
    		<tr>
    			<td>
    				<!-- <input type="file" id="logoImage" name="logoImage"> -->
    				<p>商家名</p><input type="text" name="vendorName"/>
    				
    				<p>商家ID</p><input type="text" name="vendorId"/>
    				
    				<p>厂商名</p> <input type="text" name="productName"/>
    				  			
    			    <p>产品ID</p> <input type="text" name="productId"/>
    			    
    			    <p>产品数量</p><input type="text" name="productCount"/>
    			       			    
    			    <p>售价</p><input type="text" name="referencePrice"/>
    			    
    			    <p>有效时间</p><input type="text" name="expiryDate"/>    				
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
