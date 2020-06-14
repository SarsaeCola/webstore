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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/search.css">
<title>投石问路</title>
</head>
<body>
    <form action="<c:url value='WeaponServlet'/>" method="get" target="body" id="form1">
    	<input type="hidden" name="method" value="findByWeaponName"/>
    	<input type="text" name="wname"/>
    	<span>
    		<a onclick="$('#form1').submit()">搜索</a>
    		<a href="<c:url value='/jsp/main/superSearch.jsp'/>" style="font-size: 10pt; color: #404040;" target="body">高级搜索</a>
    	</span>
    </form>
</body>
</html>