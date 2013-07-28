package kasa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private static String connectionString = "jdbc:mysql://localhost:3306/pmfkasa";
	private static String dbUsername = "root";
	private static String dbPassword = "root";

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
			
//			System.out.println(query);
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

		String queryString = String.format("insert into proizvodi (barkod, cijena, naziv, akcija) " 
					+ " values (%d, %f, '%s', %b)", 
					p.getBarcode(), p.getPrice(), p.getName(), p.isLowPrice());
		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate(queryString);

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Nisam uspio dodati korisnika!");
		}

	}

//------------------------------------------------------------------------------------------
	
	public static List<User> findAllUsers() {
		List<User> users = new ArrayList<User>();
		User user;
		
		String ime;
		boolean admin;
		
		Connection connection = null;

		try {
			connection = Database.getConnection();

			// Create a Statement class to execute the SQL statement
			Statement stmt = connection.createStatement();

			// Execute the SQL statement and get the results in a Resultset
			ResultSet rs = stmt.executeQuery("select ime, admin from korisnici");

			while (rs.next()) {
				ime = rs.getString("ime");
				admin = rs.getBoolean("admin");

				user = new User(ime, admin);

				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connection
			Utilities.close(connection);
		}
		
		System.out.println("Ucitali smo " + users.size() + " korisnika!");
		
		return users;
	}
//------------------------------------------------------------------------------------------
	
	//Statička medota kojom nalazimo sve proizvode u bazi
	public static List<Product> findAllProducts() {
		List<Product> products = new ArrayList<Product>();
		Product product;
		
		int barcode;
		String name;
		double price;
		boolean isLowPrice;
		
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
				isLowPrice = rs.getBoolean("akcija");
				
				product = new Product(barcode, name, price, isLowPrice);
				
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
		
		System.out.println("Ucitali smo " + products.size() + " proizvoda!");
		return products;
	}


	public static void updateProduct(int barcode, Product product) {
		Connection connection = Database.getConnection();

		String queryString = String.format("update proizvodi set barkod = %d, cijena = %f, naziv = '%s', akcija = %b where barkod = %d", 
				product.getBarcode(), product.getPrice(), product.getName(), product.isLowPrice(), barcode);
		
		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate(queryString);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Nisam uspio ažurirati proizvod.");
		}
		
	}


	public static void deleteProduct(int barcode) {
		Connection connection = Database.getConnection();

		String queryString = String.format("delete from proizvodi where barkod = %d", barcode);
		
		try {

			Statement statement = connection.createStatement();
			statement.executeUpdate(queryString);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Nisam uspio obrisati proizvod.");
		}
		
	}

}