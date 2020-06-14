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
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin/order/description.css'/>">
<title>订单详细</title>
</head>
<body>
	<div class="divOrder">
		<span>订单号：${oder.oid }
<c:choose>
	<c:when test="${order.status eq 1 }">(等待付款)</c:when>
	<c:when test="${order.status eq 2 }">(准备发货)</c:when>
	<c:when test="${order.status eq 3 }">(等待确认)</c:when>
	<c:when test="${order.status eq 4 }">(交易成功)</c:when>
	<c:when test="${order.status eq 5 }">(已取消)</c:when>
</c:choose>
		　　　下单时间：${order.orderTime }
		</span>
	</div>
	<div class="divRow">
		<div class="divContent">
			<dl>
				<dt>收货人信息</dt>
				<dd>${order.address }</dd>
			</dl>
		</div>
		<div class="divContent">
			<dl>
				<dt>商品清单</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">商品名称</th>
							<th class="tt" align="left">单价</th>
							<th class="tt" align="left">数量</th>
							<th class="tt" align="left">小计</th>
						</tr>

<c:forEach items="${order.orderItemList }" var="orderItem">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="weaponname">
								  <img align="middle" width="70" src="<c:url value='/${orderItem.weapon.simg }'/>"/>
								  ${orderItem.weapon.wname }
								</div>
							</td>
							<td class="td" >
								<span>&yen;${orderItem.weapon.currentPrice }</span>
							</td>
							<td class="td">
								<span>${orderItem.quantity }</span>
							</td>
							<td class="td">
								<span>&yen;${orderItem.subtotal }</span>
							</td>			
						</tr>
</c:forEach>
		
					</table>
				</dd>
			</dl>
		</div>
		<div class="divBtn">
			<span class="spanTotal">合　　计：</span>
			<span class="price_t">&yen;${order.total }</span><br/>

<c:if test="${order.status eq 2 and btn eq 'deliver' }">
	<a id="deliver" href="<c:url value='/admin/AdminOrderServlet?method=deliver&oid=${order.oid }'/>">发　　货</a>
</c:if>
<c:if test="${order.status eq 1 and btn eq 'cancel' }">
	<a id="cancel" href="<c:url value='/admin/AdminOrderServlet?method=cancel&oid=${order.oid }'/>">取　　消</a>
</c:if>
		</div>
	</div>
</body>
</html>