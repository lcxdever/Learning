<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>未找到页面</title>
<jsp:include page="/static/pages/bootstrap.jsp"></jsp:include>
</head>
<body style="text-align: center;">
	<div class="jumbotron">
		<h1>404  此页面或资源未找到</h1>
		<p style="margin-top: 50px">
			<a class="btn btn-primary btn-lg" href="<%=request.getContextPath()%>/index"  role="button">返回主页</a>
		</p>
	</div>
</body>
</html>