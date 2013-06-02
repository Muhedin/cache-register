package kasa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static String connectionString = "jdbc:mysql://localhost:3306/pmfkasa";
	private static String dbUsername = "kasir";
	private static String dbPassword = "kasir";

	public static Connection getConnection() {
		Connection connection = null;

		try {

			// Load the Driver class.
			Class.forName("com.mysql.jdbc.Driver");
			// If you are using any other database then load the right driver
			// here.

			// Create the connection using the static getConnection method
			connection = DriverManager.getConnection(connectionString,
					dbUsername, dbPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;

	}

	public static void save(Proizvod p) {

		Connection connection = Database.getConnection();

		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into proizvodi (barkod, cijena, naziv) values (" +
					"'" + p.getBarKod() + "', " 
					+ "'" + p.getCijena() + "', " 
					+ "'" + p.dajNaziv() + "')");
			
			
		} catch (SQLException e) {
			System.out.println("Nisam uspio sacuvati proizvod u bazu.");
		}

	}
}
