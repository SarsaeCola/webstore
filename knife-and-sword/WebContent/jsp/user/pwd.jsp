<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>回炉重造</title>
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/user/pwd.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/user/pwd.css">
</head>
 <body>
    <div class="div0">
    	<span>修改密码</span>
    </div>

	<div class="div1">
		<form action="<c:url value='/UserServlet'/>" method="post" target="_top" id="remake">
			<input type="hidden" name="method" value="updatePassword"/>
		<table>
			<tr>
				<td><label id="msg" class="error">${msg }</label></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td align="right">原密码:</td>
				<td><input class="input" type="password" name="loginpass" id="loginpass" value="${form.userPassword }"/></td>
				<td><label id="loginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">新密码:</td>
				<td><input class="input" type="password" name="newpass" id="newpass" value="${form.newPassword }"/></td>
				<td><label id="newpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right">确认密码:</td>
				<td><input class="input" type="password" name="reloginpass" id="reloginpass" value="${form.repeatPassword }"/></td>
				<td><label id="reloginpassError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td>
				  <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>
		    	  <a href="javascript:changeImg();">看不清，换一张</a>
				</td>
			</tr>
			<tr>
				<td align="right">验证码:</td>
				<td>
				  <input class="input" type="text" name="VerifyCode" id="VerifyCode" />
				</td>
				<td><label id="VerifyCodeError" class="error"></label></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td><a id="submit" onclick="$('#remake').submit()">人生重来</a></td>
			</tr>
		</table>
		</form>
	</div>
  </body>
</html>