package kasa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

	public User login(String username, String password) {
		Connection connection = null;
		User user = null;

		try {
			connection = Database.getConnection();

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("select * from korisnici where " 
								+ "ime='" + username + "'"
								+ "and lozinka = sha1(\"" + password + "\")");

			if (rs.next()) {
				user = new User(username, password);
				user.setUsername(username);
				user.setAdmin(rs.getBoolean("admin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}

		return user;
	}

}