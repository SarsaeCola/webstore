<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta charset="UTF-8">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/page.css'/>" />
<script type="text/javascript" src="<c:url value='/js/page/page.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/order/list.css'/>" />


<title>订单管理</title>
</head>
 <body>
<p class="pLink">
  <a href="<c:url value='/admin/AdminOrderServlet?method=findByStatus&status=1'/>">未付款</a>  | 
  <a href="<c:url value='/admin/AdminOrderServlet?method=findByStatus&status=2'/>">已付款</a>  | 
  <a href="<c:url value='/admin/AdminOrderServlet?method=findByStatus&status=3'/>">已发货</a>  | 
  <a href="<c:url value='/admin/AdminOrderServlet?method=findByStatus&status=4'/>">交易成功</a>  | 
  <a href="<c:url value='/admin/AdminOrderServlet?method=findByStatus&status=5'/>">已取消</a>
</p>
<div class="divMain">
	<div class="title">
		<div style="margin-top:7px;">
			<span style="margin-left: 150px;margin-right: 280px;">商品信息</span>
			<span style="margin-left: 40px;margin-right: 100px;">金额</span>
			<span style="margin-left: 50px;margin-right: 53px;">订单状态</span>
			<span style="margin-left: 100px;">操作</span>
		</div>
	</div>
	<br/>
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
<c:forEach items="${pageBean.beanList }" var="order">	
	
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/admin/AdminOrderServlet?method=load&oid=${order.oid }'/>">${order.oid }</a></td>
			<td width="200px">下单时间：${order.orderTime }</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>	

		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">


  <c:forEach items="${order.orderItemList }" var="orderItem">
	    <img border="0" width="70" src="<c:url value='/${orderItem.weapon.simg }'/>"/>
  </c:forEach>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;${order.total }</span>
			</td>
			<td>
<c:choose>
	<c:when test="${order.status eq 1 }">(等待付款)</c:when>
	<c:when test="${order.status eq 2 }">(准备发货)</c:when>
	<c:when test="${order.status eq 3 }">(等待确认)</c:when>
	<c:when test="${order.status eq 4 }">(交易成功)</c:when>
	<c:when test="${order.status eq 5 }">(已取消)</c:when>
</c:choose>	
			</td>
						
			<td>
			<a href="<c:url value='/admin/AdminOrderServlet?method=load&oid=${order.oid }'/>">查看</a><br/>
<c:if test="${order.status eq 1 }">
				<a href="<c:url value='/admin/AdminOrderServlet?method=load&oid=${order.oid }&btn=cancel'/>">取消</a><br/>						
</c:if>
<c:if test="${order.status eq 2 }">
				<a href="<c:url value='/admin/AdminOrderServlet?method=load&oid=${order.oid }&btn=deliver'/>">发货</a><br/>
</c:if>			

			</td>
		</tr>
</c:forEach>

	</table>
	<br/>
	<%@include file="/jsp/page/page.jsp" %>
</div>
  </body>
</html>