package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
		MOST_RATED 	(0),
		BEST_RATING (1),
		NAME 		(2);
		
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
	
	public ArrayList<Movie> getMovie(int amount, SortBy sortParameter, SearchBy searchParameter, String search) {
		ArrayList<Movie> list = new ArrayList<Movie>();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " INNER JOIN ratings AS r ON r.movieId = m.id "
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
		String orderBy = " ORDER BY ";
		switch (sortParameter) {
			case MOST_RATED: 	orderBy += " ratingCount DESC "; 		break;
			case BEST_RATING: 	orderBy += " ratingAverage DESC ";		break;
			case NAME: 			orderBy += " m.titulo DESC ";			break;
		}
		return orderBy;
	}	
	
	private String makeHavingClause(SearchBy searchParameter, String search) {
		String having = "";
		switch(searchParameter) {
			case RATING_AVG:	having = " HAVING ratingAverage >= " + search + " "; 	break;
			case RATING_COUNT:	having = " HAVING ratingCount >= " + search + " "; 		break;
			default:			break;		
		}
		return having;
	}
	
	private String makeLimitClause(int amount) {
		if (amount > 0)
			return " LIMIT " + amount;
		else
			return "";
	}
	
	public Movie getMovie(int id) {
		Movie movie = new Movie();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " INNER JOIN ratings AS r ON r.movieId = m.id "
				+ " WHERE id = " + String.valueOf(id) + " "
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero;";
		
		try {
			ResultSet rs = this.dbQuery.query(query);
			while(rs.next()) {
				movie = new Movie(
					rs.getInt("m.id"),
					rs.getString("m.titulo"),
					rs.getString("m.diretor"),
					rs.getString("m.genero"),
					rs.getInt("ratingCount"),
					rs.getFloat("ratingAverage")
				);
		} catch (SQLException e) {
			e.printStackTrace();
			return new Movie();
		} catch (Exception e) {
			e.printStackTrace();
			return new Movie();
		}
		return movie;
	}


	
	public void store(Movie movie) {
		if (movie.id == 0)
			insertMovie(movie);
		else
			updateMovie(movie);
			
	}
	
	private void insertMovie(Movie movie) {
		String query = 
				" INSERT INTO Movies(titulo, diretor, genero)"
				+ " VALUES('"+ movie.title +"', '"+ movie.director +"', '"+ movie.genre +"')";
		dbQuery.execute(query);
	}
	
	private void updateMovie(Movie movie) {
		String query = 
				" UPDATE Movies SET "
				+ " titulo = '"+ movie.title +"',"
				+ " diretor = '"+ movie.title +"',"
				+ " genero = '"+ movie.title +"';";
		dbQuery.execute(query);
	}
}
