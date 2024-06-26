package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataAccess.DAOMovies;
import dataAccess.DAOMovies.SortBy;

@WebServlet(name="/MovieServlet",
			urlPatterns={"/movies/view", "/movies/all", "/movies/edit"})
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAOMovies daoMovies = new DAOMovies(); 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		switch (request.getServletPath()) {
			case "/movies/edit":
				/*VALIDATE USER*/
				int movieId = Integer.valueOf(request.getParameter("id"));
				request.setAttribute("movie", daoMovies.getMovie(movieId));
				getServletContext().getRequestDispatcher("/view/admin/movieEdit.jsp").forward(request, response);
				break;
			case "/movies/view":
				request.setAttribute("movie", daoMovies.getMovie((int) request.getAttribute("id")));
				break;
			case "movies/all":
				request.setAttribute("movieList", daoMovies.getMovie(SortBy.NAME));
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
