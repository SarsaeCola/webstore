<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/main.css'/>">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<title>后台主页</title>
<script type="text/javascript">
$(function() {
	var ipAddr="${ipAddrAdminLogin}";
	var timestamp="${loginTimeAdmin}";
	alert("ip："+ipAddr+"\n时间："+timestamp);
});
</script>
</head>
<body >
<table class="table" align="center" >
	<tr>
		<td colspan="2" align="center" height="100px;">
			<iframe frameborder="0" src="<c:url value='/adminJsps/content/top.jsp'/>" name="top"></iframe>
		</td>
	</tr>
	<tr style="height: 99%;">
		<td>
			<iframe frameborder="0" src="<c:url value='/adminJsps/content/body.jsp'/>" name="body" onload="this.height=iframe2.document.body.scrollHeight"></iframe>
		</td>
	</tr>
</table>
</body>
</html>