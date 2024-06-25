package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;
import dataAccess.DAOMovies.SortBy;

@WebServlet(name="/indexServlet", urlPatterns={"/index", "/home", "/welcome"})
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("bestRatingMovieList", daoMovies.getMovie(10, SortBy.BEST_RATING));
		request.setAttribute("mostRatedMovieList", daoMovies.getMovie(10, SortBy.MOST_RATED));
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
