<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="models.Movie"%>
<!DOCTYPE html>
<html>
<head>
	<%
		ArrayList<Movie> bestRatingMovieList = (ArrayList<Movie>) request.getAttribute("bestRatingMovieList");
		ArrayList<Movie> mostRatedMovieList = (ArrayList<Movie>) request.getAttribute("mostRatedMovieList");
	%>
	<%@ include file="/view/common/defaultPageHead.jsp"%>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />
	<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
	<title>Insert title here</title>

	<style>
		.swiper {
			width: 100%;
			height: 108%;
		}
		
		.swiper-slide {
			position: relative;
			margin-right: 12px;
		}
		
		.swiper-slide img {
			display: block;
			width: 100%;			
		}
	</style>
</head>
<body class="overflow-x-hidden">
	<%@ include file="/view/common/navbar.jsp"%>

	<main style="min-height: calc(100vh - (169px + 56px));" class="px-5 py-2">
		
		<div class="d-flex flex-wrap column-gap-2 row-gap-2">
			<%for (Movie movie : bestRatingMovieList) { %>
				<div class="card" style="width: 14rem;">
					<img src="https://placehold.co/30x40" class="card-img-top" alt="...">
					<div class="card-body">
						<a href="view?movieID=<%= movie.id %>" style="text-decoration: none; color: inherit;"><h5 class="card-title"><%= movie.title %></h5></a>
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
					<%} %>
				</div>
				<div class="swiper-button-next"></div>
			    <div class="swiper-button-prev"></div>
			</div>
		</div>
		
		<h2>Mais avaliados:</h2>
		<div class="d-flex flex-wrap column-gap-2 row-gap-2">
			<div class="swiper mySwiper">
				<div class="swiper-wrapper">
					<%for (Movie movie : mostRatedMovieList) { %>
						<div class="card swiper-slide">
							<img src="https://placehold.co/30x40" class="card-img-top" alt="...">
							<div class="card-body">
								<h5 class="card-title text-truncate"><%= movie.title %></h5>
								<p class="card-text text-truncate">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
								<div id="ratings" class="w-100 d-flex align-items-center justify-content-end">
									<p style="margin: 0; padding: 0"><%=(movie.ratingAverage * 10) %> %</p>
									<div id="star-container" class="d-inline-block text-align-end">
										<div id="star-bg-fill" style="margin-bottom: -20px; background-color: #222; width: 100px; height: 20px;"></div>
										<div id="star-fill" style="margin-bottom: -20px; background-color: #ff0; width: <%=((movie.ratingAverage / 2) * 20) %>px; height: 20px;"></div>
										<div id="star-outline" class="d-flex">
											<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"><path d="M0 .054V20h21V.054zm15.422 18.075-5.264-2.768-5.265 2.768 1.006-5.863L1.64 8.114l5.887-.855 2.632-5.334 2.633 5.334 5.885.855-4.258 4.152z" fill="#FFF"/></svg><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"><path d="M0 .054V20h21V.054zm15.422 18.075-5.264-2.768-5.265 2.768 1.006-5.863L1.64 8.114l5.887-.855 2.632-5.334 2.633 5.334 5.885.855-4.258 4.152z" fill="#FFF"/></svg><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"><path d="M0 .054V20h21V.054zm15.422 18.075-5.264-2.768-5.265 2.768 1.006-5.863L1.64 8.114l5.887-.855 2.632-5.334 2.633 5.334 5.885.855-4.258 4.152z" fill="#FFF"/></svg><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"><path d="M0 .054V20h21V.054zm15.422 18.075-5.264-2.768-5.265 2.768 1.006-5.863L1.64 8.114l5.887-.855 2.632-5.334 2.633 5.334 5.885.855-4.258 4.152z" fill="#FFF"/></svg><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"><path d="M0 .054V20h21V.054zm15.422 18.075-5.264-2.768-5.265 2.768 1.006-5.863L1.64 8.114l5.887-.855 2.632-5.334 2.633 5.334 5.885.855-4.258 4.152z" fill="#FFF"/></svg>
										</div>
									</div>
									<p style="margin: 0; padding: 0;">(<%=movie.ratingCount %>)</p>
								</div>
							</div>
						</div>
					<%} %>
				</div>
				<div class="swiper-button-next"></div>
			    <div class="swiper-button-prev"></div>
			</div>
		</div>

	</main>
	
	<%@ include file="/view/common/footer.jsp"%>

	<script>   
		var swiper = new Swiper(".mySwiper", {
			slidesPerView : 6,
			centeredSlides : false,
			slidesPerGroupSkip : 1,
			grabCursor : true,
			keyboard : {
				enabled : false,
			},
			navigation : {
				nextEl : ".swiper-button-next",
				prevEl : ".swiper-button-prev",
			},
		});
	</script>
</body>
</html>