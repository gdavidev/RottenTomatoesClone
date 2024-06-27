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

@WebServlet(name="MovieViewServlet", urlPatterns={"/view"})
public class MovieViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovie = new DAOMovies();
	DAORates daoRate = new DAORates();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieID = Integer.parseInt(request.getParameter("movieID"));
		System.out.println(movieID);
		Movie movie = daoMovie.getMovie(movieID);
		request.setAttribute("movie", movie);
		System.out.println(movie.title);
		
		if (request.getSession().getAttribute("loggedUser") != null) {
			User user = (User) request.getSession().getAttribute("loggedUser");
			Boolean avaliado = daoRate.verificaAvaliacao(movie.id, user.id);
			request.setAttribute("avaliado", avaliado);
		}
		
		
		getServletContext().getRequestDispatcher("/view/movies/view.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(request, response);
	}
}
