<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/formItems.tld" prefix="formItem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="models.Movie"%>
<%@ page import="dataAccess.DAOMovies.SearchBy"%>
<!DOCTYPE html>
<html>
<head>
	<%
		ArrayList<Movie> movieList = (ArrayList<Movie>) request.getAttribute("movieList");
		String[] orderByOptions = {"Nome", "Mais avaliados", "Melhor avaliados"};
		String[] searchByOptions = {"Titulo", "Diretor", "Gênero", "Média de avaliações"};
		
		String search = 				(request.getParameter("search") 		== null ? "" 	: request.getParameter("search"));
		String selectedIndexSortBy = 	(request.getParameter("sortBy") 		== null ? "0" 	: request.getParameter("sortBy"));
		String selectedIndexSerchBy = 	(request.getParameter("searchBy") 		== null ? "0" 	: request.getParameter("searchBy"));
		String avgRatingRange = 		(request.getParameter("avgRatingRange") == null ? "0" 	: request.getParameter("avgRatingRange"));
	%>
	<%@ include file="/view/common/defaultPageHead.jsp" %>
	<title>Procurar Filmes</title>
</head>
<body>
	<%@ include file="/view/common/navbar.jsp" %>	
	
	<main style="min-height: calc(100vh - (169px + 56px));" class="px-5 py-2">
		
		<form action="all" method="GET">
			<div class="d-flex column-gap-2">
				<div class="d-flex column-gap-2">
					<formItem:comboBox label="Pesquisar em:" name="searchBy" onChange="toggleSearchMethod(this.value)" dataSource="<%=Arrays.toString(searchByOptions) %>" selectedIndex="<%=Integer.valueOf(selectedIndexSerchBy) %>" />
					<formItem:textField label="Pesquisar:" name="search" value="<%=search %>" />
					<div id="avgRatingRangeContainer" style="display: flex;" class="flex-column">
						<label id="avgRatingRangeLabel" for="rateRange" class="form-label mb-3">Example range</label>
						<input id="avgRatingRange" name="avgRatingRange" value="<%=avgRatingRange %>" style="width: 207px;" type="range" onchange="$('#avgRatingRangeLabel').html('Média >= ' + this.value)" class="form-range" min="0" max="10" step="0.5" id="rateRange" name="rateRange">
					</div>
					<formItem:comboBox label="Ordernar:" name="sortBy" dataSource="<%=Arrays.toString(orderByOptions) %>" selectedIndex="<%=Integer.valueOf(selectedIndexSortBy) %>" />
				</div>
				<input type="submit" class="btn btn-primary" style="height: fit-content; margin-top: 32px;" value="Pesquisar">
			</div>
		</form>
		
		<div class="d-flex flex-wrap column-gap-2 row-gap-2">
			<%for (Movie movie : movieList) { %>
				<div class="card" style="width: 14rem;">
					<img src="https://placehold.co/30x40" class="card-img-top" alt="...">
					<div class="card-body">
						<div class="mb-2">
							<h5 style="margin: 0 !important;" class="card-title text-truncate"><a href="view?movieID=<%= movie.id %>" style="text-decoration: none; color: inherit;"><%= movie.title %></a></h5>
							<p style="margin: 0 !important;" class="card-text fs-6 text-info-emphasis text-truncate"><%=movie.director %></p>
							<p style="margin: 0 !important;" class="card-text fs-7 text-secondary text-truncate"><%=movie.genre %></p>
						</div>
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

	</main>
	
	<%@ include file="/view/common/footer.jsp" %>
</body>
	<script type="text/javascript">
		$(document).ready(() => {
			toggleSearchMethod($("#searchBy").find(":selected").val());
			$("#avgRatingRange").trigger("change");
		});		
	
		function toggleSearchMethod(selectedValue) {
			if (selectedValue == 3) {
				$("#searchContainer").hide();
				$("#avgRatingRangeContainer").show();
				$("#search").val("");
			} else {
				$("#searchContainer").show();
				$("#avgRatingRangeContainer").hide();
				$("#avgRatingRange").val("0");
				$("#avgRatingRange").trigger("change");
			}
			
		}		
	</script>
</html>