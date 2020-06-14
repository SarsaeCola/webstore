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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main/superSearch.css">
<title>抛砖引玉</title>
</head>
<body>
<form action="<c:url value='WeaponServlet'/>" method="get">
  	<input type="hidden" name="method" value="findByCombination"/>
<table align="center">
	<tr>
		<td>武器名：</td>
		<td><input type="text" name="wname"/></td>
	</tr>
	<tr>
		<td>制造者：</td>
		<td><input type="text" name="author"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
			<input type="reset" value="重新填写"/>	
			<input type="submit" value="搜　　索"/>
		</td>
	</tr>
</table>
	</form>
</body>
</html>