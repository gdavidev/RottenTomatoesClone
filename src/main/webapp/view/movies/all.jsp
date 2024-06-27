<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Movie"%>
<!DOCTYPE html>
<html>
<head>
	<%
		ArrayList<Movie> movieList = (ArrayList<Movie>) request.getAttribute("movieList");
	%>
	<%@ include file="/view/common/defaultPageHead.jsp" %>
	<title>Insert title here</title>
</head>
<body>
	<%@ include file="/view/common/navbar.jsp" %>
	
	<main style="min-height: calc(100vh - ( 169px + 56px));"
		class="px-5 py-2">
		
		<div class="d-flex flex-wrap column-gap-2 row-gap-2">
			<%for (Movie movie : movieList) { %>
				<div class="card" style="width: 14rem;">
					<img src="https://placehold.co/30x40" class="card-img-top" alt="...">
					<div class="card-body">
						<h5 class="card-title"><%= movie.title %></h5>
						<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
					</div>
					<div id="ratings" class="w-100 d-flex align-items-center">
						<p style="margin: 0; padding: 0"><%=movie.ratingAverage %> %</p>
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
						<p style="margin: 0; padding: 0">(<%=movie.ratingCount %>)</p>
					</div>
				</div>
			<%} %>
		</div>

	</main>
	
	<%@ include file="/view/common/footer.jsp" %>
</body>
</html>