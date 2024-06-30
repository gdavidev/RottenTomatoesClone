package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import helpers.files.FileSystemHelper;
import models.Movie;

public class DAOMovies extends DAO {
	public static enum SearchBy {
		NONE 			(-1),
		TITLE 			(0),
		DIRECTOR 		(1),
		GENRE 			(2),		
		RATING_AVG 		(3),
		RATING_COUNT 	(9);
		Integer currentValue;
		
		SearchBy(int SearchByValue) {
			this.currentValue = SearchByValue;
		}
		
		public static final SearchBy valueOf(Integer value) {
			return Arrays.stream(SearchBy.values())
					.filter(searchBy -> searchBy.currentValue.equals(value))
					.findFirst().orElse(NONE);
		}
	}
	
	public static enum SortBy {
		NAME 		(0),
		MOST_RATED 	(1),
		BEST_RATING (2);
		
		Integer currentValue;
		SortBy(int SortByValue) {
			this.currentValue = SortByValue;
		}
		
		public static final SortBy valueOf(Integer value) {
			return Arrays.stream(SortBy.values())
					.filter(sortBy -> sortBy.currentValue.equals(value))
					.findFirst().orElse(MOST_RATED);
		}
	}	
	
	public ArrayList<Movie> getMovie(SortBy sortParameter) {
		return getMovie(0, sortParameter, SearchBy.NONE, "");
	}
	
	public ArrayList<Movie> getMovie(int amount, SortBy sortParameter) {
		return getMovie(amount, sortParameter, SearchBy.NONE, "");
	}
	
	public ArrayList<Movie> getMovie(SortBy sortParameter, SearchBy searchParameter, String search) {
		return getMovie(0, sortParameter, searchParameter, search);
	}
	
	public ArrayList<Movie> getMovie(String sortParameter, String searchParameter, String search) {
		SortBy sortBy 		= sortParameter 	!= null ? SortBy.valueOf(Integer.valueOf(sortParameter)) 		: SortBy.NAME;
		SearchBy searchBy 	= searchParameter 	!= null ? SearchBy.valueOf(Integer.valueOf(searchParameter)) 	: SearchBy.TITLE;
		String searchString	= search		 	!= null ? search										 		: "";
		
		return getMovie(0, sortBy, searchBy, searchString);
	}
	
	public ArrayList<Movie> getMovie(int amount, SortBy sortParameter, SearchBy searchParameter, String search) {
		ArrayList<Movie> list = new ArrayList<Movie>();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " LEFT JOIN ratings AS r ON r.movieId = m.id "
				+ makeWhereClause(searchParameter, search)
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero "
				+ makeHavingClause(searchParameter, search)
				+ makeOrderByClause(sortParameter)
				+ makeLimitClause(amount);
		
		try {
			ResultSet rs = this.dbQuery.query(query);
			while(rs.next()) {
				Movie movie = new Movie(
					rs.getInt("m.id"),
					rs.getString("m.titulo"),
					rs.getString("m.diretor"),
					rs.getString("m.genero"),
					rs.getInt("ratingCount"),
					rs.getFloat("ratingAverage")
				);
				list.add(movie);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return new ArrayList<Movie>();
	}
	
	private String makeWhereClause(SearchBy searchParameter, String search) {
		String where = "";
		if (search.trim() != "") {
			switch (searchParameter) {
				case TITLE:			where += " WHERE m.titulo LIKE '%" + search + "%' "; 		break;
				case DIRECTOR: 		where += " WHERE m.diretor LIKE '%" + search + "%' "; 		break;
				case GENRE:			where += " WHERE m.genero LIKE '%" + search + "%' "; 		break;
				default:			break;
			}
		}
		return where;
	}
	
	private String makeOrderByClause(SortBy sortParameter) {
		String orderBy = "";	
		switch (sortParameter) {
			case MOST_RATED: 	orderBy += " ORDER BY ratingCount DESC "; 		break;
			case BEST_RATING: 	orderBy += " ORDER BY ratingAverage DESC ";		break;
			case NAME: 			orderBy += " ORDER BY m.titulo ASC ";			break;
		}
		return orderBy;
	}	
	
	private String makeHavingClause(SearchBy searchParameter, String search) {
		String having = "";
		if (search.trim() != "") {
			switch(searchParameter) {
				case RATING_AVG:	having = " HAVING ratingAverage >= " + search + " "; 	break;
				case RATING_COUNT:	having = " HAVING ratingCount >= " + search + " "; 		break;
				default:			break;		
			}
		}
		return having;
	}
	
	private String makeLimitClause(int amount) {
		if (amount > 0)
			return " LIMIT " + amount;		
		return "";
	}
	
	public Movie getMovie(int id) {
		Movie movie = new Movie();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " LEFT JOIN ratings AS r ON r.movieId = m.id "
				+ " WHERE id = " + String.valueOf(id) + " "
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero;";
		
		try {
			ResultSet rs = this.dbQuery.query(query);
			if (rs.next()) {
				movie = new Movie(
					rs.getInt("m.id"),
					rs.getString("m.titulo"),
					rs.getString("m.diretor"),
					rs.getString("m.genero"),
					rs.getInt("ratingCount"),
					rs.getFloat("ratingAverage")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movie;
	}
	
	public void LoadMovieListTumbnailPaths(String serverContextPath, String directoryRealPath, ArrayList<Movie> movieList) {
		for (Movie movie : movieList)
			LoadMovieTumbnailPath(serverContextPath, directoryRealPath, movie);
	}
	
	public void LoadMovieTumbnailPath(String serverContextPath, String directoryRealPath, Movie movie) {
		String realTumbnailPath = FileSystemHelper.getFileFullPath(directoryRealPath, String.valueOf(movie.id));
		String serverTumbnailPath = FileSystemHelper.FileAbsolutePathToServerPath(serverContextPath, realTumbnailPath);
		movie.tumbnailPath = serverTumbnailPath;
	}
	
	public void store(Movie movie) {
		if (movie.id == 0)
			insertMovie(movie);
		else
			updateMovie(movie);			
	}
	
	private void insertMovie(Movie movie) {
		String query = 
				" INSERT INTO Movies(titulo, diretor, genero) "
				+ " VALUES('"+ movie.title +"', '"+ movie.director +"', '"+ movie.genre +"');";
		String queryLastId = " SELECT LAST_INSERT_ID() AS lastInsertId; ";
		
		try {
			this.dbQuery.execute(query);
			ResultSet rs = this.dbQuery.query(queryLastId);
			if (rs.next()) {
				movie.id = rs.getInt("lastInsertId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void updateMovie(Movie movie) {
		String query = 
				" UPDATE Movies SET "
				+ " titulo = '"+ movie.title +"',"
				+ " diretor = '"+ movie.director +"',"
				+ " genero = '"+ movie.genre +"'"
				+ " WHERE id = " + movie.id;
		this.dbQuery.execute(query);
	}
}
