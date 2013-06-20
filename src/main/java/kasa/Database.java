package kasa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Notification;

public class Database {
	private static String connectionString = "jdbc:mysql://localhost:3306/pmfkasa";
	private static String dbUsername = "kasir";
	private static String dbPassword = "kasir";

	public static Connection getConnection() {
		Connection connection = null;

		try {

			// Load the Driver class.
			Class.forName("com.mysql.jdbc.Driver");

			// Kreiramo konekciju koristeci staticku metodu getConnection
			connection = DriverManager.getConnection(connectionString,
					dbUsername, dbPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;

	}

	
	//Metoda pomocu koje vrsimo unos kasira u bazu
	public static void saveUser(User user) {
		Connection connection = Database.getConnection();

		try {
			Statement statement = connection.createStatement();
			String query = "insert into korisnici (ime, lozinka, admin) values (" +
					"'" + user.getTxtCashierName() + "', " 
					+ hash(user.getTxtPassword()) + ", "
					+ "false)";
			
			System.out.println(query);
			statement.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Nisam uspio sacuvati proizvod u bazu.");
		}
		
	}

	
	private static String hash(String password) {
		return "sha1(\"" + password + "\")";
	}


	//Medota pomocu koje vrsimo unos proizvoda u bazu 
	public static void saveProduct(Product p) {

		Connection connection = Database.getConnection();

		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into proizvodi (barkod, cijena, naziv) values ("
							+ "'" + p.getBarcode() + "', "
							+ "'" + p.getPrice() + "', " 
							+ "'" + p.getName() + "')");

		} catch (SQLException e) {
			e.printStackTrace();
			Notification.show("Nisam uspio dodati korisnika!");
		}

	}

	public static List<Product> findAllProducts() {
		List<Product> products = new ArrayList<Product>();
		Product product;
		
		int barcode;
		String name;
		double price;
		
		Connection connection = null;

		try {
			connection = Database.getConnection();

			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select * from proizvodi");

			while (rs.next()) {
				barcode = rs.getInt("barkod");
				name = rs.getString("naziv");
				price = rs.getDouble("cijena");
				
				product = new Product(barcode, name, price);
				
				products.add(product);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}
		
		System.out.println("Ucitali smo " + products.size() + " proizvoda");
		return products;
	}

}
