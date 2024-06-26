<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/view/common/defaultPageHead.jsp" %>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	<title>Login/Sign-in</title>
</head>
<body>
	<%@ include file="/view/common/navbar.jsp" %>
	<br>
	<br>
	<br>
	<div class="container mt-5">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<h2 class="text-center"><%= request.getAttribute("action").equals("login") ? "Login" : "Register" %></h2>
					</div>
					<div class="card-body">
						<form action="<%= request.getServletPath() %>" method="post">
							<% if (request.getAttribute("action").equals("register")) { %>
								<div class="form-group">
									<label for="name">Name:</label>
									<input type="text" id="name" name="name" class="form-control" required>
								</div>
							<% } %>
							<div class="form-group">
								<label for="email">Email:</label>
								<input type="email" id="email" name="email" class="form-control" required>
							</div>
							<div class="form-group">
								<label for="password">Password:</label>
								<input type="password" id="password" name="password" class="form-control" required>
							</div>
							<div class="form-group text-center">
								<button type="submit" class="btn btn-primary btn-block">
									<%= request.getAttribute("action").equals("login") ? "Login" : "Register" %>
								</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	

	<%@ include file="/view/common/footer.jsp" %>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
