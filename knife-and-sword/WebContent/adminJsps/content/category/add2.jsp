<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta charset="UTF-8">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/admin/category/add2.js"></script>
<style type="text/css">
	body {
	background: #FFB5C5;
	text-align: center;
	}
</style>
<title>添加二级分类</title>
</head>
<body>
    <h3>添加2级分类</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value='addChild'/>
    	分类名称：<input type="text" name="cname" id="cname"/><br/>
    	一级分类：<select name="pid" id="pid">
    		<option value="">===选择1级分类===</option>
<c:forEach items="${parents }" var="parent">
    		<option value="${parent.cid }" <c:if test="${parent.cid eq pid }">selected="selected"</c:if> >${parent.cname }</option>
</c:forEach>
    	</select><br/>
    	分类描述：<textarea rows="5" cols="50" name="description" id="description"></textarea><br/>
    	<input type="submit" value="添加二级分类"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>