package controllers;

import java.io.IOException;

import dataAccess.DAOMovies;
import dataAccess.DAORates;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Movie;
import models.User;

@WebServlet(name="MovieViewServlet", urlPatterns={"/movies/view"})
public class MovieViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovie = new DAOMovies();
	DAORates daoRate = new DAORates();
	String movieTumbnailDir;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		movieTumbnailDir = getServletContext().getRealPath("/") + "media\\movieTumbnails\\";
		int movieID = Integer.parseInt(request.getParameter("movieID"));
		Movie movie = daoMovie.getMovie(movieID);
		daoMovie.LoadMovieTumbnailPath(request.getContextPath(), movieTumbnailDir, movie);
		request.setAttribute("movie", movie);
		
		if (request.getSession().getAttribute("loggedUser") != null) {
			User user = (User) request.getSession().getAttribute("loggedUser");
			Boolean avaliado = daoRate.verificaAvaliacao(movie.id, user.id);
			request.setAttribute("avaliado", avaliado);
		}		
		
		getServletContext().getRequestDispatcher("/view/movies/view.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
