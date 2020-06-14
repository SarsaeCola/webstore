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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/left.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/category.css">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<c:url value='/js/main/category.js'/>"></script>
<title>Left</title>
</head>
<body>
	  <div id="menu"></div>
</body>
<script type="text/javascript">

var bar = new Q6MenuBar("bar", "刀山剑林大宝库");
$(function() {
	bar.colorStyle = 4;//指定配色样式
	bar.config.imgDir = "<c:url value='/imgs/category/'/>";//小工具所需图片的路径
	bar.config.radioButton=true;//是否可以打开多个一级分类

	/*
	1. 程序设计：一级分类名称
	2. Java Javascript：二级分类名称
	3. /knife-and-sword/jsps/book/list.jsp：点击二级分类后链接到的URL
	4. body:链接的内容在哪个框架页中显示
	*/
<c:forEach items="${parents }" var="parent">
  <c:forEach items="${parent.children }" var="child">
	bar.add("${parent.cname }", "${child.cname }", "/knife-and-sword/WeaponServlet?method=findByCategory&cid=${child.cid }", "body");
  </c:forEach>
</c:forEach>
	
	$("#menu").html(bar.toString());
});
</script>
</html>
