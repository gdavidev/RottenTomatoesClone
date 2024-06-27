<%@ page import="models.Movie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/defaultPageHead.jsp" %>
    <%
        Movie movie = (Movie) request.getAttribute("movie");
    %>
    <title>Rotten Tomatoes | <%= movie.title %></title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="/view/common/navbar.jsp" %>
    
    <div class="container mt-5">
        <div class="row">
            <!-- Left side: Movie image -->
            <div class="col-md-6">
                <img src="https://placehold.co/540x540.jpg" class="card-img-top" alt="<%= movie.title %> Poster">
            </div>
            <!-- Right side: Movie information -->
            <div class="col-md-6">
                <h2><%= movie.title %></h2>
                <p><strong>Director:</strong> <%= movie.director %></p>
                <p><strong>Genre:</strong> <%= movie.genre %></p>
                <p><strong>Rating Count:</strong> <%= movie.ratingCount %></p>
                <p><strong>Rating Average:</strong> <%= movie.ratingAverage %></p>
            </div>
        </div>
    </div>
    
    <%@ include file="/view/common/footer.jsp" %>
    
    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
