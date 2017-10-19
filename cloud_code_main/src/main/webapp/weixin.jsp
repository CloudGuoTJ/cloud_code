<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'weixin.jsp' starting page</title>
    
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
    This is my JSP page. <br>
    <a href="javascript:void(0)" onclick="test()">shouquan</a>
    <p id="text"></p>
   	<script src="https://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
   	<script type="text/javascript">
   	function test() {
   		$.ajax({
	    		url : "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx620ee71a7e75ffe9&redirect_uri=https%3a%2f%2fym-a.top%2fopenweixin%2fcallback.do&response_type=code&scope=snsapi_base&state=123&component_appid=wxb41e9be2f174588e#wechat_redirect",
	    		type : "post",
	    		datatype : "json",
	       		success : function(data) {
	       			$('#text').html(data)
				},
				error:function(data){
					$('#text').html(data)
				},
				async: false,
				cache : false
       	});
   	}
   	</script>
  </body>
</html>
