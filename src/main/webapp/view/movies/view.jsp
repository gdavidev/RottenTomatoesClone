<%@ page import="models.Movie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/view/common/defaultPageHead.jsp" %>
	<% Movie movie = (Movie) request.getAttribute("movie"); %>
	<title><%= movie.title %></title>
</head>
<body>
	<%@ include file="/view/common/navbar.jsp" %>
	
	
	
	<%@ include file="/view/common/footer.jsp" %>
</body>
</html>