<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript"
	src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/cart/showitem.js"></script>
<script type="text/javascript" src="<%=basePath%>js/cart/round.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/cart/showitem.css">
<title>投镖</title>
</head>
<body>
<form id="form1" action="<c:url value='/OrderServlet'/>" method="post">
	<input type="hidden" name="cartItemIds" value="${cartItemIds }"/>
	<input type="hidden" name="method" value="createOrder"/>
<table width="95%" align="center" cellpadding="0" cellspacing="0">
	<tr bgcolor="#efeae5">
		<td width="400px" colspan="5"><span style="font-weight: 900;">生成订单</span></td>
	</tr>
	<tr align="center">
		<td width="10%">&nbsp;</td>
		<td width="50%">武器名称</td>
		<td>单价</td>
		<td>数量</td>
		<td>小计</td>
	</tr>

<c:forEach items="${cartItemList }" var="cartItem">
	<tr align="center">
		<td align="right">
			<a class="linkImage" href="<c:url value='/jsp/weapon/description.jsp'/>"><img border="0" width="54" align="top" src="<c:url value='/${cartItem.weapon.simg }'/>"/></a>
		</td>
		<td align="left">
			<a href="<c:url value='/jsp/weapon/description.jsp'/>"><span>${cartItem.weapon.wname }</span></a>
		</td>
		<td>&yen;${cartItem.weapon.currentPrice }</td>
		<td>${cartItem.quantity }</td>
		<td>
			<span class="price_n">&yen;<span class="subtotal">${cartItem.subtotal }</span></span>
		</td>
	</tr>
</c:forEach>
	<tr>
		<td colspan="6" align="right">
			<span>总计：</span><span class="price_t">&yen;<span id="total">${total }</span></span>
		</td>
	</tr>
	<tr>
		<td colspan="5" bgcolor="#efeae5"><span style="font-weight: 900">收货地址</span></td>
	</tr>
	<tr>
		<td colspan="6">
			<input id="addr" type="text" name="address" placeholder="请填写收货地址"/>
			&nbsp;<label id="addrError" class="error"></label>
		</td>
	</tr>
	<tr>
		<td style="border-top-width: 4px;" colspan="5" align="right">
			<a id="linkSubmit" onclick="$('#form1').submit()">生成订单</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>