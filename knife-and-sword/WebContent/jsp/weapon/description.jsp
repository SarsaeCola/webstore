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
<script type="text/javascript" src="<%=basePath%>js/weapon/description.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/weapon/description.css">
<title>再买断手</title>
</head>
<body>
  <div class="divBookName">${weapon.wname }</div>
  <div>
    <img align="top" src="<c:url value='/${weapon.bimg }'/>" class="img_image_w"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>商品编号：${weapon.wid }</li>
	    	<li>现价：<span class="price_n">&yen;${weapon.currentPrice }</span></li>
	    	<li>定价：<span class="spanPrice">&yen;${weapon.price }</span>　折扣：<span style="color: #c30;">${weapon.discount }</span>折</li>
	    </ul>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
					制造方：${weapon.author } 
				</td>
			</tr>
			<tr>
				<td colspan="3">材质：${weapon.material }</td>
			</tr>
		</table>
		<div class="divForm">
			<form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="method" value="add"/>
				<input type="hidden" name="wid" value="${weapon.wid }"/>
  				我要买：<input id="cnt" style="width: 40px;text-align: center;" type="text" name="quantity" value="1"/>件
  			</form>
  			<a id="btn" href="javascript:$('#form1').submit();"></a>
  		</div>
	</div>
  </div>
  </body>
</html>