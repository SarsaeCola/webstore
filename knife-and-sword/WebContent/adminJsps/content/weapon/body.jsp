<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<title>内容主体</title>
</head>
  <body>
<h1 align="center">武器管理</h1>
<p align="center">
<a href="<c:url value='/admin/AdminWeaponServlet?method=addPre'/>" style="margin: 20px; font-size: 20px;">添加武器</a>
<a href="<c:url value='/adminJsps/content/weapon/superSearch.jsp'/>" style="margin: 20px; font-size: 20px;">高级搜索</a>
</p>
  </body>
</html>