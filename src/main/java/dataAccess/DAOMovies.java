package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import models.Movie;

public class DAOMovies extends DAO {
	public static enum SearchBy {
		NONE 			(0),
		TITLE 			(1),
		DIRECTOR 		(2),
		RATING_AVG 		(3),
		RATING_COUNT 	(4),
		GENRE 			(5);		
		Integer currentValue;
		
		SearchBy(int SearchByValue) {
			this.currentValue = SearchByValue;
		}
		
		public static final SearchBy valueOf(Integer value) {
			return Arrays.stream(SearchBy.values())
					.filter(searchBy -> searchBy.currentValue.equals(value))
					.findFirst()
					.orElse(NONE);
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
					.findFirst()
					.orElse(MOST_RATED);
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
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero ";
		if (search.trim() != "") {
			switch (searchParameter) {
				case TITLE:			query += "WHERE m.titulo LIKE '%" + search + "%'"; 		break;
				case DIRECTOR: 		query += "WHERE m.director LIKE '%" + search + "%'"; 	break;
				case RATING_AVG:	query += "WHERE ratingAverage LIKE '%" + search + "%'"; break;
				case RATING_COUNT:	query += "WHERE ratingCount LIKE '%" + search + "%'"; 	break;
				case GENRE:			query += "WHERE m.genero LIKE '%" + search + "%'"; 		break;
				default: break;
			}
		}
		switch (sortParameter) {
			case MOST_RATED: 	query += "ORDER BY ratingCount DESC "; 		break;
			case BEST_RATING: 	query += "ORDER BY ratingAverage DESC ";	break;
			case NAME: 			query += "ORDER BY m.titulo DESC ";			break;
		}
		if (amount > 0) {
			query += " LIMIT " + amount;
		}
		
		ResultSet rs = this.dbQuery.query(query);
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public Movie getMovie(int id) {
		Movie movie = new Movie();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " INNER JOIN ratings AS r ON r.movieId = m.id "
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero "
				+ " WHERE id = " + String.valueOf(id) + ";";
		
		ResultSet rs = this.dbQuery.query(query);
		try {
			while(rs.next()) {
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
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
