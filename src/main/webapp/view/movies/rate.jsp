<%@ page import="models.Movie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/defaultPageHead.jsp" %>
    <%
        Movie movie = (Movie) request.getAttribute("movie");
    	int rate = Math.round(movie.ratingAverage);
    %>
    <title>Rotten Tomatoes | <%= movie.title %></title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
						<h2 class="text-center"><%= movie.title %></h2>
					</div>
					<div class="card-body">
						<form action="rate" method="post">
							<div class="form-group">
								<label for="nota">Nota:</label>
								<input type="number" id="nota" name="nota" class="form-control" required min="0" max="10">
								<input type="number" id="movie" name="movie" class="form-control" hidden value="<%= movie.id %>">
							</div>
							<div class="form-group text-center">
								<button type="submit" class="btn btn-primary btn-block">Avaliar</button>
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
    
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
