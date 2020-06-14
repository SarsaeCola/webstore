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
<title>修改二级分类</title>
<style type="text/css">
	body {
	background: #C6E2FF;
	text-align: center;
	}
	input{
		margin-bottom: 10px;
	}
</style>
</head>
  <body>
    <h3>修改2级分类</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value="editChild"/>
    	<input type="hidden" name="cid" value="${child.cid }"/>
    	分类名称：<input type="text" name="cname" value="${child.cname }" id="cname"/><br/>
    	一级分类：<select name="pid" id="pid">
    		<option value="">===选择1级分类===</option>
<c:forEach items="${parents }" var="parent">
    		<option value="${parent.cid }" <c:if test="${parent.cid eq child.parent.cid }">selected="selected"</c:if> >${parent.cname }</option>
</c:forEach>
    	</select><br/>
    	分类描述：<textarea rows="5" cols="50" name="description" id="description">${child.description }</textarea><br/>
    	<input type="submit" value="修改二级分类"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>