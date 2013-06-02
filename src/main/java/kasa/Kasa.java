package kasa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Kasa {

	public Proizvod izracunajCijenu(Proizvod proizvod) {
		double ukupnaCijena = 0;

		double cijena = saznajCijenu(proizvod.getBarKod())
				* proizvod.getKolicina();
		ukupnaCijena += cijena;

		proizvod.postaviUkupnuCijenu(ukupnaCijena);
		proizvod.postaviNaziv(saznajNaziv(proizvod.getBarKod()));

		return proizvod;
	}

	// TODO Refaktorisati
	private String saznajNaziv(int barkod) {
		Connection connection = null;
		String naziv = null;

		try {

			connection = Database.getConnection();
			
			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select naziv from proizvodi where barkod='"
							+ barkod + "'");

			// Iterate through the ResultSet, displaying two values
			// for each row using the getString method

			while (rs.next()) {
				naziv = rs.getString("naziv");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}

		return naziv;
	}

	private double saznajCijenu(int barkodProizvoda) {
		Connection connection = null;

		double cijena = 0;

		try {

			connection = Database.getConnection();
			
			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select cijena from proizvodi where barkod='"
							+ barkodProizvoda + "'");

			// Iterate through the ResultSet, displaying two values
			// for each row using the getString method

			while (rs.next()) {
				cijena = rs.getDouble("cijena");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}

		return cijena;
	}

	public boolean postojiBarkod(String barkodString) {
		int barkod = Integer.valueOf(barkodString);

		Connection connection = null;

		boolean postoji = false;

		try {

			connection = Database.getConnection();
			
			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select barkod from proizvodi where barkod='"
							+ barkod + "'");

			postoji = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}

		return postoji;
	}
}
