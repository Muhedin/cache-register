package kasa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

	public User login(String username, String password) {
		String stringZaKonekciju = "jdbc:mysql://localhost:3306/pmfkasa";

		Connection konekcija = null;

		User user = null;

		try {

			// Load the Driver class.
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver
			// here.

			// Create the connection using the static getConnection method
			konekcija = DriverManager.getConnection(stringZaKonekciju, "kasir",
					"kasir");

			// Create a Statement class to execute the SQL statement
			Statement stmt = konekcija.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select * from korisnici where " 
								+ "ime='" + username + "'"
								+ "and lozinka = sha1(\"" + password + "\")");

			// Iterate through the ResultSet, displaying two values
			// for each row using the getString method

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
			Utilities.close(konekcija);
		}

		return user;
	}

}
