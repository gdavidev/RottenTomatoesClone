package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Movie;

public class DAOMovies extends DAO {
	public ArrayList<Movie> getAll() {
		String query = "SELECT * FROM Movies;";
		ArrayList<Movie> list = new ArrayList<Movie>();
		
		ResultSet rs = this.dbQuery.query(query);
		try {
			while(rs.next()) {
				Movie movie = new Movie(rs.getInt("id"), rs.getString("titulo"), rs.getString("diretor"), rs.getString("genero"));
				list.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public Movie getMovie(int id) {
		String query = "SELECT * FROM Movies WHERE id = "+ String.valueOf(id) +";";
		Movie movie = null;		
		
		try(ResultSet rs = this.dbQuery.query(query)) {
			if (rs.getFetchSize() == 1)
				movie = new Movie(rs.getInt("id"), rs.getString("titulo"), rs.getString("diretor"),rs.getString("genero"));			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}
}
