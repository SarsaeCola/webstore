<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/weapon/main.css'/>">
<title>武器管理主页</title>

<style type="text/css">
  html,body{ width:100%; height:100%;}
  body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
  }
</style>
</head>
  <body>
<table class="table" align="center" width="100%" height="100%" border="0">
	<tr>
		<td align="center" width="200px;">
			<iframe frameborder="0" src="<c:url value='/admin/AdminWeaponServlet?method=findCategoryAll'/>" name="left"></iframe>
		</td>
		<td>
			<iframe frameborder="0" src="<c:url value='/adminJsps/content/weapon/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>
  </body>
</html>