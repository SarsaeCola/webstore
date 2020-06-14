<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
var logoutIp="${ipAddrAdminLogout}";
var loginTime="${loginTimeAdmin}";
var logoutTime="${logoutTimeAdmin}";
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
		function checkForm() {
			if(!$("#adminName").val()) {
				alert("管理员名称不能为空！");
				return false;
			}
			if(!$("#adminPassword").val()) {
				alert("管理员密码不能为空！");
				return false;
			}
			return true;
		}
</script>
<style>
*{
padding:0px;
margin:0px;
}
a{color:White}
body{
font-family:Arial, Helvetica, sans-serif;
font-size:13px; 
}
.title{
text-align: center;
}
.lg{width:468px; height:468px; margin:100px auto;}
.lg_top{ height:200px; width:468px;}
.lg_main{width:400px; height:180px; margin:0 25px;}
.lg_m_1{
width:290px;
height:100px;
padding:60px 55px 20px 55px;
}
#adminName{
height:37px;
border:0;
color:#666;
width:236px;
margin:4px 28px;
padding-left:10px;
font-size:16pt;
font-family:Arial, Helvetica, sans-serif;
border: 1px solid #00FF00;
border-radius: 4px;
}
#adminPassword{
height:37px;
border:0;
color:#666;
width:236px;
margin:4px 28px;
padding-left:10px;
font-size:16pt;
font-family:Arial, Helvetica, sans-serif;
border: 1px solid #00FF00;
border-radius: 4px;
}
.bn{
background-color: #00BFFF;
width:330px; 
height:50px; 
border:0; 
display:block; 
font-size:18px; 
color:#FFF; 
font-family:Arial, Helvetica, sans-serif; 
font-weight:bolder;
}
.lg_foot{
height:80px;
width:330px;
padding: 6px 68px 0 68px;
}
</style>
<title>刀山剑林管理员登陆</title>
</head>
<body class="b">
<h1 class="title">销售员登录页面</h1>
<hr/>
  <p style="font-weight: 900; color: red">${msg }</p>
<div class="lg">
<form action="<c:url value='/AdminServlet'/>" method="post" onsubmit="return checkForm()">
<input type="hidden" name="method" value="login"/>
<div class="lg_top"></div>
<div class="lg_main">
<div class="lg_m_1">
<input type="text" name="adminName" id="adminName" placeholder="管理员账户"/><br/>
<input type="password" name="adminPassword" id="adminPassword" placeholder="密码"/><br/>
</div>
</div>
<div class="lg_foot">
<input type="submit" value="进入后台" class="bn"/>
</div>
</form>
</div>
</body>
</html>

