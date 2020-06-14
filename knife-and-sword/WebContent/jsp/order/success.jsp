<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/order/success.css">
<title>下单成功</title>
</head>
<body>
<div class="div1">
	<span class="span1">订单已生成</span>
</div>
<div class="div2">
	<img src="<c:url value='/imgs/pass.jpeg'/>" class="img"/>
	<dl>
		<dt>订单编号</dt>
		<dd>${order.oid }</dd>
		<dt>合计金额</dt>
		<dd><span class="price_t">&yen;${order.total }</span></dd>
		<dt>收货地址</dt>
		<dd>${order.address }</dd>
	</dl>
	<span>感谢您对本站的支持，祝您购物愉快！</span>
	<a href="<c:url value='/OrderServlet?method=paymentPre&oid=${order.oid }'/>" id="linkPay">支付</a>
</div>
  </body>
</html>