package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;
import dataAccess.DAOMovies.SortBy;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("bestRatingMovieList", daoMovies.getMovie(10, SortBy.BEST_RATING));
		request.setAttribute("mostRatedMovieList", daoMovies.getMovie(10, SortBy.MOST_RATED));
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
