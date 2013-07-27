package kasa;

import java.sql.Connection;
import java.sql.SQLException;

public class Utilities {

	//Metoda koju pozivamo kada zelimo da zatvorimo konekciju 
	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}