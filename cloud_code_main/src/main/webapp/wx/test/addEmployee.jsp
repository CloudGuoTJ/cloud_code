<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
		String name=request.getParameter("openId");
		out.println("接收到:"+name); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addEmployee.jsp' starting page</title>
    
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

    <form action="http://120.77.149.115/cloud_code/ADD/AgentEmployee/addEmpInfo.do" method="post">
  		<input type="text" id="openId" name="openId" style="display:none">
    	
  		<input type="text" id="vendorId" name="vendorId" style="display:none">
    	
  		<input type="text" id="agentId" name="agentId" style="display:none">
  		<label>员工姓名:</label>
  		<input type="text" id="empName" name="empName"></br>
  		
  		<input type="text" id="productId" name="productId" style="display:none">
  		<label>员工工号:</label>
  		<input type="text" id=workNum name="workNum"></br>
  		<label>员工电话:</label>
  		<input type="text" id="empTel" name="empTel"></br>
  		<label>员工身份证号:</label>
  		<input type="text" id="empIdcard" name="empIdcard"></br>
  		
    	<input type="submit" value="提交">
    </form>
  </body>
  <script src="https://code.jquery.com/jquery-3.2.1.js" ></script>
  
  <script>
  var getPrams=function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null)return unescape(r[2]);
                return null;
            };
   $('#openId').attr('value',getPrams('openId'));
   $('#vendorId').attr('value',getPrams('vendorId'));
   $('#agentId').attr('value',getPrams('agentId'));
  </script>
</html>
