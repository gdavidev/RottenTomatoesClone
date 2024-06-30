<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/formItems.tld" prefix="formItem"%>
<%@ page import="models.Movie"%>
<!DOCTYPE html>
<html>
<head>
	<% 
		String positiveMessage = (String) request.getAttribute("positiveMessage");
		String negativeMessage = (String) request.getAttribute("negativeMessage");
		
		Movie movie = (Movie) request.getAttribute("movie");
		String imgPath = movie.tumbnailPath != null && movie.tumbnailPath != "" ? movie.tumbnailPath : "https://placehold.co/30x40";
		boolean isImgFileRequired = movie.tumbnailPath == "";
	%>

	<%@ include file="/view/common/defaultPageHead.jsp"%>
	<title>Insert title here</title>
</head>
<body class="overflow-x-hidden">
	<%@ include file="/view/common/navbar.jsp"%>

	<main style="min-height: calc(100vh - (169px + 56px));" class="px-5 py-2">
		
		<form action="<%=request.getContextPath() %>/movies/edit" method="POST" enctype="multipart/form-data">
			<div class="align-center w-75 mx-auto mt-5">
				<div class="row mb-2">
					<div class="col-3">						
						<img id="movieTumbnail" src="<%=imgPath %>" style="height: 260px; width: 200px;" class="img-thumbnail" alt="...">
					</div>
					<div class="col">
						<formItem:fileInput label="Poster:" name="poster" accept="image/png, image/jpeg, image/webp" onchange="fileUploaded()" required="<%=isImgFileRequired %>" />
						<formItem:textField label="Title:" name="title" required="true" value="<%=movie.title %>" />
						<div class="row">
							<div class="col">
								<formItem:textField label="Director:" name="director" required="true" value="<%=movie.director %>" />
							</div>
							<div class="col">
								<formItem:textField label="Genre:" name="genre" required="true" value="<%=movie.genre %>" />				
							</div>
						</div>
					</div>
				</div>
				<%if (negativeMessage != null) { %>
					<div class="p-2 mb-1 bg-danger-subtle border-start border-danger text-black">
						<span><%=negativeMessage %></span>
					</div>
				<%} else if (positiveMessage != null) { %>
					<div class="p-2 mb-1 bg-success-subtle border-start border-success text-black">
						<span><%=positiveMessage %></span>
					</div>
				<%} %>
				<div class="d-flex justify-content-end column-gap-2">
					<input type="submit" class="btn btn-primary" value="Confirmar">
					<a href="<%=request.getContextPath() %>/home" class="btn btn-secondary">Cancelar</a>
				</div>
			</div>
			<input type="hidden" id="movieID" name="movieID" value="<%=movie.id %>">
		</form>
		
	</main>
	
	<%@ include file="/view/common/footer.jsp"%>
</body>
	<script>
		function fileUploaded() {
			let imgTarget = document.querySelector("#movieTumbnail");
			let file = document.querySelector("#poster").files[0];
			let reader = new FileReader();
			
			reader.onloadend = function () {
				imgTarget.src = reader.result;
			};
			
			if (file) {
				reader.readAsDataURL(file);
			} else {
				target.src = "";
			}			
		}
	</script>
</html>