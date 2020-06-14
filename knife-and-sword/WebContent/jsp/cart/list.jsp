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
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/cart/round.js"></script>
<script type="text/javascript" src="<%=basePath%>js/cart/list.js"></script>
<script type="text/javascript" src="<c:url value='/js/page/page.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/page.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/cart/list.css">
<title>喜欢就买了吧</title>
</head>
<body>

	<c:choose>
		<c:when test="${empty cartItemList }">
			<table width="95%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right"><img align="top"
						src="<c:url value='/imgs/cart/icon_empty.png'/>" /></td>
					<td><span class="spanEmpty">您的购物车中暂时没有商品</span></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<table width="95%" align="center" cellpadding="0" cellspacing="0">
				<tr align="center" bgcolor="#efeae5">
					<td align="left" width="50px">
						<input type="checkbox" id="selectAll" checked="checked" />
						<label for="selectAll">全选</label>
					</td>
					<td colspan="2">商品名称</td>
					<td>单价</td>
					<td>数量</td>
					<td>小计</td>
					<td>操作</td>
				</tr>

				<c:forEach items="${cartItemList }" var="cartItem">
					<tr align="center">
						<td align="left"><input value="${cartItem.iid }"
							type="checkbox" name="checkboxBtn" checked="checked" /></td>
						<td align="left" width="70px">
							<a class="linkImage" href="<c:url value='/jsp/weapon/description.jsp'/>">
							<img border="0" width="54" align="top" src="<c:url value='/${cartItem.weapon.simg }'/>" />
							</a>
						</td>
						<td align="left" width="400px">
							<a href="<c:url value='/jsp/weapon/description.jsp'/>"><span>${cartItem.weapon.wname }</span></a>
						</td>
						<td><span>&yen;<span class="currentPrice">${cartItem.weapon.currentPrice }</span></span></td>
						<td><a class="decrease" id="${cartItem.iid }Decrease"></a><input
							class="quantity" readonly="readonly"
							id="${cartItem.iid }Quantity" type="text"
							value="${cartItem.quantity }" /><a class="increase"
							id="${cartItem.iid }Increase"></a></td>
						<td width="100px">
							<span class="price_n">
								&yen;<span class="subTotal" id="${cartItem.iid }Subtotal">${cartItem.subtotal }</span>
							</span>
						</td>
						<td><a
							href="<c:url value='/CartItemServlet?method=batchDelete&cartItemIds=${cartItem.iid }'/>">删除</a>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4" class="tdBatchDelete">
					<a href="javascript:batchDelete();">批量删除</a></td>
					<td colspan="3" align="right" class="tdTotal"><span>总计：</span><span
						class="price_t">&yen;<span id="total"></span></span></td>
				</tr>
				<tr>
					<td colspan="7" align="right">
					<a href="javascript:settlement();" id="settlement" class="settlement"
					style="poorfish:expression_r(this.onclick = function settlement(){return true})">
					</a>
					</td>
				</tr>
			</table>
			<form id="settlementForm" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="cartItemIds" id="cartItemIds" /> 
				<input type="hidden" name="total" id="hiddenTotal" /> 
				<input type="hidden" name="method" value="loadCartItems" />
			</form>

		</c:otherwise>
	</c:choose>
</body>

</html>