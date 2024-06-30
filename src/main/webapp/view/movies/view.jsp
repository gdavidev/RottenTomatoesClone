<%@ page import="models.Movie"%>
<%@ page import="dataAccess.DAORates"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/defaultPageHead.jsp" %>
    <%
        Movie movie = (Movie) request.getAttribute("movie");
    	int rate = Math.round(movie.ratingAverage);
    	DAORates daoRate = new DAORates();
    %>
    <title>Rotten Tomatoes | <%= movie.title %></title>
</head>
<body>
    <%@ include file="/view/common/navbar.jsp" %>

    <div class="container mt-5">
        <div class="row w-50 mx-auto">
            <!-- Left side: Movie image -->
            <div class="col">
            	<% String imgPath = movie.tumbnailPath != null && movie.tumbnailPath != "" ? movie.tumbnailPath : "https://placehold.co/540x540.jpg"; %>
                <img src="<%=imgPath %>" style="height: 60vh" class="img-fluid" alt="<%= movie.title %> Poster">
            </div>
            <!-- Right side: Movie information -->
            <div class="col">
                <h2><%= movie.title %></h2>
                <p><strong>Director:</strong> <%= movie.director %></p>
                <p><strong>Genre:</strong> <%= movie.genre %></p>
                <p><strong>Rating Count:</strong> <%= movie.ratingCount %></p>
                <p><strong>Rating Average:</strong> <%= movie.ratingAverage %></p>
                
                <!-- Rating Stars -->
                <div id="ratings" class="w-100 d-flex align-items-center">
					<div id="star-container" class="d-inline-block text-align-end">
						<div id="star-bg-fill" style="margin-bottom: -20px; background-color: #222; width: 104px; height: 20px;"></div>
						<div id="star-fill" style="margin-bottom: -20px; background-color: #ff0; width: <%=((movie.ratingAverage / 2) * 20.8) %>px; height: 20px;"></div>
						<div id="star-outline" class="d-flex">
							<%for (int i = 0; i < 5; i++) { %>
								<svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/2000/xlink" width="21px" height="20px">
									<path d="M0,0.054V20h21V0.054H0z M15.422,18.129l-5.264-2.768l-5.265,2.768l1.006-5.863L1.64,8.114l5.887-0.855
										l2.632-5.334l2.633,5.334l5.885,0.855l-4.258,4.152L15.422,18.129z" fill="#FFF"/>
								</svg>
							<%} %>
						</div>
					</div>
				</div>
				
				<% 
				if (request.getSession().getAttribute("loggedUser") != null) {
					%><div class="mt-5">
					    <a href="<%=request.getContextPath() %>/movies/rate?movieID=<%= movie.id %>"><button type="button" class="btn btn-primary" style="width: 150px;">
					    <%	
					    	User user = (User) request.getSession().getAttribute("loggedUser");
					    	Boolean avaliado = daoRate.verificaAvaliacao(movie.id, user.id);
					    	if (!avaliado) {
					    		%>Avaliar<%
					    	} else {
					    		%>Reavaliar<%
					    	}
					    %>
					    </button></a>
					</div><%
				}
				%>

                
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
