package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.User;

public class DAOUsers extends DAO {

    public User loga(String email, String password) {
        User user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?;";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = this.conn.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("userName"), rs.getString("email"),
                        rs.getString("password"), rs.getInt("admin"));
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

        return user;
    }

    public boolean registra(String name, String email, String password) {
        String query = "INSERT INTO users (userName, email, password, admin) VALUES (?, ?, ?, 0);";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = this.conn.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

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
}
