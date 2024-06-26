package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Movie;

public class DAOMovies extends DAO {
	public enum SortBy {
		MOST_RATED,
		BEST_RATING,
		NAME
	}
	
	public ArrayList<Movie> getMovie(SortBy sortParameter) {
		return getMovie(0, sortParameter);
	}
	
	public ArrayList<Movie> getMovie(int amount, SortBy sortParameter) {
		ArrayList<Movie> list = new ArrayList<Movie>();
		String query = 
				  " SELECT m.id, m.titulo, m.diretor, m.genero, COUNT(r.userId) AS ratingCount, ROUND(AVG(r.rating), 2) AS ratingAverage FROM movies AS m "
				+ " INNER JOIN ratings AS r ON r.movieId = m.id "
				+ " GROUP BY m.id, m.titulo, m.diretor, m.genero ";
		switch (sortParameter) {
			case MOST_RATED: 	query += "ORDER BY ratingCount DESC "; 		break;
			case BEST_RATING: 	query += "ORDER BY ratingAverage DESC ";	break;
			case NAME: 			query += "ORDER BY m.titulo DESC ";			break;
		}
		if (amount > 0) {
			query += "LIMIT " + amount;
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
		Movie movie = null;		
		String query = "SELECT * FROM Movies WHERE id = "+ String.valueOf(id) +";";
		
		try(ResultSet rs = this.dbQuery.query(query)) {
			if (rs.getFetchSize() == 1)
				movie = new Movie(rs.getInt("id"), rs.getString("titulo"), rs.getString("diretor"),rs.getString("genero"));			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}
}
