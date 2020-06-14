<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<% 
	String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" +     
    request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/main.css">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<title>刀山剑林</title>
<script type="text/javascript">
$(function() {
	var ipAddr="${ipAddrUserLogin}";
	var timestamp="${loginTimeUser}";
	if(typeof(ipAddr)!="undefined"&&ipAddr!=null&&ipAddr!=""){
		alert("ip："+ipAddr+"\n时间："+timestamp);
	}
})

</script>
</head>
<body>
<table class="table" align="center">
	<tr class="Top">
		<td colspan="2"	class="top">
			<iframe frameborder="0" src="<c:url value='/jsp/main/top.jsp'/>" name="top"></iframe>
		</td>
	</tr>
	<tr>
		<td class="left" rowspan="2">
			<iframe frameborder="0" src="<c:url value='/CategoryServlet?method=searchAll'/>" name="left"></iframe>
		</td>
		<td class="search" style="border-bottom-width: 0px;">
			<iframe frameborder="0" src="<c:url value='/jsp/main/search.jsp'/>" name="search"></iframe>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 0px;">
			<iframe frameborder="0" src="<c:url value='/jsp/main/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>
</body>
</html>