package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Rate;

public class DAORates extends DAO {
	
	public Rate getRate(int movie, int user) {
		Rate rate = new Rate();
		
		String query = 
				  " SELECT * FROM ratings WHERE movieId = " + String.valueOf(movie) + "AND userId = " + String.valueOf(user) + ";";
		
		ResultSet rs = this.dbQuery.query(query);
		try {
			while(rs.next()) {
				rate = new Rate(
					rs.getInt("movieID"),
					rs.getInt("userID"),
					rs.getInt("ratingID")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return rate;
	}

    public boolean registra(int movie, int user, int rate) {
        String query = "INSERT INTO ratings (movieId, userId, rating) VALUES (?, ?, ?);";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = this.conn.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, movie);
            ps.setInt(2, user);
            ps.setInt(3, rate);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (ps != null) ps.close();
                // Do not close the connection here
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    
    public boolean update(int movie, int user, int rate) {
        String query = "UPDATE ratings SET rating = ? WHERE movieId = ? AND userId = ?;";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = this.conn.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, rate);
            ps.setInt(2, movie);
            ps.setInt(3, user);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (ps != null) ps.close();
                // Do not close the connection here
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    
    public boolean verificaAvaliacao(int movie, int user) {
        String query = "SELECT * FROM ratings WHERE movieId = ? AND userId = ?;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = this.conn.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, movie);
            ps.setInt(2, user);

            rs = ps.executeQuery();
            if (rs.next()) {
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                // Do not close the connection here
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
	}
}
