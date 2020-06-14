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
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/weapon/left.css'/>">
<script type="text/javascript" src="<c:url value='/js/main/category.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/category.css">
<title>分类菜单</title>
<script language="javascript">
/*
 * 1. 对象名必须与第一个参数相同！
   2. 第二个参数是显示在菜单上的大标题
 */
var bar = new Q6MenuBar("bar", "刀山剑林大宝库");
$(function() {
	bar.colorStyle = 2;//指定配色样式，一共0,1,2,3,4
	bar.config.imgDir = "<c:url value='/imgs/category/'/>";//小工具所需图片的路径
	bar.config.radioButton=true;//是否排斥，多个一级分类是否排斥

	/*
	1. 程序设计：一级分类名称
	2. Java Javascript：二级分类名称
	3. /goods/jsps/book/list.jsp：点击二级分类后链接到的URL
	4. body:链接的内容在哪个框架页中显示
	*/
	<c:forEach items="${parents }" var="parent">
	  <c:forEach items="${parent.children }" var="child">
		bar.add("${parent.cname }", "${child.cname }", "/knife-and-sword/admin/AdminWeaponServlet?method=findByCategory&cid=${child.cid }", "body");
	  </c:forEach>
	</c:forEach>
		
		$("#menu").html(bar.toString());
	});
</script>
</head>
  <body onload="load()">
<div id="menu"></div>
  </body>
</html>