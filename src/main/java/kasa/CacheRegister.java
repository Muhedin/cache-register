package kasa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CacheRegister {

	public Product izracunajCijenu(Product proizvod) {
		double totalPrice = 0;

		double price = getPriceForProduct(proizvod.getBarcode()) * proizvod.getQuantity();
		totalPrice += price;

		proizvod.setTotalPrice(totalPrice);
		proizvod.setName(getNameForProduct(proizvod.getBarcode()));

		return proizvod;
	}

	// TODO Refaktorisati
	private String getNameForProduct(int barcode) {
		Connection connection = null;
		String name = null;

		try {

			connection = Database.getConnection();
			
			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select naziv from proizvodi where barkod='"
							+ barcode + "'");

			// Iterate through the ResultSet, displaying two values
			// for each row using the getString method

			while (rs.next()) {
				name = rs.getString("naziv");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}

		return name;
	}

	private double getPriceForProduct(int barkodProizvoda) {
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
