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
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/top.css">
<title>三花聚顶</title>
</head>
<body>
<h1 style="text-align: center;">聚宝盆</h1>
<div style="font-size: 10pt; line-height: 10px;">

<%-- 根据用户是否登录，显示不同的链接 --%>
<c:choose>
	<c:when test="${empty sessionScope.sessionUser }">
		  <a href="<c:url value='/jsp/user/login.jsp'/>" target="_parent">刀山剑林登录</a> |&nbsp; 
		  <a href="<c:url value='/jsp/user/register.jsp'/>" target="_parent">注册刀山剑林</a>	
	</c:when>
	<c:otherwise>
		      侠客：${sessionScope.sessionUser.userName }&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">百宝袋</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">镖局</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/jsp/user/pwd.jsp'/>" target="body">人生重来</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		  <a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">引退江湖</a>&nbsp;&nbsp;
	</c:otherwise>
</c:choose>
</div>
</body>
</html>