package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;

@WebServlet(name = "/mainServlet", urlPatterns = { "/index", "/home", "/welcome", "/" })
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("bestRatingMovieList", daoMovies.getMovie(10, DAOMovies.SortBy.BEST_RATING));
		request.setAttribute("mostRatedMovieList", daoMovies.getMovie(10, DAOMovies.SortBy.MOST_RATED));
		getServletContext().getRequestDispatcher("/view/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
