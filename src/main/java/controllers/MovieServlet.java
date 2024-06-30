package controllers;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import dataAccess.DAOMovies;
import dataAccess.DAOMovies.SearchBy;
import dataAccess.DAOMovies.SortBy;
import helpers.files.FileSystemHelper;
import models.Movie;
import models.User;

@WebServlet(name="/MovieServlet", urlPatterns={"/movies/all", "/movies/edit"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOMovies daoMovies = new DAOMovies();
	private String movieTumbnailDir;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		movieTumbnailDir = getServletContext().getRealPath("/") + "media\\movieTumbnails\\";
		
		switch (request.getServletPath()) {
			case "/movies/edit": 	{ processMovieEditGetRequest(request, response);		break; } 
			case "/movies/all": 	{ processMovieSearchGetRequest(request, response); 		break; }
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		movieTumbnailDir = getServletContext().getRealPath("/") + "media\\movieTumbnails\\";
		
		switch (request.getServletPath()) {
			case "/movies/edit": { processMovieEditPostRequest(request, response);	break; }
		}		
	}
	
// PROCESS POST REQUESTS //
	private void processMovieEditPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieId 	= request.getParameter("movieID");
		String title 	= request.getParameter("title");
		String director = request.getParameter("director");
		String genre 	= request.getParameter("genre");
		
		System.out.println(movieId + " " + title + " " + director + " " + genre + " ");
		System.out.println("file: " + request.getPart("poster"));
		
		if (title == null 	|| director == null 	|| genre == null ||
			title.isBlank() || director.isBlank() 	|| genre.isBlank()) {
			Movie movie = new Movie(
					Integer.valueOf(movieId),
					(title 		!= null ? title 	: ""),
					(director	!= null ? director 	: ""),
					(genre 		!= null ? genre 	: "")
				);
			request.setAttribute("movie", movie);
			request.setAttribute("negativeMessage", "Preencha todos os campos.");
			doGet(request, response);
			return;
		}		
		
		Movie movie = new Movie(Integer.valueOf(movieId), title, director, genre);		
		daoMovies.store(movie);
		
		Part part = request.getPart("poster");
		String fileName = extractFileName(part);
		if (fileName != null && !fileName.isBlank()) {
			String fileExtension = fileName.substring(fileName.indexOf('.'),  fileName.length());
			String finalFileName = String.valueOf(movie.id) + fileExtension;
			part.write(FileSystemHelper.makeSaveFilePath(movieTumbnailDir, finalFileName));
		}

		request.setAttribute("movie", movie);
		request.setAttribute("positiveMessage", "Filme gravado com sucesso!");
		getServletContext().getRequestDispatcher("/index").forward(request, response);		
	}
	
// PROCESS GET REQUESTS //
	private void processMovieEditGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isUserAllowed((User) request.getSession().getAttribute("loggedUser"))) {
			getServletContext().getRequestDispatcher("/index").forward(request, response);
			return;
		}
			
		String movieIdParameter = request.getParameter("movieID");
		if (movieIdParameter != null) {
			Movie movie = daoMovies.getMovie(Integer.valueOf(movieIdParameter));
			daoMovies.LoadMovieTumbnailPath(request.getContextPath(), movieTumbnailDir, movie);
			request.setAttribute("movie", movie);
		} else {
			request.setAttribute("movie", new Movie());
		}
		getServletContext().getRequestDispatcher("/view/admin/movieEdit.jsp").forward(request, response);
	}
	
	private void processMovieSearchGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		String sortByValue		= request.getParameter("sortBy");
		String searchByValue 	= request.getParameter("searchBy");
		String search 			= request.getParameter("search");
		String avgRating 		= request.getParameter("avgRatingRange");
		if (searchByValue != null && SearchBy.valueOf(Integer.valueOf(searchByValue)) == SearchBy.RATING_AVG) {
			movieList = daoMovies.getMovie(SortBy.valueOf(Integer.valueOf(sortByValue)), SearchBy.RATING_AVG, avgRating);
		} else {
			movieList = daoMovies.getMovie(sortByValue, searchByValue, search);			
		}			
		daoMovies.LoadMovieListTumbnailPaths(request.getContextPath(), movieTumbnailDir, movieList);
		
		request.setAttribute("movieList", movieList);
		getServletContext().getRequestDispatcher("/view/movies/all.jsp").forward(request, response);
	}
	
// HELPER FUNCTIONS //
	private boolean isUserAllowed(User user) {
		if (user == null || user.admin == 0)
			return false;
		return true;
	}
		
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
