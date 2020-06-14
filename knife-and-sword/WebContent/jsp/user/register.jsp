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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/user/register.css">
<link rel="stylesheet" href="<%=basePath%>css/font-awesome-4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/user/register.js"></script>
<title>刀山剑林注册</title>
</head>
<body>
	<div id="maindiv">
		<div id="titlediv">
			<span id="title">初涉江湖</span>
		</div>
		<div id="contentdiv">
		  <form id="registerform" action="<c:url value='/UserServlet'/>" method="post" onsubmit="return checkForm();">
			<input type="hidden" name="method" value="regist">
			<table>
				<tr>
					<td class="tdleft">用户名：</td>
					<td class="tdmiddle"><input class="inputclass" type="text" name="userName" id="userName" value="${form.userName }"></td>
					<td class="tdright" id="userNameError"><label class="errorWarn" >${errors.userName }</label></td>
				</tr>
				<tr>
					<td class="tdleft">密码：</td>
					<td class="tdmiddle"><input class="inputclass" type="password" name="userPassword" id=userPassword value="${form.userPassword }"></td>
					<td class="tdright" id="userPasswordError"><label class="errorWarn" >${errors.userPassword }</label></td>
				</tr>
				<tr>
					<td class="tdleft">确认密码：</td>
					<td class="tdmiddle"><input class="inputclass" type="password" name="repeatPassword" id="repeatPassword" value="${form.repeatPassword }"></td>
					<td class="tdright" id="repeatPasswordError"><label class="errorWarn" >${errors.repeatPassword }</label></td>
				</tr>
				<tr>
					<td class="tdleft">邮箱：</td>
					<td class="tdmiddle"><input class="inputclass" type="text" name="email" id="email" value="${form.email }"></td>
					<td class="tdright" id="emailError"><label class="errorWarn" >${errors.email }</label></td>
				</tr>
				<tr>
					<td class="tdleft">验证码：</td>
					<td class="tdmiddle"><input class="inputclass" type="text" name="VerifyCode" id="VerifyCode"></td>
					<td class="tdright" id="VerifyCodeError"><label class="errorWarn" >${errors.VerifyCode }</label></td>
				</tr>
				<tr>
					<td class="tdleft"></td>
					<td class="tdmiddle"><div id="VerifyCodediv"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/></div></td>
					<td class="tdright"><a href="javascript:changeImg()">换一张</a></td>
				</tr>
				<tr>
					<td class="tdleft"></td>
					<td class="tdmiddle"><a id="button" onclick="$('#registerform').submit()">开启征途</a></td>
				</tr>
			</table>
		  </form>
		</div>
	</div>

</body>
</html>