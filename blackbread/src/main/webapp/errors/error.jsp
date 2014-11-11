<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PrintWriter pt = response.getWriter();
	pt.print("{\"success\":false,\"message\":\""
			+ ((Exception) request.getAttribute("ex")).getMessage()
			+ "\"}");
	pt.flush();
	pt.close();
%>