package controllers;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;
import models.Movie;

@WebServlet(name = "/mainServlet", urlPatterns = { "/index", "/home", "/welcome" })
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies();
	private String movieTumbnailDir;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		movieTumbnailDir = getServletContext().getRealPath("/") + "media\\movieTumbnails\\";
		
		ArrayList<Movie> bestRatingMovieList = daoMovies.getMovie(10, DAOMovies.SortBy.BEST_RATING);
		ArrayList<Movie> mostRatedMovieList = daoMovies.getMovie(10, DAOMovies.SortBy.MOST_RATED);
		daoMovies.LoadMovieListTumbnailPaths(request.getContextPath(), movieTumbnailDir, bestRatingMovieList);
		daoMovies.LoadMovieListTumbnailPaths(request.getContextPath(), movieTumbnailDir, mostRatedMovieList);

		request.setAttribute("bestRatingMovieList", bestRatingMovieList);
		request.setAttribute("mostRatedMovieList", mostRatedMovieList);
		getServletContext().getRequestDispatcher("/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
