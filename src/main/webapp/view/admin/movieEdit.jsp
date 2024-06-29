<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/formItems.tld" prefix="formItem"%>
<%@ page import="models.Movie"%>
<!DOCTYPE html>
<html>
<head>
	<%
		Movie movie = (Movie) request.getAttribute("movie");
	%>

	<%@ include file="/view/common/defaultPageHead.jsp"%>
	<title>Insert title here</title>
</head>
<body class="overflow-x-hidden">
	<%@ include file="/view/common/navbar.jsp"%>

	<main style="min-height: calc(100vh - (169px + 56px));" class="px-5 py-2">
		
		<div class="align-center w-50 mx-auto mt-5">
			<div class="row">
				<div class="col-2">
					<img src="https://placehold.co/120x120" class="img-thumbnail" alt="...">
				</div>
				<div class="col">
					<formItem:fileInput label="Poster:" name="poster" />
				</div>
			</div>
			<div>
				<formItem:textField label="Title:" name="title" value="<%=movie.title %>" />
			</div>
			<div class="row">
				<div class="col">
					<formItem:textField label="Director:" name="director" value="<%=movie.director %>" />
				</div>
				<div class="col">
					<formItem:textField label="Genre:" name="genre" value="<%=movie.genre %>" />				
				</div>
			</div>
		</div>
		
		
	</main>
	
	<%@ include file="/view/common/footer.jsp"%>
</body>
</html>