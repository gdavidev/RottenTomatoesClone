package controllers;

import java.io.IOException;

import dataAccess.DAOMovies;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Movie;

@WebServlet(name="MovieRateServlet", urlPatterns={"/rate"})
public class MovieRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovie = new DAOMovies();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieID = Integer.parseInt(request.getParameter("movieID"));
		System.out.println(movieID);
		Movie movie = daoMovie.getMovie(movieID);
		request.setAttribute("movie", movie);
		
		getServletContext().getRequestDispatcher("/view/movies/view.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(request, response);
	}
}
