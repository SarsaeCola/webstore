<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>刀山剑林后台管理</title>
</head>
  <body style="background: 	#48D1CC;color: #FFFF00;">
<h1 style="text-align: center; line-height: 30px;">刀山剑林后台管理</h1>
<div style="line-height: 10px;">
	<span>管理员：${sessionScope.admin.adminName }</span>
	<a target="_top" href="<c:url value='/AdminServlet?method=quit'/>">退出</a>
	<span style="padding-left:50px;">
		<a target="body" href="<c:url value='/admin/AdminCategoryServlet?method=findAll'/>">分类管理</a>
		<a target="body" href="<c:url value='/adminJsps/content/weapon/main.jsp'/>">武器管理</a>
		<a target="body" href="<c:url value='/adminJsps/content/order/list.jsp'/>">订单管理</a>
	</span>
</div>
  </body>
</html>