<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String navipath = request.getContextPath();
	String navibasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ navipath + "/";
%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">后台管理系统</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="<%=navibasePath%>user/logout/">退出</a></li>
			</ul>
		</div>
	</div>
</div>