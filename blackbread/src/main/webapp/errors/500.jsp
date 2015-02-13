<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>系统内部错误页面</title>
<jsp:include page="/static/pages/bootstrap.jsp"></jsp:include>
</head>
<body style="text-align: center;">
	<div class="jumbotron">
		<h1>500  系统内部错误</h1>
		<p style="margin-top: 50px">错误原因：${error}</p>
		<p style="margin-top: 50px">
			<a class="btn btn-primary btn-lg" href="javascript:void(0)" onclick="history.go(-1)" role="button">返回上一步</a>
		</p>
	</div>
</body>
</html>