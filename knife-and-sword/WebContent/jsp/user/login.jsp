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
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/user/login.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>css/user/login.css">
<title>刀山剑林登陆</title>
<script type="text/javascript">
	$(function() {
		//获取保存在cookie中的用户名,并解码
		var userName=window.decodeURI("${cookie.userName.value}");
		if("${requestScope.user.userName}"){
			userName="${requestScope.user.userName}";
		}
		$("#userName").val(userName);
		
		var logoutIp="${ipAddrUserLogout}";
		var loginTime="${loginTimeUser}";
		var logoutTime="${logoutTimeUser}";
		 var stime = Date.parse(new Date(loginTime.toString()));
	      var etime = Date.parse(new Date(logoutTime.toString()));
	      // 两个时间戳相差的毫秒数
	      var usedTime = etime - stime;
	      // 计算相差的天数  
	      var days = Math.floor(usedTime / (24 * 3600 * 1000));
	      // 计算天数后剩余的毫秒数
	      var leave1 = usedTime % (24 * 3600 * 1000);  
	      // 计算出小时数  
	      var hours = Math.floor(leave1 / (3600 * 1000));
	      // 计算小时数后剩余的毫秒数
	      var leave2 = leave1 % (3600 * 1000);        
	      // 计算相差分钟数
	      var minutes = Math.floor(leave2 / (60 * 1000));
	      var time = days + "天" + hours + "时" + minutes + "分";
		if(typeof(logoutIp)!="undefined"&&logoutIp!=null&&logoutIp!=""){
			alert("ip："+logoutIp+"\n退出时间："+logoutTime+"\n在线时长："+time);
		}
	});
</script>
</head>
<body>
<div id="content">
 <form target="_top" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">
 	<input type="hidden" name="method" value="login" />
	<div class="login-input-box">
		<label class="error" id="msg">${msg }</label><br>
		<i class="fa fa-user-circle-o" aria-hidden="true"></i>&nbsp;
		<input class="input" type="text" name="userName" placeholder="用户名" id="userName"><br>
		&nbsp;<label id="userNameError" class="error"></label>
	</div>
	<div class="login-input-box">
		<i class="fa fa-key" aria-hidden="true"></i>&nbsp;
		<input class="input" type="password" name="userPassword" placeholder="密码" id="userPassword"><br>
		&nbsp;<label id="userPasswordError" class="error"></label>
	</div>
	<div id="VerifyCodediv">
		<table>
			<tr>
				<td>
		<input class="input" type="text" name="VerifyCode" id="VerifyCode" value="" placeholder="验证码"/>&nbsp;
        		</td>
        		<td>
        <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>
        		</td>
        	</tr>
        	<tr>
        		<td>		
        <label id="VerifyCodeError" class="error"></label>
        		</td>
        		 <td>
        <a id="changeVerifyCode" href="javascript:changeImg()">换一张</a>
        		</td>
        	</tr>
        </table>
	</div>
 </form>
	<div class="login-button-box">
		<img id="submit" src="<%=basePath%>imgs/loginbtn.png" onclick="$('#loginForm').submit()">
	</div>
	<div class="logon-box">
		<!--  <a id="forget" href="">忘记账号/密码？</a>-->
		<a id="reg" href="<%=basePath%>jsp/user/register.jsp">注册</a>
	</div>
</div>
</body>
</html>
