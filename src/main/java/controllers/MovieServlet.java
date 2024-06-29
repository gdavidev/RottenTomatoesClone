package controllers;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;
import dataAccess.DAOMovies.SearchBy;
import dataAccess.DAOMovies.SortBy;
import models.Movie;

@WebServlet(name="/MovieServlet",
			urlPatterns={"/movies/view", "/movies/all", "/movies/edit", "/movies/add"})
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getServletPath()) {
			case "/movies/edit":
				int movieId = Integer.valueOf(request.getParameter("id"));
				request.setAttribute("movie", daoMovies.getMovie(movieId));
				getServletContext().getRequestDispatcher("/view/admin/movieEdit.jsp").forward(request, response);
				break;
			case "/movies/view":
				request.setAttribute("movie", daoMovies.getMovie((int) request.getAttribute("id")));
				break;
			case "/movies/all":
				request.setAttribute("movieList", ProcessRequestMovieSearch(request));
				getServletContext().getRequestDispatcher("/view/movies/all.jsp").forward(request, response);
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private ArrayList<Movie> ProcessRequestMovieSearch(HttpServletRequest request) {
		String sortByValue		= request.getParameter("sortBy");
		String searchByValue 	= request.getParameter("searchBy");
		String search 			= request.getParameter("search");
		String avgRating 		= request.getParameter("avgRatingRange");
		if (sortByValue == null && searchByValue == null && search == null)
			return daoMovies.getMovie(SortBy.NAME);
		if (searchByValue == null && search == null)
			return daoMovies.getMovie(SortBy.valueOf(Integer.valueOf(sortByValue)));
		if (searchByValue == null)
			return daoMovies.getMovie(SortBy.valueOf(Integer.valueOf(sortByValue)), SearchBy.TITLE, search);		
		if (SearchBy.valueOf(Integer.valueOf(searchByValue)) == SearchBy.RATING_AVG)
			return daoMovies.getMovie(SortBy.valueOf(Integer.valueOf(sortByValue)), SearchBy.RATING_AVG, avgRating);
			
		return daoMovies.getMovie(SortBy.valueOf(Integer.valueOf(sortByValue)), SearchBy.valueOf(Integer.valueOf(searchByValue)), search);
	}
}
