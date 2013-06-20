package kasa;

import java.sql.Connection;
import java.sql.SQLException;

public class Utilities {

	//Metoda koju pozivamo kada zelimo da zatvarimo konekciju 
	public static void close(Connection konekcija) {
		try {
			if (konekcija != null) {
				konekcija.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
