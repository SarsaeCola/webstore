<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>错误操作</title>
</head>
<style type="text/css">
	body {background: rgb(254,238,189);}
</style>
  <body>
<h2>${msg }</h2>
<input type="button" value="返回" onclick="history.go(-1)"/>
  </body>
</html>