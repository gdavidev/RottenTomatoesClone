package controllers;

import java.io.IOException;

import dataAccess.DAORates;
import dataAccess.DAOMovies;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Movie;
import models.User;

@WebServlet(name="MovieRateServlet", urlPatterns={"/rate"})
public class MovieRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovie = new DAOMovies();
	DAORates daoRate = new DAORates();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int movieID = Integer.parseInt(request.getParameter("movieID"));
		Movie movie = daoMovie.getMovie(movieID);
		
		request.setAttribute("movie", movie);
		
		if (request.getSession().getAttribute("loggedUser") == null) {
			getServletContext().getRequestDispatcher("/home").forward(request, response);
		}
		
		getServletContext().getRequestDispatcher("/view/movies/rate.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int nota = Integer.parseInt(request.getParameter("nota"));
		int movie = Integer.parseInt(request.getParameter("movie"));
		User user = (User) request.getSession().getAttribute("loggedUser");
		boolean success = false;
		Boolean avaliado = daoRate.verificaAvaliacao(movie, user.id);
		
		if (avaliado) {
			success = daoRate.update(movie, user.id, nota);
		} else {
        	success = daoRate.registra(movie, user.id, nota);
		}

        if (success) {
            response.sendRedirect("view?movieID=" + movie + "&registro=true");
        } else {
            response.sendRedirect("view?movieID=" + movie + "&erro=true");
        }
	}
}
