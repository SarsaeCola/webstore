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
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/weapon/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/page/page.css'/>" />
    <script type="text/javascript" src="<c:url value='/js/page/page.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/weapon/list.js'/>"></script>
<title>刀山剑林</title>
</head>
  
  <body>

<ul>
<c:forEach items="${pageBean.beanList }" var="weapon">
  <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/WeaponServlet?method=load&wid=${weapon.wid }'/>"><img src="<c:url value='/${weapon.bimg }'/>" border="0"/></a>
    <p class="price">
		<span class="price_n">${weapon.currentPrice }</span>
		<span class="price_r">${weapon.price }</span>
		(<span class="price_s">${weapon.discount }折</span>)
	</p>
	<p><a id="weaponname" title="${weapon.wname }" href="<c:url value='/WeaponServlet?method=load&wid=${weapon.wid }'/>">${weapon.wname }</a></p>
	<%-- url标签会自动对参数进行url编码 --%>
	<c:url value="/WeaponServlet" var="authorUrl">
		<c:param name="method" value="findByAuthor"/>
		<c:param name="author" value="${weapon.author }"/>
	</c:url> 
	<p><a href="${authorUrl }" name='P_zz' title='${weapon.author }'>${weapon.author }</a></p>
  </div>
  </li>
</c:forEach>
</ul>

	<div style="float:left; width: 100%; text-align: center;">
		<hr/>
		<br/>
		<%@include file="/jsp/page/page.jsp" %>
	</div>
</body>
</html>