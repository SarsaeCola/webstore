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
<script type="text/javascript" src="<%=basePath%>js/admin/category/add.js"></script>
<title>修改一级分类</title>
<style type="text/css">
	body {
	background: #7A67EE;
	text-align: center;
	}
	input{
		margin-bottom: 10px;
	}
</style>
</head>
 <body>
    <h3>修改1级分类</h3>
    <h1></h1>
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="cid" value="${parent.cid }"/>
    	<input type="hidden" name="method" value="editParent"/>
    	分类名称：<input type="text" name="cname" id="cname" value="${parent.cname }"/><br/>
    	分类描述：<textarea rows="5" cols="50" id="description" name="description">${parent.description }</textarea><br/>
    	<input type="submit" value="修改分类"/>
    	<input type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </body>
</html>